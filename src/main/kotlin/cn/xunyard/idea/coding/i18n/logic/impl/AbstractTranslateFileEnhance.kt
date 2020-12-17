package cn.xunyard.idea.coding.i18n.logic.impl

import cn.xunyard.idea.coding.i18n.TranslateProcessContext
import cn.xunyard.idea.coding.i18n.logic.LanguageManager
import cn.xunyard.idea.coding.i18n.logic.TranslateFileEnhance
import com.intellij.openapi.project.Project

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
abstract class AbstractTranslateFileEnhance : TranslateFileEnhance, LanguageManager {

    override fun reloadFromDisk(project: Project) {
        reload()
    }

    @Synchronized
    override fun sortByName(project: Project) {
        val inspectionConfiguration = TranslateProcessContext.getInstance(project).inspectionConfiguration
        for ((_, filepath) in inspectionConfiguration.languageConfigMap) {
            sortFile(filepath)
        }

        reload()
    }

    @Synchronized
    override fun invalidCheck(project: Project) {
        val inspectionConfiguration = TranslateProcessContext.getInstance(project).inspectionConfiguration
        for ((_, filepath) in inspectionConfiguration.languageConfigMap) {
            invalidCheck(filepath)
        }

        reload()
    }

    private fun sortFile(filepath: String) {

    }

    private fun invalidCheck(filepath: String) {

    }
}