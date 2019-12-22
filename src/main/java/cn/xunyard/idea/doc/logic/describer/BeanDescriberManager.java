package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.JavaType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
public class BeanDescriberManager {

    private final Map<String, BeanDescriber> describerMap;

    private BeanDescriberManager() {
        this.describerMap = new HashMap<>();
    }

    public BeanDescriber load(JavaType javaType, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        ClassDescriber classDescriber = docBuildingContext.tryResolveClass(javaType.getValue(), javaClass);
        if (classDescriber == null && docBuildingContext.getLogUnresolved()) {
            DocLogger.warn(String.format("无法解析的类: %s，可能不是源码!", javaType.getBinaryName()));
            return null;
        }

        if (describerMap.containsKey(classDescriber.getFullPath())) {
            return describerMap.get(classDescriber.getFullPath());
        }
        JavaProjectBuilder builder = docBuildingContext.getJavaProjectBuilder();

        JavaClass beanClass;
        try {
            JavaSource javaSource = builder.addSource(new File(classDescriber.getFullPath()));
            beanClass = javaSource.getClasses().iterator().next();
        } catch (IOException e) {
            DocLogger.error("无效的文件路径: " + classDescriber.getFullPath());
            throw new RuntimeException("invalid.file.path");
        }

        if (beanClass == null) {
            DocLogger.error("");
            return null;
        }

        BeanDescriber beanDescriber = BeanDescriber.fromJavaClass(beanClass, docBuildingContext);
        describerMap.put(classDescriber.getFullPath(), beanDescriber);
        return beanDescriber;
    }

    public void clear() {
        describerMap.clear();
    }

    public static BeanDescriberManager beanDescriberManager;

    public static BeanDescriberManager getInstance() {
        if (beanDescriberManager == null) {
            beanDescriberManager = new BeanDescriberManager();
        }

        return beanDescriberManager;
    }
}
