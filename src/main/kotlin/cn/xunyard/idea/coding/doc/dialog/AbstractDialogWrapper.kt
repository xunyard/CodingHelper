package cn.xunyard.idea.coding.doc.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.jetbrains.rd.framework.base.deepClonePolymorphic
import javax.swing.JComponent

abstract class AbstractDialogWrapper<T>(
    project: Project,
    private val content: T?,
    ctor: () -> T
) : DialogWrapper(project) {
    private lateinit var panel: DialogPanel
    private val _content: T = content?.deepClonePolymorphic() ?: ctor.invoke()

    init {
        init()
    }

    abstract fun renderPanel(content: T): DialogPanel

    abstract fun isOk(content: T): Boolean

    fun displayAndGet(): T? {
        check(isModal) { "The showAndGet() method is for modal dialogs only" }
        show()
        return if (isOK) _content else content
    }

    override fun createCenterPanel(): JComponent {
        this.panel = renderPanel(_content)
        return this.panel
    }

    override fun doOKAction() {
        panel.apply()
        if (!okAction.isEnabled) {
            return
        }

        if (!isOk(_content)) {
            return
        }

        close(OK_EXIT_CODE)
    }
}