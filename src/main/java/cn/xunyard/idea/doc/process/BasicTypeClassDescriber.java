package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public class BasicTypeClassDescriber implements ClassDescriber {

    private final String packageName;
    private final String className;

    BasicTypeClassDescriber(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }


    @Nullable
    @Override
    public String getDescription() {
        return className;
    }

    @Override
    public String getNote() {
        return null;
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
        return true;
    }

    @Override
    public Set<ClassDescriber> getExtend() {
        return null;
    }

    @Override
    public boolean isParameterized() {
        return false;
    }

    @Override
    public List<ClassDescriber> getParameterized() {
        return null;
    }
}
