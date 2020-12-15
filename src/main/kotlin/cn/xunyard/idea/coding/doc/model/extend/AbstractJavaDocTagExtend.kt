package cn.xunyard.idea.coding.doc.model.extend

import com.thoughtworks.qdox.model.DocletTag

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
abstract class AbstractJavaDocTagExtend constructor(
        private val tag: DocletTag
) : JavaDocTagExtend {

    override fun getContent(): String? {
        return tag.value
    }
}