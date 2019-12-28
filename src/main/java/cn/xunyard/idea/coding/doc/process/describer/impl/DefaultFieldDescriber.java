package cn.xunyard.idea.coding.doc.process.describer.impl;

import cn.xunyard.idea.coding.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.process.describer.FieldDescriber;
import cn.xunyard.idea.coding.doc.process.model.ApiModelProperty;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
public class DefaultFieldDescriber implements FieldDescriber {
    private final boolean required;
    private final String description;
    private final String name;
    private final String note;
    @Nullable
    private final ClassDescriber classDescriber;

    public DefaultFieldDescriber(ApiModelProperty apiModelProperty, String name, @Nullable ClassDescriber classDescriber) {
        this.required = apiModelProperty.getRequired();
        this.description = apiModelProperty.getValue();
        this.note = apiModelProperty.getNote();
        this.name = name;
        this.classDescriber = classDescriber;
    }

    @Override
    public boolean isBasicType() {
        return classDescriber.isBasicType();
    }

    @Override
    public String toSimpleString() {
        return classDescriber.toSimpleString();
    }
}