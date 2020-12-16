package cn.xunyard.idea.coding.i18n.logic.impl

import cn.xunyard.idea.coding.i18n.logic.*
import java.util.HashMap

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
class LanguageManagerImpl constructor(
        private val inspectionConfiguration: InspectionConfiguration
) : LanguageManager, LanguageTranslateProvider {
    private val translateMap: MutableMap<String, SingleLanguageTranslate> = HashMap()

    init {
        loadConfiguration()
    }

    @Synchronized
    override fun loadConfiguration() {
        if (translateMap.isNotEmpty()) {
            throw RuntimeException("language load should only invoke once!")
        }

        for ((language, filepath) in inspectionConfiguration.getAll()) {
            translateMap[language] = createSingleLanguageTranslate(language, filepath)
        }
    }

    @Synchronized
    override fun reload() {
        val toReplace: MutableMap<String, SingleLanguageTranslate> = HashMap()
        for ((language, filepath) in inspectionConfiguration.getAll()) {
            if (translateMap.containsKey(language)) {
                val languageTranslate = translateMap[language]!!.apply { this.reload(filepath) }
                toReplace[language] = languageTranslate
                translateMap.remove(language)
            } else {
                toReplace[language] = createSingleLanguageTranslate(language, filepath)
            }
        }
        translateMap.values.forEach { it.free()}
        translateMap.clear()
        translateMap.putAll(toReplace)
    }

    override fun missing(errorCode: String): Boolean {
        for (translate in translateMap.values) {
            if (translate.missing(errorCode)) {
                return true
            }
        }
        return false
    }

    override fun getLanguages(): Set<String> {
        return translateMap.keys
    }

    override fun getTranslate(language: String, errorCode: String): String? {
        val translate = translateMap[language] ?: throw IllegalArgumentException("指定语言未设置")
        return translate.getTranslate(errorCode)
    }

    override fun setTranslate(language: String, errorCode: String, translate: String) {
        val languageTranslate = translateMap[language] ?: throw IllegalArgumentException("指定语言未设置")
        languageTranslate.setTranslate(errorCode, translate)
    }

    override fun containsLanguage(language: String): Boolean {
        return translateMap.containsKey(language)
    }

    private fun createSingleLanguageTranslate(language: String, filepath: String) =
            SingleLanguageTranslateImpl(language, filepath)
}
