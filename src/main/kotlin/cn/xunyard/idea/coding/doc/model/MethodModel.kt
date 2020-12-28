package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.support.ClassSupport
import cn.xunyard.idea.coding.doc.model.support.CommentSupport

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-22
 */
interface MethodModel : CommentSupport {

    /**
     * 获取方法的返回对象
     */
    fun getReturnObj(): ClassSupport

    /**
     * 获取方法名
     */
    fun getName(): String

    /**
     * 获取方法的参数
     */
    fun getParams(): Array<ClassSupport>
}