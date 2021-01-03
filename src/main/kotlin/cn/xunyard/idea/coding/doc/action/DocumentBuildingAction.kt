package cn.xunyard.idea.coding.doc.action

import cn.xunyard.idea.coding.doc.config.DialogRenderAnnotation
import cn.xunyard.idea.coding.doc.dialog.AnnotationManageDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.util.*
import kotlin.collections.HashMap

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-15
 */
class DocumentBuildingAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val annotationList: MutableList<DialogRenderAnnotation> = LinkedList()
        val map = HashMap<String, Boolean>()
        map.put("value", true)
        map.put("size", false)
        val annotation = DialogRenderAnnotation("ApiModel", "io.swagger", false, map)
        annotationList.add(annotation)
        val docDynamicDialog = AnnotationManageDialog(e.project, annotationList)
        docDynamicDialog.showAndGet()
    }
}