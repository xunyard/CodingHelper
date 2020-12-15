package cn.xunyard.idea.coding.doc.model

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-11-01
 */
interface FieldModel : CommentSupport{

    fun getName(): String

    fun getType(): ClassSupport

    fun getContainerClass(): ClassSupport
}