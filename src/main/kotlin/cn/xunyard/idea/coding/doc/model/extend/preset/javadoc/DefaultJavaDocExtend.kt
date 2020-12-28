package cn.xunyard.idea.coding.doc.model.extend.preset.javadoc

import cn.xunyard.idea.coding.doc.model.extend.JavaDocExtend
import cn.xunyard.idea.coding.doc.model.extend.JavaDocTagExtend
import com.thoughtworks.qdox.model.JavaAnnotatedElement

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
class DefaultJavaDocExtend constructor(
    javaModel: JavaAnnotatedElement
) : JavaDocExtend {
    private val comment: String? = javaModel.comment
    private val tagMap = HashMap<String, JavaDocTagExtend>()

    override fun getComment(): String? {
        return comment
    }

    override fun getTagMap(): MutableMap<String, JavaDocTagExtend> {
        return tagMap
    }

    override fun putTag(tagExtend: JavaDocTagExtend) {
        tagMap[tagExtend.getTagName()] = tagExtend
    }

    override fun getTagNames(): MutableSet<String> {
        return tagMap.keys
    }

    override fun getTagProperty(tagName: String): String? {
        return tagMap[tagName]?.getContent()
    }
}