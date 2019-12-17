package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaType;
import lombok.Getter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class BeanDescriber {

    private ApiModel apiModel;

    public static BeanDescriber fromJavaType(JavaType javaType) {

        return null;
    }

    private void resolveRoot(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            ApiModel apiModel = ApiModel.fromAnnotation(annotation);

            if (apiModel != null) {
                this.apiModel = apiModel;
            }
        }

        String comment = javaClass.getComment();

        if (AssertUtils.isEmpty(comment)) {
            this.apiModel = new ApiModel(javaClass.getName());
        } else {
            // TODO 解析comment内容
        }
    }

    private void resolveMethod(JavaClass javaClass) {
//        methodDescriberList = new LinkedList<>();
//        for (JavaMethod javaMethod : javaClass.getMethods()) {
//            methodDescriberList.add(MethodDescriber.fromJavaMethod(javaMethod));
//        }
    }

    /**
     * @see io.swagger.annotations.ApiModel
     */
    @Getter
    private static class ApiModel {
        private static final String NAME = "io.swagger.annotations.ApiModel";

        private final String value;

        private ApiModel(String value) {
            this.value = value;
        }

        private static ApiModel fromAnnotation(JavaAnnotation annotation) {
            if (!NAME.equals(annotation.getType().getName())) {
                return null;
            }

            return new ApiModel(annotation.getProperty("value").toString());
        }
    }

    /**
     * @see io.swagger.annotations.ApiModelProperty
     */
    @Getter
    private static class ApiModelProperty {
        private static final String NAME = "io.swagger.annotations.ApiModelProperty";

        private final String value;
        private final String note;
        private final Boolean required;

        private ApiModelProperty(String value, String note, Boolean required) {
            this.value = value;
            this.note = note;
            this.required = required;
        }

        private static ApiModelProperty fromAnnotation(JavaAnnotation annotation) {
            if (!NAME.equals(annotation.getType().getName())) {
                return null;
            }

            return new ApiModelProperty(annotation.getProperty("value").toString(),
                    annotation.getProperty("note").toString(),
                    Boolean.parseBoolean(annotation.getProperty("required").toString()));
        }
    }
}
