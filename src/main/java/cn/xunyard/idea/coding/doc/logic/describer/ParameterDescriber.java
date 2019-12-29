package cn.xunyard.idea.coding.doc.logic.describer;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface ParameterDescriber {

    /**
     * 参数名
     */
    String getName();

    /**
     * 类描述
     */
    ClassDescriber getClassDescriber();
}
