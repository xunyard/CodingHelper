package cn.xunyard.idea.coding.i18n.logic

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
interface LanguageTranslateProvider {

    /**
     * 获取设置的语言集合
     *
     * @return 语言集合
     */
    fun getLanguages(): Set<String>

    /**
     * 判断错误码是否缺少翻译
     *
     * @param errorCode 错误码
     * @return 缺失翻译返回true，否则false
     */
    fun missing(errorCode: String): Boolean

    /**
     * 获取指定语言的错误码翻译
     *
     * @param language  语言
     * @param errorCode 错误码
     * @return 翻译
     */
    fun getTranslate(language: String, errorCode: String): String?

    /**
     * 设置翻译值
     *
     * @param language  语言
     * @param errorCode 错误码
     * @param translate 翻译
     */
    fun setTranslate(language: String, errorCode: String, translate: String)
}