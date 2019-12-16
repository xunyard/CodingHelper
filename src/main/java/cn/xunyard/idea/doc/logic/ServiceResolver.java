package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceResolver {

    private final Set<String> servicePathSet;
    private final Map<String, ServiceDescriber> serviceDescriberMap;
    private final Map<String, BeanDescriber> beanDescriberMap;

    public ServiceResolver(Set<String> servicePathSet) {
        this.servicePathSet = servicePathSet;
        serviceDescriberMap = new HashMap<>();
        beanDescriberMap = new HashMap<>();
    }

    public void run() {
        if (AssertUtils.isEmpty(servicePathSet)) {
            return;
        }

        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        for (String srcPath : servicePathSet) {
            resolve(javaProjectBuilder, srcPath);
        }
    }

    private void resolve(JavaProjectBuilder builder, String srcPath) {
        try {
            JavaSource javaSource = builder.addSource(new File(srcPath));

            List<JavaClass> classes = javaSource.getClasses();
            for (JavaClass javaClass : classes) {
                resolveServiceClass(javaClass);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resolveServiceClass(JavaClass javaClass) {

        List<JavaAnnotation> annotations = javaClass.getAnnotations();
    }
}
