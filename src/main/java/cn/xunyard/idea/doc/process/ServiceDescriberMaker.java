package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.doc.process.describer.MethodDescriber;
import cn.xunyard.idea.doc.process.describer.ServiceDescriber;
import cn.xunyard.idea.doc.process.describer.impl.DefaultServiceDescriber;
import cn.xunyard.idea.doc.process.model.Api;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ProjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
@RequiredArgsConstructor
public class ServiceDescriberMaker {
    private final ProcessContext processContext;

    public ServiceDescriber fromJavaClass(JavaClass javaClass) {
        if (!javaClass.isInterface()) {
            throw new IllegalArgumentException("only allow interface kind service");
        }

        Api api = Api.fromJavaClass(javaClass);
        if (api == null) {
            ServiceResolver.setResolveFail();
            if (processContext.getDocConfig().getLogUnresolved()) {
                DocLogger.error("[注释缺失] 服务: " + javaClass.getName() + " 未找到有效注释");
            }
            api = new Api(ProjectUtils.getSimpleName(javaClass), null);
        }

        List<MethodDescriber> methods = getMethods(javaClass.getMethods(), javaClass);

        return new DefaultServiceDescriber(api, methods, javaClass);
    }

    private List<MethodDescriber> getMethods(List<JavaMethod> javaMethods, JavaClass javaClass) {
        if (AssertUtils.isEmpty(javaMethods)) {
            return Collections.emptyList();
        }

        List<MethodDescriber> describers = new ArrayList<>(javaMethods.size());
        for (JavaMethod javaMethod : javaMethods) {
            describers.add(processContext.getMethodDescriberMaker().fromJavaMethod(javaMethod, javaClass));
        }

        return describers;
    }

}
