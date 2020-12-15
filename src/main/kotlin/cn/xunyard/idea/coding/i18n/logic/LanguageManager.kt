package cn.xunyard.idea.coding.i18n.logic

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-18
 */
interface LanguageManager: LanguageReloadable {

    /**
     * 重新加载，包括配置与具体的翻译内容
     */
    fun loadConfiguration()

    /**
     * 判断是否已有语言项
     *
     * @param language 语言项
     * @return 已有返回true，否则false
     */
    fun containsLanguage(language: String): Boolean
}