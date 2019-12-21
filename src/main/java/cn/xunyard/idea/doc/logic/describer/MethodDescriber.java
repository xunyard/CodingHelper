package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.*;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
@Getter
public class MethodDescriber {
    private final DocBuildingContext docBuildingContext;
    private BeanDescriber param;
    private BeanDescriber response;
    private ApiOperation apiOperation;

    public static MethodDescriber fromJavaMethod(JavaClass javaClass, JavaMethod javaMethod,
                                                 DocBuildingContext docBuildingContext) {
        return new MethodDescriber(javaClass, javaMethod, docBuildingContext);
    }

    private MethodDescriber(JavaClass javaClass, JavaMethod javaMethod, DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
        resolveMethod(javaClass, javaMethod);
        resolveParameter(javaMethod, javaClass, docBuildingContext);
        resolveResponse(javaMethod, docBuildingContext);
    }

    private void resolveMethod(JavaClass javaClass, JavaMethod javaMethod) {
        List<JavaAnnotation> annotations = javaMethod.getAnnotations();
        for (JavaAnnotation annotation : annotations) {
            apiOperation = ApiOperation.fromAnnotation(annotation);

            if (apiOperation != null) {
                return;
            }
        }

        if (apiOperation == null) {
            String comment = javaMethod.getComment();

            if (!AssertUtils.isEmpty(comment)) {
                apiOperation = new ApiOperation(javaMethod.getName(), null);
                return;
            }
        }

        DocLogger.error("方法: " + javaClass.getName() + "#" + javaMethod.getName() + " 未找到有效注释");
    }

    private void resolveParameter(JavaMethod javaMethod, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        List<JavaParameter> parameters = javaMethod.getParameters();
        JavaParameter javaParameter = parameters.get(0);

        JavaType javaType = javaParameter.getType();
        this.param = BeanDescriberManager.getInstance().load(javaType, javaClass, docBuildingContext);
    }

    private void resolveResponse(JavaMethod javaMethod, DocBuildingContext docBuildingContext) {

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
            if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
                return null;
            }

            AnnotationValue note = annotation.getProperty("note");
            return new ApiOperation(annotation.getProperty("value").toString(),
                    note != null ? note.toString() : null);
        }
    }
}
