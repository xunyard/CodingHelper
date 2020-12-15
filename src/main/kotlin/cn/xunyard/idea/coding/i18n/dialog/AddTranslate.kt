package cn.xunyard.idea.coding.i18n.dialog

import cn.xunyard.idea.coding.dialog.BindingTextField
import cn.xunyard.idea.coding.i18n.logic.LanguageTranslateProvider
import cn.xunyard.idea.coding.util.AssertUtils.isEmpty
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.util.ui.JBUI
import java.awt.*
import java.util.*
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class AddTranslate constructor(
        errorCode: String,
        translateProvider: LanguageTranslateProvider
) : JPanel() {
    private val jTextFieldList: MutableList<JTextField>
    private var focused = false

    private fun addTitle(errorCode: String) {
        val container = Panel(FlowLayout())
        val label = Label("错误码: $errorCode")
        container.add(label)
        this.add(container)
    }

    private fun addTranslate(errorCode: String, translateProvider: LanguageTranslateProvider) {
        val layout = GridBagLayout()
        layout.columnWidths = intArrayOf(50, 300)
        val panel = Panel(layout)
        var i = 0
        for (language in translateProvider.getLanguages()) {
            addSingleTranslate(panel, i++, language, { translate: String? -> translateProvider.setTranslate(language, errorCode, translate!!) },
                    translateProvider.getTranslate(language, errorCode))
        }
        panel.preferredSize = Dimension(400, 35 * i)
        this.add(panel)
    }

    val allTextFields: List<JTextField>
        get() = jTextFieldList

    private fun addSingleTranslate(container: Panel,
                                   index: Int,
                                   language: String,
                                   setter: Consumer<String>,
                                   translate: String?) {
        val languageConstraints = GridBagConstraints(0, index, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insets(0), 0, 0)
        container.add(JLabel(language), languageConstraints)
        val translateConstraints = GridBagConstraints(1, index, 1, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insets(0), 0, 0)
        val textField = BindingTextField.BindingTextFieldBuilder.from(setter)
                .text(translate)
                .build()
        if (!isEmpty(translate)) {
            textField.isEnabled = false
            textField.toolTipText = "已设置的翻译，禁止在此编辑"
        } else if (!focused) {
            textField.requestFocus(true)
            focused = true
        }
        textField.preferredSize = Dimension(290, 35)
        jTextFieldList.add(textField)
        container.add(textField, translateConstraints)
    }

    init {
        this.layout = VerticalFlowLayout()
        addTitle(errorCode)
        jTextFieldList = ArrayList()
        addTranslate(errorCode, translateProvider)
    }
}