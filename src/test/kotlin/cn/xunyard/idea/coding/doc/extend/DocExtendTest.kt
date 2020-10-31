package cn.xunyard.idea.coding.doc.extend

import cn.xunyard.idea.coding.doc.extend.preset.annotation.Api
import com.thoughtworks.qdox.model.JavaAnnotation
import com.thoughtworks.qdox.model.impl.DefaultJavaAnnotation
import com.thoughtworks.qdox.model.impl.DefaultJavaClass
import junit.framework.TestCase
import kotlin.reflect.KClass
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf


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
    }
}