package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDescriber {

    private Api api;
    private List<MethodDescriber> methodDescriberList;

    public static ServiceDescriber fromJavaClass(JavaClass javaClass) {
        return new ServiceDescriber(javaClass);
    }

    private ServiceDescriber(JavaClass javaClass) {
        resolveRoot(javaClass);
        resolveMethod(javaClass);
    }

    private void resolveRoot(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            Api api = Api.fromAnnotation(annotation);

            if (api != null) {
                this.api = api;
            }
        }

        String comment = javaClass.getComment();

        if (AssertUtils.isEmpty(comment)) {
            this.api = new Api(javaClass.getName(), null);
        } else {
            // TODO 解析comment内容
        }
    }

    private void resolveMethod(JavaClass javaClass) {
        methodDescriberList = new LinkedList<>();
        for (JavaMethod javaMethod : javaClass.getMethods()) {
            methodDescriberList.add(MethodDescriber.fromJavaMethod(javaMethod));
        }
    }

    /**
     * @see io.swagger.annotations.Api
     */
    @Getter
    private static class Api {
        private static final String NAME = "io.swagger.annotations.Api";

        private final String value;
        private final String note;

        private Api(String value, String note) {
            this.value = value;
            this.note = note;
        }

        private static Api fromAnnotation(JavaAnnotation annotation) {
            if (!NAME.equals(annotation.getType().getName())) {
                return null;
            }

            return new Api(annotation.getProperty("value").toString(), null);
        }
    }

    /**
     * @see io.swagger.annotations.ApiOperation
     */
    @Getter
    private static class ApiOperation {
        private static final String NAME = "io.swagger.annotations.ApiOperation";

        private final String value;
        private final String note;

        private ApiOperation(String value, String note) {
            this.value = value;
            this.note = note;
        }

        private static ApiOperation fromAnnotation(JavaAnnotation annotation) {
            if (!NAME.equals(annotation.getType().getName())) {
                return null;
            }

            return new ApiOperation(annotation.getProperty("value").toString(),
                    annotation.getProperty("note").toString());
        }
    }
}
