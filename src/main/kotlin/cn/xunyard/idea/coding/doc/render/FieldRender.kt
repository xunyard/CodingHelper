package cn.xunyard.idea.coding.doc.render

import cn.xunyard.idea.coding.doc.model.FieldModel
import cn.xunyard.idea.coding.doc.model.extend.Extend
import com.thoughtworks.qdox.model.JavaField

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-22
 */
interface FieldRender {

    fun provide(field: FieldModel, extends: MutableSet<Extend>): String
}