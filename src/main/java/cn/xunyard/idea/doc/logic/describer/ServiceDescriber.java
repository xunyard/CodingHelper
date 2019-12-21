package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
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
    private final DocBuildingContext docBuildingContext;

    private Api api;
    private List<MethodDescriber> methodDescriberList;

    public static ServiceDescriber fromJavaClass(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        return new ServiceDescriber(javaClass, docBuildingContext);
    }

    private ServiceDescriber(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
        resolveRoot(javaClass);
        resolveMethod(javaClass);
    }

    private void resolveRoot(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            this.api = Api.fromAnnotation(annotation);

            if (this.api != null) {
                return;
            }
        }

        String comment = javaClass.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            this.api = new Api(javaClass.getName(), null);
            return;
        }

        DocLogger.error("服务: " + javaClass.getName() + " 未找到有效注释");
    }

    private void resolveMethod(JavaClass javaClass) {
        methodDescriberList = new LinkedList<>();
        for (JavaMethod javaMethod : javaClass.getMethods()) {
            if (AssertUtils.isEmpty(javaMethod.getParameters())) {
                DocLogger.warn(javaClass.getName() + "." + javaMethod.getName() + "未找到参数");
            } else {
                methodDescriberList.add(MethodDescriber.fromJavaMethod(javaClass, javaMethod, docBuildingContext));
            }
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
            if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
                return null;
            }

            return new Api(annotation.getProperty("value").toString(), null);
        }
    }
}
