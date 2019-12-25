package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@Getter
@RequiredArgsConstructor
public class DefaultFieldDescriber implements FieldDescriber {
    private final boolean required;
    private final String description;
    private final String note;
    private final JavaType javaType;

    @Override
    public boolean isBasicType() {
        return AssertUtils.isBasicType(javaType);
    }
}
