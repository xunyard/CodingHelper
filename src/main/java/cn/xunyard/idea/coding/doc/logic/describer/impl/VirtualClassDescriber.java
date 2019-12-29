package cn.xunyard.idea.coding.doc.logic.describer.impl;

import cn.xunyard.idea.coding.doc.logic.ClassUtils;
import cn.xunyard.idea.coding.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.FieldDescriber;
import com.thoughtworks.qdox.model.JavaType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
public class VirtualClassDescriber extends AbstractBasicClass implements ClassDescriber{
    private final JavaType javaType;

    public VirtualClassDescriber(JavaType javaType) {
        super(javaType);
        this.javaType = javaType;
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
    public List<FieldDescriber> getFields() {
        return null;
    }

    @Override
    public boolean isBasicType() {
        return ClassUtils.isBasicType(javaType);
    }

    @Override
    public Set<ClassDescriber> getExtend() {
        return Collections.emptySet();
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
