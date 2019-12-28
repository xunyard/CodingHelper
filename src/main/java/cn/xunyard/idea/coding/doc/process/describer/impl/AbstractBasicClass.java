package cn.xunyard.idea.coding.doc.process.describer.impl;

import com.thoughtworks.qdox.model.JavaType;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-28
 */
public abstract class AbstractBasicClass {

    private final String packageName;
    private final String className;
    private final boolean hasPackage;

    public AbstractBasicClass(JavaType javaType) {
        String fullClassName = javaType.toString();

        hasPackage = fullClassName.contains(".");
        this.className = javaType.getValue();
        this.packageName = hasPackage ? fullClassName.substring(0, fullClassName.lastIndexOf("."))
                : null;
    }

    public boolean hasPackage() {
        return hasPackage;
    }

    public String getPackage() {
        return packageName;
    }

    public String getSimpleName() {
        return className;
    }

    public String getFullName() {
        return hasPackage ? packageName + "." + className : className;
    }

    public String toSimpleString() {
        return className;
    }
}
