package cn.xunyard.idea.coding.i18n.dialog

import cn.xunyard.idea.coding.i18n.logic.LanguageTranslateProvider
import cn.xunyard.idea.coding.util.AssertUtils.isEmpty
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import javax.swing.Action
import javax.swing.JComponent

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class AddTranslateDialogWrapper(project: Project?,
                                private val errorCode: String,
                                private val translateProvider: LanguageTranslateProvider
) : DialogWrapper(project) {
    private lateinit var addTranslate: AddTranslate

    override fun createCenterPanel(): JComponent {
        addTranslate = AddTranslate(errorCode, translateProvider)
        return addTranslate
    }

    override fun doOKAction() {
        // 检查配置操作，并执行
        for (textField in addTranslate.allTextFields) {
            if (isEmpty(textField.text)) {
                Messages.showMessageDialog("部分翻译尚未填写", "错误", Messages.getErrorIcon())
                return
            }
        }
        super.doOKAction()
    }

    override fun doCancelAction() {
        // 什么都不做，禁止关闭对话框
    }

    /**
     * 覆写，不返回取消按钮
     *
     * @return 无取消按钮的action集合
     */
    override fun createActions(): Array<Action> {
        val helpAction = helpAction
        return if (helpAction === myHelpAction && helpId == null) arrayOf(okAction) else arrayOf(okAction, helpAction)
    }

    init {
        init()
        title = "设置错误码翻译"
    }
}