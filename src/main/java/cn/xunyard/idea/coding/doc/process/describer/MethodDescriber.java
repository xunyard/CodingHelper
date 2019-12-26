package cn.xunyard.idea.coding.doc.process.describer;

import cn.xunyard.idea.coding.util.AssertUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface MethodDescriber {

    /**
     * 方法描述信息
     */
    String getDescription();

    /**
     * 备注信息
     */
    String getNote();

    String getPackage();

    String getClassName();

    default boolean hasResponse() {
        return getResponse() != null;
    }

    /**
     * 方法返回对象
     */
    @Nullable
    ClassDescriber getResponse();

    default boolean hasParameter() {
        return !AssertUtils.isEmpty(getParameterList());
    }

    /**
     * 方法参数集合
     */
    List<ParameterDescriber> getParameterList();

    /**
     * 方法名
     */
    String getSimpleName();

    /**
     * 方法签名
     */
    String getSign();
}
