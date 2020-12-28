package cn.xunyard.idea.coding.doc.model.extend

import com.thoughtworks.qdox.model.impl.DefaultJavaAnnotation
import com.thoughtworks.qdox.model.impl.DefaultJavaClass
import junit.framework.TestCase


/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
class DocExtendTest : TestCase() {

    fun testLoadExtend() {
        val manager = DocumentExtendManager()
        val clazz = DefaultJavaClass("io.swagger.annotations.ApiModel")
        val annotation = DefaultJavaAnnotation(clazz)
        val annotationExtend = manager.constructFromAnnotation(annotation)
        println(annotationExtend)
    }
}