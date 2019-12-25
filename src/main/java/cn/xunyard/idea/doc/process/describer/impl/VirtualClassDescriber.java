package cn.xunyard.idea.doc.process.describer.impl;

import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import com.thoughtworks.qdox.model.JavaType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
public class VirtualClassDescriber implements ClassDescriber {
    private final String packageName;
    private final String className;
    private final boolean hasPackage;

    public VirtualClassDescriber(JavaType javaType) {
        String fullClassName = javaType.toString();

        hasPackage = fullClassName.contains(".");
        this.className = javaType.getValue();
        this.packageName = hasPackage ? fullClassName.substring(0, fullClassName.lastIndexOf("."))
                : null;
    }

    @Nullable
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getNote() {
        return null;
    }

    @Override
    public boolean hasPackage() {
        return hasPackage;
    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public List<FieldDescriber> getFields() {
        return null;
    }

    @Override
    public String getSimpleName() {
        return className;
    }

    @Override
    public String getFullName() {
        return packageName + "." + className;
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public Set<ClassDescriber> getExtend() {
        return Collections.emptySet();
    }

    @Override
    public boolean isParameterized() {
        return false;
    }

    @Override
    public List<ClassDescriber> getParameterized() {
        return null;
    }

    @Override
    public String toSimpleString() {
        return getSimpleName();
    }
}
