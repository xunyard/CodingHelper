package cn.xunyard.idea.coding.doc.model.extend.preset.javadoc.tag

import cn.xunyard.idea.coding.doc.model.extend.AbstractJavaDocTagExtend
import com.thoughtworks.qdox.model.DocletTag

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
class Deprecated constructor(
        tag: DocletTag
) : AbstractJavaDocTagExtend(tag) {

    init {
        if (getTagName() != tag.name) {
            throw IllegalArgumentException("java.doc.tag.mismatch.with.deprecated")
        }
    }

    override fun getTagName(): String {
        return "deprecated"
    }
}