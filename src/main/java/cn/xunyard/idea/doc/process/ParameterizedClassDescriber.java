package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import cn.xunyard.idea.util.ObjectUtils;
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
public class ParameterizedClassDescriber extends GeneralClassDescriber {

    private final List<ClassDescriber> parameterizedList;

    public ParameterizedClassDescriber(JavaClass javaClass, List<FieldDescriber> fields, Set<ClassDescriber> extendSet,
                                       List<ClassDescriber> parameterizedList) {
        super(javaClass, fields, extendSet);
        this.parameterizedList = ObjectUtils.firstNonNull(parameterizedList, LinkedList::new);
    }

    @Override
    public boolean isBasicType() {
        return false;
    }

    @Override
    public boolean isParameterized() {
        return true;
    }
}
