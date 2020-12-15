package cn.xunyard.idea.coding.dialog

import com.intellij.ui.JBColor
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JLabel

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
internal class LabelMouseListener(
        private val label: JLabel
) : MouseListener {

    private var allowRender = true

    fun setAllowRender(allowRender: Boolean) {
        this.allowRender = allowRender
        if (!allowRender) {
            label.isOpaque = false
            label.repaint()
        }
    }

    private var actionListener: ActionListener? = null

    fun setActionListener(listener: ActionListener?) {
        this.actionListener = listener
    }

    override fun mouseClicked(e: MouseEvent) {
        actionListener?.actionPerformed(ActionEvent(label, ActionEvent.ACTION_PERFORMED, "click"))
    }

    override fun mousePressed(e: MouseEvent) {}
    override fun mouseReleased(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {
        if (!allowRender) {
            return
        }
        label.isOpaque = true
        label.repaint()
    }

    override fun mouseExited(e: MouseEvent) {
        if (!allowRender) {
            return
        }
        label.isOpaque = false
        label.repaint()
    }

    init {
        label.background = JBColor.LIGHT_GRAY
    }
}