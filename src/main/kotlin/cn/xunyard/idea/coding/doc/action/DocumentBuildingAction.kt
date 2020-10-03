package cn.xunyard.idea.coding.doc.action

import cn.xunyard.idea.coding.doc.dialog.DocDynamicDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-15
 */
class DocumentBuildingAction : AnAction() {
    private val log = Logger.getInstance(DocumentBuildingAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        log.warn("log tested!")
        val docDynamicDialog = DocDynamicDialog(e.project)
        docDynamicDialog.showAndGet()
    }

}