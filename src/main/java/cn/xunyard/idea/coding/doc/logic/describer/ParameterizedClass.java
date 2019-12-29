package cn.xunyard.idea.coding.doc.logic.describer;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-27
 */
public interface ParameterizedClass extends ClassDescriber {

    boolean isParameterizedBasicType();

    boolean isCommonCollectionClass();
}
