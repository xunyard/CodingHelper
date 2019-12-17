package cn.xunyard.idea.doc.logic;

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
    private List<ServiceDescriber> serviceDescriberList;

    public ServiceResolver(Set<String> servicePathSet) {
        this.servicePathSet = servicePathSet;
    }

    public void run() {
        if (AssertUtils.isEmpty(servicePathSet)) {
            return;
        }

        BeanDescriberManager.getInstance().clear();
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        serviceDescriberList = new LinkedList<>();
        for (String srcPath : servicePathSet) {
            resolve(javaProjectBuilder, srcPath);
        }
    }

    private void resolve(JavaProjectBuilder builder, String srcPath) {
        try {
            JavaSource javaSource = builder.addSource(new File(srcPath));
            for (JavaClass javaClass : javaSource.getClasses()) {
                resolveServiceClass(javaClass);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resolveServiceClass(JavaClass javaClass) {
        serviceDescriberList.add(ServiceDescriber.fromJavaClass(javaClass));
    }
}
