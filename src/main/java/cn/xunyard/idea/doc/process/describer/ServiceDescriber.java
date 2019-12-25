package cn.xunyard.idea.doc.process.describer;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface ServiceDescriber {

    /**
     * 服务描述信息
     */
    String getDescription();

    /**
     * 备注信息
     */
    @Nullable
    String getNote();

    List<MethodDescriber> getMethods();

    /**
     * 包路径
     */
    String getPackage();

    /**
     * 类名
     */
    String getSimpleValue();

    /**
     * 带包路径的完整类名
     */
    String getFullValue();
}