package cn.xunyard.idea.coding.i18n.logic.impl

import cn.xunyard.idea.coding.i18n.logic.SingleLanguageTranslate
import cn.xunyard.idea.coding.i18n.logic.persistent.TranslateAsyncPersistent
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class SingleLanguageTranslateImpl(
        private val language: String,
        private var filepath: String
) : SingleLanguageTranslate {
    private val translateMap: MutableMap<String, String> = HashMap()

    @Synchronized
    override fun reload(newFilepath: String?) {
        if (newFilepath != null) {
            this.filepath = newFilepath
        }

        translateMap.clear()
        try {
            val file = File(filepath)
            if (!file.exists()) {
                return
            }
            FileReader(file).use { fileReader ->
                BufferedReader(fileReader).use { reader ->
                    var str: String
                    while (reader.readLine().also { str = it } != null) {
                        val property = str.split("=")
                        translateMap[property[0]] = property[1]
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("语言[${language}]的翻译文件处理失败")
        }
    }

    @Synchronized
    override fun free() {
        translateMap.clear()
    }

    override fun missing(errorCode: String): Boolean {
        return !translateMap.containsKey(errorCode)
    }

    override fun getTranslate(errorCode: String): String? {
        return translateMap[errorCode]
    }

    override fun setTranslate(errorCode: String, translate: String) {
        translateMap[errorCode] = translate
        TranslateAsyncPersistent.submit(filepath, errorCode, translate)
    }
}