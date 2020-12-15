package cn.xunyard.idea.coding.i18n.dialog

import com.intellij.openapi.ui.DialogWrapper
import cn.xunyard.idea.coding.i18n.dialog.AddLanguage
import javax.swing.JComponent
import cn.xunyard.idea.coding.i18n.dialog.SingleLanguageConfiguration
import com.intellij.openapi.project.Project

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-10
 */
class AddLanguageDialogWrapper(private val project: Project) : DialogWrapper(
    project, false
) {
    private val addLanguage: AddLanguage

    init {
        addLanguage = AddLanguage()
        init()
        title = "添加翻译语言"
    }

    override fun createCenterPanel(): JComponent? {
        return addLanguage.createPanel(project)
    }

    fun getLanguageConfiguration(): SingleLanguageConfiguration {
        return addLanguage.languageConfiguration
    }
}