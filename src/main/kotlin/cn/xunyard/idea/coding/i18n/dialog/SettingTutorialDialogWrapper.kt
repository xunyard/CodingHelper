package cn.xunyard.idea.coding.i18n.dialog

import cn.xunyard.idea.coding.i18n.TranslateProcessContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.util.*
import java.util.stream.Collectors
import javax.swing.JComponent

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-12
 */
class SettingTutorialDialogWrapper(private val project: Project) : DialogWrapper(false) {
    private lateinit var settingTutorial: SettingTutorial
    override fun createCenterPanel(): JComponent {
        settingTutorial = SettingTutorial()
        val configMap: Map<String, String> = TranslateProcessContext.getInstance(project).inspectionConfiguration.getAll()
        return settingTutorial.createPanel(project, configMap.toConfigList(), configMap)
    }

    override fun doOKAction() {
        val languageConfigurations: List<SingleLanguageConfiguration> = settingTutorial.languageConfigurations
        val new = languageConfigurations.stream()
                .collect(Collectors.toMap({ cfg -> cfg.language }, { cfg -> cfg.filepath }))
        TranslateProcessContext.getInstance(project).inspectionConfiguration.refreshAll(new)
        TranslateProcessContext.getInstance(project).getLanguageManager().reload()
        super.doOKAction()
    }

    init {
        init()
        title = "错误码翻译设置"
    }

    companion object {

        fun Map<String, String>.toConfigList(): List<SingleLanguageConfiguration> {
            if (this.isEmpty()) {
                return Collections.emptyList()
            }

            return this.entries.stream().map { (k, v) -> SingleLanguageConfiguration(k, v) }.collect(Collectors.toList())
        }
    }
}