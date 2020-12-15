package cn.xunyard.idea.coding.doc.model.extend

import cn.xunyard.idea.coding.doc.model.extend.preset.annotation.DynamicAnnotation
import com.intellij.openapi.diagnostic.Logger
import com.thoughtworks.qdox.model.JavaAnnotation
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSubtypeOf

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
class DocumentExtendManager {
    private lateinit var annotationExtends: MutableList<AnnotationExtend>
    private lateinit var javaDocExtends: MutableList<JavaDocExtend>
    private val extendConstructFactories = HashMap<String, KFunction<AnnotationExtend>>()
    private val log = Logger.getInstance(DocumentExtendManager::class.java)

    init {
        loadFromPreset()
    }

    fun constructFromAnnotation(annotation: JavaAnnotation): AnnotationExtend {
        val name = annotation.type.name
        if (extendConstructFactories.containsKey(name)) {
            val construct = extendConstructFactories[name]
            return construct!!.call(annotation)
        }

        return DynamicAnnotation(annotation)
    }

    private fun loadFromPreset() {
        val presetPackage = "${this.javaClass.`package`.name}.preset.annotation"

        registerAnnotationExtend("$presetPackage.Api", "io.swagger.annotations")
        registerAnnotationExtend("$presetPackage.ApiModel", "io.swagger.annotations")
        registerAnnotationExtend("$presetPackage.ApiModelProperty", "io.swagger.annotations")
    }

    private fun registerAnnotationExtend(fullName: String, `package`: String) {
        val kClass = Class.forName(fullName).kotlin
        if (!kClass.isSubclassOf(AnnotationExtend::class)) {
            throw IllegalArgumentException("invalid.class.of.annotation.extend")
        }

        // 以JavaAnnotation为入参的构造方法
        val construct = kClass.constructors.firstOrNull { it.parameters.size == 1 &&
                    it.parameters.size == 1 && it.parameters[0].type.isSubtypeOf(JavaAnnotation::class.createType())
        } ?: throw IllegalArgumentException("extend.class.should.construct.with.java.annotation.element")

        val annotationFullName = "${`package`}.${kClass.simpleName!!}"
        extendConstructFactories[annotationFullName]  = construct as KFunction<AnnotationExtend>
        log.warn("[CH-Extend] Successfully load extend '$annotationFullName' of $kClass")
    }
}