package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.support.ClassSupport

/**
 * 存在泛型参数的类模型
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-18
 */
interface ParameterizedClassModel : SimpleClassModel, ClassSupport {

    /**
     * 获取泛型参数
     */
    fun getParameter(): MutableList<ClassSupport>
}