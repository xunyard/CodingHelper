package cn.xunyard.idea.coding.i18n.logic

import cn.xunyard.idea.coding.i18n.I18nInspectionSettings
import cn.xunyard.idea.coding.i18n.logic.impl.LanguageManagerImpl
import com.intellij.openapi.project.Project
import java.util.*
import kotlin.collections.HashMap

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class TranslateProcessContext(
        public val inspectionConfiguration: InspectionConfiguration
) {
    private val impl: LanguageManagerImpl = LanguageManagerImpl(inspectionConfiguration)

    fun getTranslateProvider(): LanguageTranslateProvider {
        return this.impl
    }

    fun getLanguageManager(): LanguageManager {
        return this.impl
    }

    companion object {
        private val contextMap: MutableMap<Project, TranslateProcessContext> = WeakHashMap()

        @JvmStatic
        fun getInstance(project: Project): TranslateProcessContext {
            return if (contextMap.containsKey(project)) {
                contextMap[project]!!
            } else {
                var configuration: InspectionConfiguration? = I18nInspectionSettings.getInstance(project).state
                if (configuration == null) {
                    configuration = InspectionConfiguration(HashMap())
                    I18nInspectionSettings.getInstance(project).loadState(configuration)
                }

                return TranslateProcessContext(configuration).apply {
                    contextMap[project] = this
                }
            }
        }
    }
}