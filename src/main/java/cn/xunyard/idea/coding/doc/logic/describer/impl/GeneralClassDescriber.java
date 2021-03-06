package cn.xunyard.idea.coding.doc.logic.describer.impl;

import cn.xunyard.idea.coding.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.ClassDescriberMerge;
import cn.xunyard.idea.coding.doc.logic.describer.FieldDescriber;
import cn.xunyard.idea.coding.util.AssertUtils;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
public class GeneralClassDescriber extends AbstractBasicClass implements ClassDescriber, ClassDescriberMerge {
    private final JavaClass javaClass;
    private final List<FieldDescriber> fields;
    private final Set<ClassDescriber> extendSet;
    @Setter
    private String description;
    @Setter
    private String note;

    public GeneralClassDescriber(JavaClass javaClass, List<FieldDescriber> fields, Set<ClassDescriber> extendSet) {
        super(javaClass);
        this.javaClass = javaClass;
        this.fields = ObjectUtils.firstNonNull(fields, LinkedList::new);
        this.extendSet = ObjectUtils.firstNonNull(extendSet, HashSet::new);
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public Set<ClassDescriber> getExtend() {
        return extendSet;
    }

    @Override
    public List<ClassDescriber> getParameterized() {
        return null;
    }

    @Override
    public ClassDescriber merge(ClassDescriber other) {
        if (other.isBasicType()) {
            return this;
        }

        // 如果发现待merge类派生于自己，翻转方向操作
        if (other.getClass().isAssignableFrom(this.getClass())) {
            return ((GeneralClassDescriber) other).merge(this);
        }

        // 合并fields
        if (!AssertUtils.isEmpty(other.getFields())) {
            this.fields.addAll(other.getFields());
        }

        // 合并扩展的复合字段
        if (!AssertUtils.isEmpty(other.getExtend())) {
            this.extendSet.addAll(other.getExtend());
        }

        return this;
    }
}
