package cn.xunyard.idea.coding.doc.model.support

import cn.xunyard.idea.coding.util.AssertUtils

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-22
 */
interface ClassSupport : CommentSupport {

    fun getPackage(): String?

    fun getSimpleName(): String

    /**
     * 获取类的完整路径名称
     */
    fun getFullName(): String {
        return if (AssertUtils.isEmpty(getPackage())) getSimpleName() else "${getPackage()}.${getSimpleName()}"
    }

    /**
     * 是否为基础类型
     */
    fun isBasicType(): Boolean {
        return when (getFullName()) {
            "java.lang.Class",
            "java.lang.Object",
            "java.lang.Boolean",
            "java.lang.Byte",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Char",
            "java.lang.String",
            "java.lang.Float",
            "java.lang.Double",
            "java.util.Date"
            -> true
            else -> false
        }
    }
}