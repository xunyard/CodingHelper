package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.support.ClassSupport
import cn.xunyard.idea.coding.doc.model.support.FieldSupport

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-11-01
 */
interface FieldModel : FieldSupport {

    fun getContainerClass(): ClassSupport
}