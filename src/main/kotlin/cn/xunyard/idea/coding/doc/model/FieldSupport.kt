package cn.xunyard.idea.coding.doc.model

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-11-02
 */
interface FieldSupport : CommentSupport {

    fun getName(): String

    fun getType(): ClassSupport
}