package cn.xunyard.idea.doc.process.describer;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * 类描述，区别于{@link ServiceDescriber}，不解析方法，仅解析{@link FieldDescriber}
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface ClassDescriber {

    /**
     * 类描述信息
     */
    @Nullable
    String getDescription();

    /**
     * 备注信息
     */
    String getNote();

    /**
     * 包路径
     */
    String getPackage();

    /**
     * 获得类中的字段
     */
    List<FieldDescriber> getFields();

    /**
     * 类名
     */
    String getSimpleName();

    /**
     * 带包路径的完整类名
     */
    String getFullName();

    /**
     * 是否为基础类型
     */
    boolean isBasicType();

    /**
     * 扩展的复合字段(非基础类型字段或内容)
     */
    Set<ClassDescriber> getExtend();

    /**
     * 是否泛型参数化
     */
    boolean isParameterized();

    /**
     * 泛型扩展
     */
    List<ClassDescriber> getParameterized();
}
