package cn.xunyard.idea.coding.i18n.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import cn.xunyard.idea.coding.i18n.dialog.SettingTutorialDialogWrapper

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-12
 */
class I18nSettingsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialogWrapper = SettingTutorialDialogWrapper(e.project!!)
        dialogWrapper.showAndGet()
    }
}