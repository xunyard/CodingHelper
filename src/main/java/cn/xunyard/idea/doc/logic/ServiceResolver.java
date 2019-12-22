package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.describer.BeanDescriberManager;
import cn.xunyard.idea.doc.logic.describer.ServiceDescriber;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceResolver {
    private static final ThreadLocal<Boolean> RESOLVE_OK = new ThreadLocal<>();

    private final Set<String> servicePathSet;
    private final DocBuildingContext docBuildingContext;
    @Getter
    private List<ServiceDescriber> serviceDescriberList;

    public static void setResolveFail() {
        RESOLVE_OK.set(false);
    }

    public ServiceResolver(Set<String> servicePathSet, DocBuildingContext docBuildingContext) {
        this.servicePathSet = servicePathSet;
        this.docBuildingContext = docBuildingContext;
    }

    public boolean run() {
        RESOLVE_OK.set(true);
        DocLogger.info("开始解析服务...");
        if (AssertUtils.isEmpty(servicePathSet)) {
            DocLogger.error("未发现有效服务");
            return false;
        }

        BeanDescriberManager.getInstance().clear();
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        serviceDescriberList = new LinkedList<>();
        for (String srcPath : servicePathSet) {
            resolve(javaProjectBuilder, srcPath);
        }
        DocLogger.info("服务解析完成");

        boolean isOk = RESOLVE_OK.get();
        RESOLVE_OK.remove();
        return isOk;
    }

    private void resolve(JavaProjectBuilder builder, String srcPath) {
        try {
            JavaSource javaSource = builder.addSource(new File(srcPath));
            docBuildingContext.setJavaProjectBuilder(builder);
            for (JavaClass javaClass : javaSource.getClasses()) {
                resolveServiceClass(javaClass);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resolveServiceClass(JavaClass javaClass) {
        serviceDescriberList.add(ServiceDescriber.fromJavaClass(javaClass, docBuildingContext));
    }
}
