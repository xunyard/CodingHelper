package cn.xunyard.idea.coding.doc.model

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-22
 */
interface MethodModel : CommentSupport {

    fun getReturnObj(): ClassSupport

    fun getParams(): Array<ClassSupport>
}