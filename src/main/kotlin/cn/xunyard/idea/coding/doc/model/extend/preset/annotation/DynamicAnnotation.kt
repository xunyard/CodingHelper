package cn.xunyard.idea.coding.doc.model.extend.preset.annotation

import cn.xunyard.idea.coding.doc.model.extend.AbstractAnnotationExtend
import com.thoughtworks.qdox.model.JavaAnnotation

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-03
 */
class DynamicAnnotation constructor(
        annotation: JavaAnnotation
) : AbstractAnnotationExtend(annotation) {

}