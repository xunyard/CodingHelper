package cn.xunyard.idea.coding.doc.process.describer.impl;

import cn.xunyard.idea.coding.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.process.describer.ParameterDescriber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
@Getter
@RequiredArgsConstructor
public class DefaultParameterDescriber implements ParameterDescriber {

    private final String name;
    private final ClassDescriber classDescriber;
}
