package cn.xunyard.idea.coding.doc.model

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
        return if (getPackage() == null) getSimpleName() else getPackage() + "." + getSimpleName()
    }

    /**
     * 是否为基础类型
     */
    fun isBasicType(): Boolean
}