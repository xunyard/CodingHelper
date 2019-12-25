package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.MethodDescriber;
import cn.xunyard.idea.doc.process.describer.ParameterDescriber;
import cn.xunyard.idea.doc.process.describer.impl.DefaultMethodDescriber;
import cn.xunyard.idea.doc.process.describer.impl.DefaultParameterDescriber;
import cn.xunyard.idea.doc.process.model.ApiOperation;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
@RequiredArgsConstructor
public class MethodDescriberMaker {
    private final ProcessContext processContext;

    public MethodDescriber fromJavaMethod(JavaMethod javaMethod, JavaClass javaClass) {
        ApiOperation apiOperation = ApiOperation.fromJavaMethod(javaMethod);

        if (apiOperation == null) {
            ServiceResolver.setResolveFail();
            if (processContext.getDocConfig().getLogUnresolved()) {
                DocLogger.error("[注释缺失] 方法: " + javaClass.getName() + "#" + javaMethod.getName() + " 未找到有效注释");
            }

            apiOperation = new ApiOperation(javaMethod.getName(), null);
        }

        List<ParameterDescriber> parameterDescribers = getParameters(javaMethod.getParameters());
        ClassDescriber response = getResponse(javaMethod.getReturns());
        return new DefaultMethodDescriber(apiOperation, parameterDescribers, response, javaMethod, javaClass);
    }

    private List<ParameterDescriber> getParameters(List<JavaParameter> javaParameters) {
        if (AssertUtils.isEmpty(javaParameters)) {
            return Collections.emptyList();
        }

        List<ParameterDescriber> describers = new ArrayList<>(javaParameters.size());
        for (JavaParameter javaParameter : javaParameters) {
            String name = javaParameter.getName();
            JavaType parameterType = javaParameter.getType();

            JavaClass javaClass = processContext.getSourceClassLoader().find(parameterType.toString());
            ClassDescriber classDescriber;


            if (javaClass == null) {
                classDescriber = processContext.getClassDescriberMaker().getOrLoadVirtualClass(parameterType);
            } else {
                classDescriber = processContext.getClassDescriberMaker().fromClass(javaClass);
            }

            describers.add(new DefaultParameterDescriber(name, classDescriber));
        }

        return describers;
    }

    private ClassDescriber getResponse(JavaClass javaClass) {
        return processContext.getClassDescriberMaker().fromClass(javaClass);
    }
}
