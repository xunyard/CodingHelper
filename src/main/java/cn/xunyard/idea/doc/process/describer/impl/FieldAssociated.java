package cn.xunyard.idea.doc.process.describer.impl;

import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
public class FieldAssociated {
    private List<FieldDescriber> fields;
    private Set<ClassDescriber> extendSet;

    public FieldAssociated() {
        fields = new LinkedList<>();
    }

    public void addField(FieldDescriber field) {
        fields.add(field);
    }

    public void addExtend(ClassDescriber extend) {
        if (extendSet == null) {
            extendSet = new HashSet<>();
        }

        extendSet.add(extend);
    }
}
