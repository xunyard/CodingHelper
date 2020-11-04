package cn.xunyard.idea.coding.doc.model

import cn.xunyard.idea.coding.doc.model.extend.AnnotationExtend
import cn.xunyard.idea.coding.doc.model.extend.JavaDocExtend

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-11-01
 */
interface CommentSupport {

    /**
     * 获取注解
     */
    fun getAnnotations(): MutableList<AnnotationExtend>

    /**
     * 获取Java文档
     */
    fun getDocs(): MutableList<JavaDocExtend>
}