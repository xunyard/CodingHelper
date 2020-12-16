package cn.xunyard.idea.coding.util

import cn.xunyard.idea.coding.util.AssertUtils.isEmpty
import cn.xunyard.idea.coding.util.AssertUtils
import java.lang.StringBuilder
import java.util.function.Function
import java.util.function.Supplier

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 */
object ObjectUtils {
    fun <T> firstNonNull(first: T?, other: T?): T {
        if (first != null) {
            return first
        }
        requireNotNull(other) { "both.argument.is.null" }
        return other
    }

    fun <T> firstNonNull(first: T?, supplier: Supplier<T>?): T {
        if (first != null) {
            return first
        }
        requireNotNull(supplier) { "supplier.is.null" }
        return supplier.get()
    }

    fun <T, R> takeIfNonNull(source: T?, take: Function<T, R>): R? {
        return if (source == null) {
            null
        } else take.apply(source)
    }

    fun takeBoolean(obj: Any?): Boolean {
        return obj != null && java.lang.Boolean.parseBoolean(obj.toString())
    }

    fun smoothStr(s: String?): String? {
        if (s == null) {
            return null
        }
        val splits = removeQuotation(s).split("\n".toRegex()).toTypedArray()
        val sb = StringBuilder()
        for (split in splits) {
            sb.append(split)
        }
        return sb.toString()
    }

    fun removeQuotation(str: String): String {
        if (isEmpty(str)) {
            return str
        }
        val trim = str.trim { it <= ' ' }
        return if (trim.startsWith("\"")) {
            trim.substring(1, str.length - 1)
        } else trim
    }
}