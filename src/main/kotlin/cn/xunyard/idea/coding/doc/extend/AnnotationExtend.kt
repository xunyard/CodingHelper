package cn.xunyard.idea.coding.doc.extend

import com.thoughtworks.qdox.model.expression.AnnotationValue

/**
 * 注解符号扩展
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2020-10-02
 */
interface AnnotationExtend : Extend {

    /**
     * 获取注解内所有的属性
     */
    fun getProperties(): MutableMap<String, AnnotationValue>

    /**
     * 获取注解内的属性内容
     */
    fun getProperty(key: String): AnnotationValue?

    fun getPropertyOfBoolean(key: String): Boolean? {
        return getProperty(key)?.takenBoolean()
    }

    fun getPropertyOfString(key: String): String? {
        return getProperty(key)?.toString()
    }

    fun getPropertyOfInt(key: String): Int? {
        return getProperty(key)?.takeInt()
    }

    fun getPropertyOfLong(key: String): Long? {
        return getProperty(key)?.takeLong()
    }

    companion object {
        private fun AnnotationValue.takenBoolean(): Boolean {
            return java.lang.Boolean.parseBoolean(this.toString())
        }

        private fun AnnotationValue.takeInt(): Int {
            return this.toString().toInt()
        }

        private fun AnnotationValue.takeLong(): Long {
            return this.toString().toLong()
        }
    }
}