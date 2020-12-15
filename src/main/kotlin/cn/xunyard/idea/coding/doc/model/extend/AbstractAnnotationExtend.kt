package cn.xunyard.idea.coding.doc.model.extend

import com.thoughtworks.qdox.model.JavaAnnotation
import com.thoughtworks.qdox.model.expression.AnnotationValue

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
abstract class AbstractAnnotationExtend constructor(
        annotation: JavaAnnotation
) : AnnotationExtend {
    private val properties: MutableMap<String, AnnotationValue> = annotation.propertyMap

    override fun getProperties(): MutableMap<String, AnnotationValue> {
        return properties
    }

    override fun getProperty(key: String): AnnotationValue? {
        return properties[key]
    }
}