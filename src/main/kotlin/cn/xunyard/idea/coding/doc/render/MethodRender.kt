package cn.xunyard.idea.coding.doc.render

import cn.xunyard.idea.coding.doc.model.extend.Extend
import com.thoughtworks.qdox.model.JavaField

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-10-22
 */
interface MethodRender {

    fun provide(field: JavaField, extends: MutableSet<Extend>): String
}