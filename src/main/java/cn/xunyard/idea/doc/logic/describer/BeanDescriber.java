package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class BeanDescriber {

    private ApiModel apiModel;
    private Map<String, ApiModelProperty> apiModelPropertyMap;

    public static BeanDescriber fromJavaClass(JavaClass javaClass) {
        return new BeanDescriber(javaClass);
    }

    private BeanDescriber(JavaClass javaClass) {
//        resolveRoot(javaClass);
        for (JavaField field : javaClass.getFields()) {
            resolveField(field, javaClass);
        }
    }

    private void resolveRoot(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            apiModel = ApiModel.fromAnnotation(annotation);

            if (apiModel != null) {
                return;
            }
        }

        if (apiModel == null) {
            String comment = javaClass.getComment();

            if (AssertUtils.isEmpty(comment)) {
                apiModel = new ApiModel(javaClass.getName());
                return;
            }
        }

        DocLogger.error("对象: " + javaClass.getName() + " 未找到有效注释");
    }

    private ApiModelProperty resolveField(JavaField javaField, JavaClass javaClass) {
        if (javaField.getModifiers().contains("final") ||
                javaField.getModifiers().contains("static")) {
            return null;
        }

        ApiModelProperty apiModelProperty = null;
        for (JavaAnnotation annotation : javaField.getAnnotations()) {
            apiModelProperty = ApiModelProperty.fromAnnotation(annotation);

            if (apiModelProperty != null) {
                return apiModelProperty;
            }
        }

        String comment = javaField.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            return new ApiModelProperty(comment, null, false);
        }
        DocLogger.error("属性: " + javaClass.getName() + "#" + javaField.getName() + " 未找到有效注解");
        return null;
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
            if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
                return null;
            }

            AnnotationValue value = annotation.getProperty("value");
            if (value == null) {
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
            if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
                return null;
            }
            AnnotationValue value = annotation.getProperty("value");
            if (value == null) {
                return null;
            }

            AnnotationValue note = annotation.getProperty("note");
            AnnotationValue required = annotation.getProperty("required");
            return new ApiModelProperty(annotation.getProperty("value").toString(),
                    note != null ? note.toString() : null,
                    required != null && Boolean.parseBoolean(required.toString()));
        }
    }
}
