package cn.xunyard.idea.coding.log.window

import cn.xunyard.idea.coding.log.Logger
import cn.xunyard.idea.coding.util.ProjectUtils.getCurrentProject

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
class WindowLogger constructor(
        private val identity: String
) : AbstractThreadBasedWindowedLogger(), Logger {

    private fun buildLogMessage(operation: LogOperation, format: String?, vararg args: Any): LogMessage {
        return LogMessage(getCurrentProject(), identity, operation, format, args)
    }

    override fun clear() {
        submitLog(buildLogMessage(LogOperation.CLEAR, null))
    }

    override fun debug(format: String, vararg args: Any) {
        submitLog(buildLogMessage(LogOperation.DEBUG, format, *args))
    }

    override fun info(format: String, vararg args: Any) {
        submitLog(buildLogMessage(LogOperation.INFO, format, *args))
    }

    override fun warn(format: String, vararg args: Any) {
        submitLog(buildLogMessage(LogOperation.WARN, format, *args))
    }

    override fun error(format: String, vararg args: Any) {
        submitLog(buildLogMessage(LogOperation.ERROR, format, *args))
    }

    override fun done() {
        submitLog(buildLogMessage(LogOperation.DONE, null))
    }
}