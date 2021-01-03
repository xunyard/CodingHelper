package cn.xunyard.idea.coding.doc.config

import cn.xunyard.idea.coding.util.AssertUtils
import cn.xunyard.idea.coding.util.ObjectUtils

data class DialogRenderAnnotation(

    /**
     * 注解名
     */
    var name: String = "",

    /**
     * 注解包路径
     */
    var pkg: String = "",

    /**
     * 是否为系统预置
     */
    val preset: Boolean = false,

    /**
     * 注解可用的field及其启用状态
     */
    var fieldMap: MutableMap<String, Boolean> = HashMap(),
) {

    fun fieldToString(): String {
        if (AssertUtils.isEmpty(this.fieldMap)) {
            return ""
        }

        return ObjectUtils.toSemicolonString(this.fieldMap.keys)
    }

    fun fieldFromString(str: String) {
        if (AssertUtils.isEmpty(str)) {
            return
        }

        val replace = HashMap<String, Boolean>()
        str.split(";").forEach {
            replace[it] = fieldMap[it] ?: false
        }

        fieldMap = replace;
    }
}