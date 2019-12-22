package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.*;
import lombok.Getter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
@Getter
public class MethodDescriber {
    private final DocBuildingContext docBuildingContext;
    private final JavaMethod javaMethod;
    private BeanDescriber parameter;
    private BeanDescriber response;
    private ApiOperation apiOperation;

    public static MethodDescriber fromJavaMethod(JavaClass javaClass, JavaMethod javaMethod,
                                                 DocBuildingContext docBuildingContext) {
        return new MethodDescriber(javaClass, javaMethod, docBuildingContext);
    }

    private MethodDescriber(JavaClass javaClass, JavaMethod javaMethod, DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
        this.javaMethod = javaMethod;
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
                apiOperation = new ApiOperation(comment, null);
                return;
            }
        }

        if (docBuildingContext.getLogUnresolved()) {
            ServiceResolver.setResolveFail();
            apiOperation = new ApiOperation(javaMethod.getName(), null);
            DocLogger.error("[注释缺失] 方法: " + javaClass.getName() + "#" + javaMethod.getName() + " 未找到有效注释");
        }
    }

    private void resolveParameter(JavaMethod javaMethod, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        List<JavaParameter> parameters = javaMethod.getParameters();
        JavaParameter javaParameter = parameters.get(0);

        JavaType javaType = javaParameter.getType();
        this.parameter = BeanDescriberManager.getInstance().load(javaType, javaClass, docBuildingContext);
    }

    private void resolveResponse(JavaMethod javaMethod, DocBuildingContext docBuildingContext) {

    }
}
