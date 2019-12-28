package cn.xunyard.idea.coding.doc.process.describer.impl;

import cn.xunyard.idea.coding.doc.ClassUtils;
import cn.xunyard.idea.coding.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.process.describer.FieldDescriber;
import cn.xunyard.idea.coding.doc.process.describer.ParameterizedClass;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
public class ParameterizedClassDescriber extends GeneralClassDescriber implements ParameterizedClass {

    private final List<ClassDescriber> parameterized;

    public ParameterizedClassDescriber(JavaClass javaClass, List<FieldDescriber> fields, Set<ClassDescriber> extendSet,
                                       List<ClassDescriber> parameterized) {
        super(javaClass, fields, extendSet);
        this.parameterized = ObjectUtils.firstNonNull(parameterized, LinkedList::new);
    }

    @Override
    public String toSimpleString() {
        StringBuilder sb = new StringBuilder(getSimpleName()).append("<");
        for (int i = 0; i < parameterized.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }

            sb.append(parameterized.get(i).toSimpleString());
        }
        sb.append(">");

        return sb.toString();
    }

    @Override
    public boolean isParameterizedBasicType() {
        for (ClassDescriber classDescriber : parameterized) {
            if (!classDescriber.isBasicType()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isCommonCollectionClass() {
        return ClassUtils.isCommonCollectionClass(super.getJavaClass());
    }
}
