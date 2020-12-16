package cn.xunyard.idea.coding.i18n.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import javax.swing.JComponent

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-10
 */
class AddLanguageDialogWrapper(
        private val project: Project
) : DialogWrapper(project, false) {
    var language: String? = null
    var filepath: String? = null

    init {
        init()
        title = "添加翻译语言"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("语言描述:")
                textField(this@AddLanguageDialogWrapper::returnEmptyStr, { text -> language = text })
                label("翻译文件:")
                textFieldWithBrowseButton(project = project, fileChosen = { vf -> vf.path.apply { filepath = this } })
            }
        }
    }

    fun getLanguageConfiguration(): SingleLanguageConfiguration {
        return SingleLanguageConfiguration(language!!, filepath!!)
    }

    private fun returnEmptyStr(): String = ""
}