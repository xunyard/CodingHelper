package cn.xunyard.idea.coding.doc.dialog

import cn.xunyard.idea.coding.doc.config.DialogRenderAnnotation
import cn.xunyard.idea.coding.util.AssertUtils
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.Messages
import com.intellij.ui.layout.panel

class AddOrEditAnnotationDialog(
    project: Project,
    annotation: DialogRenderAnnotation?
) : AbstractDialogWrapper<DialogRenderAnnotation>(project, annotation, { DialogRenderAnnotation() }) {
    private val isCreate =
        annotation == null || AssertUtils.isEmpty(annotation.name) || AssertUtils.isEmpty(annotation.pkg)

    override fun renderPanel(content: DialogRenderAnnotation): DialogPanel {
        return panel {
            titledRow(if (isCreate) "" else "") {
                row("注解名:") {
                    textField(content::name, { content.name = it })
                }
                row("所属包:") {
                    textField(content::pkg, { content.pkg = it })
                }
                row("可选值:") {
                    textField(content::fieldToString, content::fieldFromString)
                }
            }
        }
    }

    override fun isOk(content: DialogRenderAnnotation): Boolean {
        if (AssertUtils.isEmpty(content.name) || AssertUtils.isEmpty(content.pkg)) {
            Messages.showMessageDialog("注释名与所属包都不可为空", "错误", Messages.getErrorIcon())
            return false
        }

        return true
    }
}