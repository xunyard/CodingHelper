package cn.xunyard.idea.coding.doc.extend.preset.javadoc

import cn.xunyard.idea.coding.doc.extend.JavaDocExtend
import cn.xunyard.idea.coding.doc.extend.JavaDocTagExtend
import com.thoughtworks.qdox.model.*

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
class DefaultJavaDocExtend constructor(
        private val javaModel: JavaAnnotatedElement
) : JavaDocExtend {
    private val docKind: JavaDocKind
    private val comment: String?
    private val tagMap = HashMap<String, JavaDocTagExtend>()

    init {
        docKind = when (javaModel) {
            is JavaField -> JavaDocKind.FIELD
            is JavaMethod -> JavaDocKind.METHOD
            is JavaClass -> JavaDocKind.CLASS
            else -> throw RuntimeException("invalid.model.type")
        }

        comment = javaModel.comment
    }

    override fun getDocKind(): JavaDocKind {
        return docKind
    }

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