package cn.xunyard.idea.coding.doc.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import com.intellij.uiDesigner.core.GridLayoutManager
import javax.swing.JComponent
import javax.swing.JPasswordField
import javax.swing.JTextArea
import javax.swing.JTextField

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-03
 */
class DocDynamicDialog constructor(
        private val project: Project?
) : DialogWrapper(project) {

    init {
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("<html>Merging branch <b>foo</b> into <b>bar</b>")
            }
            row {
                scrollPane(DocumentSettings()).constraints(pushX)

                cell(isVerticalFlow = true) {
                    button("Accept Yours") {}.constraints(growX)
                    button("Accept Theirs") {}.constraints(growX)
                    button("Merge ...") {}.constraints(growX)
                    radioButton("234").constraints(growX)
                    label("").constraints(growX)
                }
                cell(isVerticalFlow = true) {
                    button("Accept Yours") {}.constraints(growX)
                    button("Accept Theirs") {}.constraints(growX)
                    button("Merge ...") {}.constraints(growX)
                    radioButton("234").constraints(growX)
                    label("").constraints(growX)
                }
            }
        }
    }
}