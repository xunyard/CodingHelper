package cn.xunyard.idea.doc.process.describer;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface FieldDescriber extends DescriberRender {

    boolean isRequired();

    String getName();

    boolean isBasicType();
}