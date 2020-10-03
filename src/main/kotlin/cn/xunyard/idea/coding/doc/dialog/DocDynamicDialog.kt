package cn.xunyard.idea.coding.doc.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.LCFlags
import com.intellij.ui.layout.panel
import javax.swing.JComponent
import javax.swing.JPasswordField
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
        val passwordField = JPasswordField()
        val textField = JTextField()

        val annotationList = panel {

        }

        return panel(LCFlags.flowY) {
            noteRow("Login to get notified when the submitted\nexceptions are fixed.")
            row("Username:") { label("123") }
            row("Password:") { passwordField() }
            row("Parameters:") {
                textField()
            }
            row {
                right {
                    link("Forgot password?") { /* custom action */ }
                }
            }
            noteRow("""Do not have an account? <a href="https://account.jetbrains.com/login">Sign Up</a>""")
        }
    }
}