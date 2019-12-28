package cn.xunyard.idea.coding.doc.process.describer.impl;

import cn.xunyard.idea.coding.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.process.describer.FieldDescriber;
import com.thoughtworks.qdox.model.JavaType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-28
 */
public class ReferenceClassDescriber extends AbstractBasicClass implements ClassDescriber {

    public ReferenceClassDescriber(JavaType javaType) {
        super(javaType);
    }

    @Override
    public boolean isCycleReference() {
        return true;
    }

    @Override
    public List<FieldDescriber> getFields() {
        return null;
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public Set<ClassDescriber> getExtend() {
        return null;
    }

    @Override
    public List<ClassDescriber> getParameterized() {
        return null;
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
}
