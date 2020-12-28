package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.support.ClassSupport

/**
 * 简单类模型
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-18
 */
interface SimpleClassModel : ClassSupport {

    /**
     * 获取自己的派生类
     */
    fun getSuper(): ClassSupport?
}