package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.*;
import com.thoughtworks.qdox.model.impl.DefaultJavaParameterizedType;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
@Getter
public class MethodDescriber {
    private final DocBuildingContext docBuildingContext;
    private final JavaMethod javaMethod;
    private String parameterName;
    private BeanDescriber parameter;
    private List<BeanDescriber> responseList;
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
        resolveResponse(javaMethod, javaClass, docBuildingContext);
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
        this.parameterName = javaParameter.getName();
        this.parameter = BeanDescriberManager.getInstance().load(javaType, javaClass, docBuildingContext);
    }

    private void resolveResponse(JavaMethod javaMethod, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        if (AssertUtils.isEmpty(docBuildingContext.getReturnPackList())) {
            BeanDescriber bean = BeanDescriberManager.getInstance().load(javaMethod.getReturnType(), javaClass, docBuildingContext);
            responseList = Collections.singletonList(bean);
        } else {
            responseList = new LinkedList<BeanDescriber>();
            for (JavaType argumentType : ((DefaultJavaParameterizedType) javaMethod.getReturns()).getActualTypeArguments()) {
                BeanDescriber bean = BeanDescriberManager.getInstance().load(argumentType, javaClass, docBuildingContext);
                responseList.add(bean);
            }
        }
    }
}
