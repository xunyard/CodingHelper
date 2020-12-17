package cn.xunyard.idea.coding.i18n.dialog

import cn.xunyard.idea.coding.i18n.logic.LanguageTranslateProvider
import cn.xunyard.idea.coding.util.AssertUtils
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.layout.panel
import javax.swing.Action
import javax.swing.JComponent

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class AddTranslateDialogWrapper(
    project: Project?,
    private val errorCode: String,
    private val translateProvider: LanguageTranslateProvider
) : DialogWrapper(project) {
    private lateinit var translateDetailMap: MutableMap<String, String?>
    private lateinit var panel : JComponent

    override fun createCenterPanel(): JComponent {
        translateDetailMap = HashMap()

        this@AddTranslateDialogWrapper.panel =  panel {
            titledRow("错误码: $errorCode") {
                for (language in translateProvider.getLanguages()) {
                    val translate = translateProvider.getTranslate(language, errorCode)
                    translateDetailMap[language] = translate
                    row(language) {
                        textField(
                            { translate ?: "" },
                            { str -> translateDetailMap[language] = str }).enabled(translate == null)
                    }
                }
            }
        }

        return panel
    }

    override fun doOKAction() {
        (panel as DialogPanel).apply()
        
        // 检查配置操作，并执行
        for ((_, translate) in translateDetailMap) {
            if (AssertUtils.isEmpty(translate)) {
                Messages.showMessageDialog("部分翻译尚未填写", "错误", Messages.getErrorIcon())
                return
            }
        }
//        val missingTranslate = translateDetailMap.values.stream().filter(AssertUtils::isEmpty).findAny()
//        if (!missingTranslate.isEmpty) {
//            Messages.showMessageDialog("部分翻译尚未填写", "错误", Messages.getErrorIcon())
//            return
//        }
        for ((language, translate) in translateDetailMap) {
            translateProvider.setTranslate(language, errorCode, translate!!)
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