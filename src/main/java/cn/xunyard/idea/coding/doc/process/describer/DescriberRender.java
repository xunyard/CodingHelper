package cn.xunyard.idea.coding.doc.process.describer;

import cn.xunyard.idea.coding.util.AssertUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
public interface DescriberRender {

    /**
     * 类描述信息
     */
    @Nullable
    String getDescription();

    default boolean hasNote() {
        return !AssertUtils.isEmpty(getNote());
    }

    /**
     * 备注信息
     */
    String getNote();

    /**
     * 不带路径的toString方法复写
     */
    String toSimpleString();
}
