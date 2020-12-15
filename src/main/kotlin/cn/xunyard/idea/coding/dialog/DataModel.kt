package cn.xunyard.idea.coding.dialog

import javax.swing.AbstractListModel

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
internal class DataModel<T> constructor(
        private val listData: List<T>
) : AbstractListModel<T>() {

    override fun getElementAt(index: Int): T {
        return listData[index]
    }

    override fun getSize(): Int {
        return listData.size
    }
}