package cn.xunyard.idea.coding.doc.extend

import cn.xunyard.idea.coding.doc.dialog.DocDynamicDialog
import junit.framework.TestCase

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-18
 */
class DialogTest: TestCase() {

    fun testDialog() {
        val dialog = DocDynamicDialog(null)
        dialog.showAndGet()
    }
}