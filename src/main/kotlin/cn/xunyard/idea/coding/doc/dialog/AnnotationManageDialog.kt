package cn.xunyard.idea.coding.doc.dialog

import cn.xunyard.idea.coding.doc.config.DialogRenderAnnotation
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import java.awt.Color
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.border.Border

class AnnotationManageDialog(
    project: Project?,
    private val annotationList: MutableList<DialogRenderAnnotation>
) : DialogWrapper(project) {

    init {
        init()
    }

    override fun createCenterPanel(): JComponent {
        val userField = JTextField("", 1)
        panel() {
//            row { userField().growPolicy() }
        }


        return panel {
            for (annotation in annotationList) {
                buttonGroup {

                }
                createNoteOrCommentRow(JPanel().apply { this.background = Color.BLUE }).apply {
                    row("注解名:") {
                        label(annotation.name)
                    }
                    row("所属包:") {
                        label(annotation.pkg)
                    }
                    row("启用配置") {
                        cell {
                            for ((field, chosen) in annotation.fieldMap) {
                                checkBox(field, { chosen }, {})
                            }
                        }
                    }
                }
                blockRow {  }
            }
        }

    }

}