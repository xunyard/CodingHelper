package cn.xunyard.idea.doc.process.describer.impl;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public class SourceClassLoader {

    private final Map<String, JavaClass> loadedJavaClassMap;
    private final JavaProjectBuilder javaProjectBuilder;

    public SourceClassLoader(JavaProjectBuilder javaProjectBuilder) {
        this.javaProjectBuilder = javaProjectBuilder;
        this.loadedJavaClassMap = new HashMap<>();
    }

    public List<JavaClass> loadClass(String filepath) throws IOException {
        JavaSource javaSource = javaProjectBuilder.addSource(new File(filepath));
        if (javaSource == null) {
            return null;
        }

        List<JavaClass> javaClassList = javaSource.getClasses();
        for (JavaClass javaClass : javaClassList) {
            loadedJavaClassMap.putIfAbsent(javaClass.getValue(), javaClass);
        }

        return javaClassList;
    }

    @Nullable
    public JavaClass find(String fullClassName) {
        return loadedJavaClassMap.get(fullClassName);
    }

    public JavaClass find(String simpleClassName, List<String> classImports) {
        List<String> potentialNames = tryBuildFullClassName(simpleClassName, classImports);

        // 遍历候选完整类，尝试获取已经加载的
        for (String fullName : potentialNames) {
            if (loadedJavaClassMap.containsKey(fullName)) {
                return loadedJavaClassMap.get(fullName);
            }
        }

        // 源码中无已加载的相关类
        return null;
    }

    private List<String> tryBuildFullClassName(String simpleClassName, List<String> imports) {
        List<String> potentialFullClassNameList = new LinkedList<>();

        for (String importStuff : imports) {
            if (importStuff.endsWith(".*")) {
                String classPackage = importStuff.substring(0, importStuff.lastIndexOf("."));
                potentialFullClassNameList.add(classPackage + "." + simpleClassName);
            } else {
                String importClass = importStuff.substring(importStuff.lastIndexOf(".") + 1);

                if (simpleClassName.equals(importClass)) {
                    potentialFullClassNameList.add(importStuff);
                }
            }
        }

        return potentialFullClassNameList;
    }
}
