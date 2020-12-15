package cn.xunyard.idea.coding.i18n

import cn.xunyard.idea.coding.i18n.logic.InspectionConfiguration
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
@State(name = "I18nInspectionSettings", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class I18nInspectionSettings : PersistentStateComponent<InspectionConfiguration> {
    private var i18nInspectionConfiguration: InspectionConfiguration? = null

    override fun getState(): InspectionConfiguration? {
        return i18nInspectionConfiguration
    }

    override fun loadState(state: InspectionConfiguration) {
        i18nInspectionConfiguration = state
    }

    companion object {
        fun getInstance(project: Project): I18nInspectionSettings {
            return ServiceManager.getService(project, I18nInspectionSettings::class.java)
        }
    }
}