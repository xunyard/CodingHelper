package cn.xunyard.idea.coding.util

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-15
 */
object AssertUtils {
    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }

    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }
}