package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.support.ClassSupport

/**
 * 接口模型，用来定义服务接口
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-11-01
 */
interface InterfaceModel : ClassSupport {

    /**
     * 获取接口中定义的方法
     */
    fun getMethods(): MutableList<MethodModel>

    /**
     * 接口不是基础类型
     */
    override fun isBasicType(): Boolean = false
}