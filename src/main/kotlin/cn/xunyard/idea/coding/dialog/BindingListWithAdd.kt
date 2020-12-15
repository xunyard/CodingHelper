package cn.xunyard.idea.coding.dialog

import cn.xunyard.idea.coding.util.AssertUtils
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import org.jdesktop.swingx.VerticalLayout
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Consumer
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-07
 */
class BindingListWithAdd<T>(setter: Consumer<List<T>>?) : JPanel() {
    private lateinit var scrollPane: JScrollPane
    private lateinit var jbList: JBList<T>
    private lateinit var buttonPanel: JPanel
    private var selectedIndex: Int? = null
    private lateinit var addMouseListener: LabelMouseListener
    private lateinit var removeMouseListener: LabelMouseListener
    private val setter: Consumer<List<T>>?
    var listData: MutableList<T>

    init {
        listData = ArrayList()
        this.setter = setter
        initComponent()
    }

    private fun addList() {
        jbList = JBList<T>()
        jbList.model = DataModel<T>(listData)
        scrollPane = JBScrollPane(jbList)
        this.add(scrollPane)
    }

    private fun addButtons() {
        val flowLayout = FlowLayout()
        flowLayout.hgap = 1
        flowLayout.vgap = 1
        buttonPanel = JPanel(flowLayout)
        val addLabel = JLabel(AllIcons.General.Add)
        addLabel.preferredSize = Dimension(20, 20)
        addMouseListener = LabelMouseListener(addLabel)
        addLabel.addMouseListener(addMouseListener)
        buttonPanel.add(addLabel)
        val removeLabel = JLabel(AllIcons.General.Remove)
        removeLabel.preferredSize = Dimension(20, 20)
        removeMouseListener = LabelMouseListener(removeLabel)
        removeLabel.addMouseListener(removeMouseListener)
        buttonPanel.add(removeLabel)
        this.add(buttonPanel)
    }

    private fun initComponent() {
        val verticalLayout = VerticalLayout()
        this.layout = verticalLayout
        addList()
        addButtons()
        removeMouseListener.setAllowRender(false)
        jbList.addListSelectionListener {
            selectedIndex = jbList.selectedIndex
            removeMouseListener.setAllowRender(true)
        }
        removeMouseListener.setActionListener { e -> deleteData(e) }
    }

    fun setAddActionListener(listener: ActionListener?) {
        addMouseListener.setActionListener(listener)
    }

    fun addData(data: T) {
        if (listData.contains(data)) {
            Messages.showMessageDialog("$data 重复", "添加数据错误", Messages.getErrorIcon())
            return
        }
        listData.add(data)
        jbList.model = DataModel<T>(listData)
        jbList.ensureIndexIsVisible(listData.size - 1)
        setter?.accept(listData)
    }

    fun setData(data: Collection<T>) {
        listData = if (AssertUtils.isEmpty(data)) {
            ArrayList()
        } else {
            ArrayList(data)
        }
        jbList.model = DataModel<T>(listData)
        jbList.ensureIndexIsVisible(listData.size - 1)
    }

    private fun deleteData(e: ActionEvent) {
        if (selectedIndex != null) {
            listData.removeAt(selectedIndex!!.toInt())
            jbList.model = DataModel<T>(listData)
            selectedIndex = -1
            removeMouseListener.setAllowRender(false)
        }
    }

    override fun setPreferredSize(preferredSize: Dimension) {
        super.setPreferredSize(preferredSize)
        scrollPane.preferredSize = Dimension(preferredSize.width, preferredSize.height - 25)
        buttonPanel.preferredSize = Dimension(preferredSize.width, 25)
    }

}