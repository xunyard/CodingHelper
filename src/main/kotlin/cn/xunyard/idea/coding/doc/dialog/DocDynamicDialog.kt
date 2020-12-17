package cn.xunyard.idea.coding.doc.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import javax.swing.JComponent

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
                    label("234").constraints(growX)
                }
                cell(isVerticalFlow = true) {
                    button("Accept Yours") {}.constraints(growX)
                    button("Accept Theirs") {}.constraints(growX)
                    button("Merge ...") {}.constraints(growX)
                    radioButton("234").constraints(growX)
                    label("43").constraints(growX)
                 }
                cell(isVerticalFlow = true) {
                    button("Accept Yours") {}.constraints(growX)
                    button("Accept Theirs") {}.constraints(growX)
                    button("Merge ...") {}.constraints(growX)
                    radioButton("234").constraints(growX)
                    label("23452").constraints(growX)
                }
            }
        }
    }
}