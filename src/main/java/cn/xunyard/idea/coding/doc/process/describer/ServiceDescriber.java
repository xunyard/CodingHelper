package cn.xunyard.idea.coding.doc.process.describer;

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

    /**
     * 方法描述
     */
    List<MethodDescriber> getMethods();

    /**
     * 包路径
     */
    String getPackage();

    /**
     * 类名
     */
    String getSimpleClass();

    /**
     * 带包路径的完整类名
     */
    String getFullName();
}