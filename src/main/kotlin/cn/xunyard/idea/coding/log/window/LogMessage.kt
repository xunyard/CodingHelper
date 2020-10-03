package cn.xunyard.idea.coding.log.window

import com.intellij.openapi.project.Project

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
data class LogMessage(
        val project: Project?,
        val identity: String ,
        val operation: LogOperation,
        val format: String?,
        val arguments: Array<out Any>
)