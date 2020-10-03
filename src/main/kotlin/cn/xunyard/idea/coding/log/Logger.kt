package cn.xunyard.idea.coding.log

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
interface Logger {
    fun clear()

    fun debug(format: String, vararg args: Any)

    fun info(format: String, vararg args: Any)

    fun warn(format: String, vararg args: Any)

    fun error(format: String, vararg args: Any)

    fun done()
}