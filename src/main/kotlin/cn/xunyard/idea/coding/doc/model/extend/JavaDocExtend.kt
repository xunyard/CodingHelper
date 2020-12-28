package cn.xunyard.idea.coding.doc.model.extend

import cn.xunyard.idea.coding.doc.model.extend.preset.javadoc.JavaDocKind

/**
 * Java文档符号
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
interface JavaDocExtend : Extend {

    /**
     * 获取JavaDoc描述的备注内容
     */
    fun getComment(): String?

    /**
     * 获取JavaDoc内所有的标签
     */
    fun getTagMap(): MutableMap<String, JavaDocTagExtend>

    /**
     * 添加所需要的JavaDoc标签
     */
    fun putTag(tagExtend: JavaDocTagExtend)

    /**
     * 获取标签名字集合
     */
    fun getTagNames(): MutableSet<String>

    /**
     * 获取标签的内容
     */
    fun getTagProperty(tagName: String): String?
}