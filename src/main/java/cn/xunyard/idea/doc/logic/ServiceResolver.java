package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.process.ProcessContext;
import cn.xunyard.idea.doc.process.describer.ServiceDescriber;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceResolver {
    private static final ThreadLocal<Boolean> RESOLVE_OK = new ThreadLocal<>();

    private final List<JavaClass> serviceClasses;
    private final ProcessContext processContext;
    @Getter
    private List<ServiceDescriber> serviceDescriberList;

    public static void setResolveFail() {
        RESOLVE_OK.set(false);
    }

    public ServiceResolver(List<JavaClass> serviceClasses, ProcessContext processContext) {
        this.serviceClasses = serviceClasses;
        this.processContext = processContext;

        serviceDescriberList = new ArrayList<>(serviceClasses.size());
    }

    public boolean run() {
        RESOLVE_OK.set(true);
        DocLogger.info("开始解析服务...");
        if (AssertUtils.isEmpty(serviceClasses)) {
            DocLogger.error("未发现有效服务");
            return false;
        }

        for (JavaClass javaClass : serviceClasses) {
            serviceDescriberList.add(processContext.getServiceDescriberMaker().fromJavaClass(javaClass));
        }
        DocLogger.info("服务解析完成");

        boolean isOk = RESOLVE_OK.get();
        RESOLVE_OK.remove();
        return isOk;
    }
}
