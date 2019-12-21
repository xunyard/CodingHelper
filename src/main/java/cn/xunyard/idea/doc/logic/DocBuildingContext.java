package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
public class DocBuildingContext {
    private final String serviceSuffix;
    private final String packagePrefix;
    private final String outputDirectory;
    private Map<String, Map<String, ClassDescriber>> projectClassMap;
    private JavaProjectBuilder javaProjectBuilder;

    public DocBuildingContext(String serviceSuffix, String packagePrefix, String outputDirectory) {
        this.serviceSuffix = serviceSuffix;
        this.packagePrefix = packagePrefix;
        this.outputDirectory = outputDirectory;
        this.projectClassMap = new HashMap<>();
    }

    public String getServiceSuffix() {
        return serviceSuffix;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public JavaProjectBuilder getJavaProjectBuilder() {
        return javaProjectBuilder;
    }

    public void setJavaProjectBuilder(JavaProjectBuilder javaProjectBuilder) {
        this.javaProjectBuilder = javaProjectBuilder;
    }

    public void addClass(ClassDescriber classDescriber) {
        if (!projectClassMap.containsKey(classDescriber.getClassName())) {
            projectClassMap.put(classDescriber.getClassName(), new HashMap<>());
        }

        Map<String, ClassDescriber> classMap = projectClassMap.get(classDescriber.getClassName());
        if (classMap.containsKey(classDescriber.getClassPackage())) {
            throw new RuntimeException("检测到同包同名类: " + classDescriber.getClassPackage() + "/" + classDescriber.getClassName());
        }

        classMap.put(classDescriber.getClassName(), classDescriber);
    }

    public ClassDescriber retrieveClass(String className, String classPackage) {
        Map<String, ClassDescriber> classMap = projectClassMap.get(className);

        if (classMap != null) {
            return classMap.get(classPackage);
        }

        return null;
    }

    public ClassDescriber tryResolveClass(String className, JavaClass serviceClass) {
        Map<String, ClassDescriber> describerMap = projectClassMap.get(className);
        if (AssertUtils.isEmpty(describerMap)) {
            return null;
        }

        // 如果只找到一个class，直接返回它
        if (describerMap.size() == 1) {
            return describerMap.values().iterator().next();
        }

        List<String> imports = serviceClass.getSource().getImports();

        for (String importStuff : imports) {
            if (importStuff.endsWith(".*")) {
                String classPackage = importStuff.substring(0, importStuff.lastIndexOf("."));
                ClassDescriber classDescriber = retrieveClass(className, classPackage);
                if (classDescriber != null) {
                    return classDescriber;
                }
            } else {
                String importClass = importStuff.substring(importStuff.lastIndexOf(".") + 1);

                if (className.equals(importClass)) {
                    return retrieveClass(className, importStuff.substring(0, importStuff.lastIndexOf(".")));
                }
            }
        }

        return null;
    }
}
