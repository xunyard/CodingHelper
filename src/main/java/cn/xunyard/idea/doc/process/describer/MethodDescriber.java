package cn.xunyard.idea.doc.process.describer;

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

    /**
     * 方法返回对象
     */
    @Nullable
    ResponseDescriber getResponse();

    /**
     * 方法参数集合
     */
    List<ParameterDescriber> getParameterList();

    /**
     * 方法名
     */
    String getValue();
}
