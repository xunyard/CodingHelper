package cn.xunyard.idea.coding.doc.process.describer;

import cn.xunyard.idea.coding.util.AssertUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * 类描述，区别于{@link ServiceDescriber}，不解析方法，仅解析{@link FieldDescriber}
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface ClassDescriber extends DescriberRender {

    /**
     * 是否为源代码
     */
    default boolean hasPackage() {
        return true;
    }

    /**
     * 包路径
     */
    @Nullable
    String getPackage();

    default boolean hasFields() {
        return !AssertUtils.isEmpty(getFields());
    }

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

    default boolean hasExtend() {
        return !AssertUtils.isEmpty(getExtend());
    }

    /**
     * 扩展的复合字段(非基础类型字段或内容)
     */
    Set<ClassDescriber> getExtend();

    /**
     * 是否泛型参数化
     */
    default boolean isParameterized() {
        return !AssertUtils.isEmpty(getParameterized());
    }

    /**
     * 泛型扩展
     */
    List<ClassDescriber> getParameterized();
}
