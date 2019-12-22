package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceResolver;
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
@Getter
public class ServiceDescriber {
    private final DocBuildingContext docBuildingContext;

    private Api api;
    private JavaClass javaClass;
    private List<MethodDescriber> methodDescriberList;

    public static ServiceDescriber fromJavaClass(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        return new ServiceDescriber(javaClass, docBuildingContext);
    }

    private ServiceDescriber(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
        this.javaClass = javaClass;
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
            this.api = new Api(comment, null);
            return;
        }

        if (docBuildingContext.getLogUnresolved()) {
            ServiceResolver.setResolveFail();
            this.api = new Api(javaClass.getName(), null);
            DocLogger.error("[注释缺失] 服务: " + javaClass.getName() + " 未找到有效注释");
        }
    }

    private void resolveMethod(JavaClass javaClass) {
        methodDescriberList = new LinkedList<>();
        for (JavaMethod javaMethod : javaClass.getMethods()) {
            if (AssertUtils.isEmpty(javaMethod.getParameters()) && docBuildingContext.getLogUnresolved()) {
                ServiceResolver.setResolveFail();
                DocLogger.error("[方法非法] 方法: " + javaClass.getName() + "." + javaMethod.getName() + "未找到参数");
            } else {
                methodDescriberList.add(MethodDescriber.fromJavaMethod(javaClass, javaMethod, docBuildingContext));
            }
        }
    }
}
