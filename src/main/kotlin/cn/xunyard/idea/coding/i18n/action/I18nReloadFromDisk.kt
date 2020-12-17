package cn.xunyard.idea.coding.i18n.action

import cn.xunyard.idea.coding.i18n.TranslateProcessContext
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
class I18nReloadFromDisk : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val languageManager = TranslateProcessContext.getInstance(e.project!!).getLanguageManager()
        languageManager.reload()
        Messages.showMessageDialog("翻译文件重载完成", "信息", Messages.getInformationIcon())
    }
}