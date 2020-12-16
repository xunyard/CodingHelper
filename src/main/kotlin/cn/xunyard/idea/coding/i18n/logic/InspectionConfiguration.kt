package cn.xunyard.idea.coding.i18n.logic

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-18
 */
data class InspectionConfiguration(
        val languageConfigMap: MutableMap<String, String>
) {

    fun getAll(): Map<String, String> {
        return this.languageConfigMap
    }

    fun refreshAll(new: Map<String, String>) {
        languageConfigMap.clear()
        languageConfigMap.putAll(new)
    }

    /**
     * 判断是否已有语言项
     *
     * @param language 语言项
     * @return 已有返回true，否则false
     */
    fun contains(language: String): Boolean {
        return languageConfigMap.containsKey(language)
    }

    companion object {
        const val PROPERTIES_SUFFIX = ".properties"
    }
}