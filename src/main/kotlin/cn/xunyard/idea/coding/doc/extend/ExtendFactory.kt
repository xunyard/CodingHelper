package cn.xunyard.idea.coding.doc.extend

import com.thoughtworks.qdox.model.JavaClass

/**
 *
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-03
 */
interface ExtendFactory {
    fun construct(javaClass: JavaClass): Extend
}