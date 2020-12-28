package cn.xunyard.idea.coding.doc.action

import cn.xunyard.idea.coding.doc.dialog.DocumentTemplateSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-15
 */
class DocumentBuildingAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val docDynamicDialog = DocumentTemplateSettings(e.project)
        docDynamicDialog.showAndGet()
    }
}