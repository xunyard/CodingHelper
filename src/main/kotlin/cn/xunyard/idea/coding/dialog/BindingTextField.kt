package cn.xunyard.idea.coding.dialog

import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-06
 */
class BindingJTextField(
        setter: (String?) -> Unit,
        getter: () -> String?,
        enable: Boolean = true,
        toolTip: String? = null
) : JTextField() {

    init {
        this.text = getter.invoke()
        this.toolTipText = toolTip

        addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent) = Unit

            override fun focusLost(e: FocusEvent) {
                setter.invoke(text)
            }
        })
    }
}