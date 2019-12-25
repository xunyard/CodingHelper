package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.process.describer.ClassDescriber;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
public interface ClassDescriberMerge {

    ClassDescriber merge(ClassDescriber other);
}
