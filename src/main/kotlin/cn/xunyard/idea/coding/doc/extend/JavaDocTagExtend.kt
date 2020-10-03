package cn.xunyard.idea.coding.doc.extend

import com.thoughtworks.qdox.model.JavaModel

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
interface JavaDocTagExtend : Extend {

    /**
     * 获取JavaDoc标签名称
     */
    fun getTagName(): String

    /**
     * 获取JavaDoc标签的内容
     */
    fun getContent(): String?
}