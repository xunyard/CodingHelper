package cn.xunyard.idea.coding.log

import cn.xunyard.idea.coding.log.window.WindowLogger

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
object LoggerFactory {
    private val loggerMap: MutableMap<String, Logger> = HashMap()

    fun getLogger(identityId: String): Logger {
        if (loggerMap.containsKey(identityId)) {
            return loggerMap[identityId] ?: throw IllegalArgumentException("invalid identity of $identityId")
        }
        val logger = WindowLogger(identityId)
        loggerMap[identityId] = logger
        return logger
    }
}