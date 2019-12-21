package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.describer.BeanDescriberManager;
import cn.xunyard.idea.doc.logic.describer.ServiceDescriber;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

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

    private final Set<String> servicePathSet;
    private final DocBuildingContext docBuildingContext;
    private List<ServiceDescriber> serviceDescriberList;

    public ServiceResolver(Set<String> servicePathSet, DocBuildingContext docBuildingContext) {
        this.servicePathSet = servicePathSet;
        this.docBuildingContext = docBuildingContext;
    }

    public void run() {
        DocLogger.info("开始解析服务...");
        if (AssertUtils.isEmpty(servicePathSet)) {
            return;
        }

        BeanDescriberManager.getInstance().clear();
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        serviceDescriberList = new LinkedList<>();
        for (String srcPath : servicePathSet) {
            resolve(javaProjectBuilder, srcPath);
        }
        DocLogger.info("服务解析完成");
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
