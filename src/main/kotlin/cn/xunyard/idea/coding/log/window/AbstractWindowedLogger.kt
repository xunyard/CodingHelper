package cn.xunyard.idea.coding.log.window

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentManager

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
abstract class AbstractWindowedLogger {
    companion object {
        /**
         * 同plugin.xml中的id
         */
        private const val TOOL_WINDOW_ID = "Coding Helper"

        @JvmStatic
        internal fun getConsoleView(project: Project, displayName: String): ConsoleView {
            val toolWindow = getToolWindow(project)
            val content = getConsoleView(toolWindow, displayName)
                    ?: buildConsoleView(project, displayName, toolWindow.contentManager)
            return content.component as ConsoleView
        }

        private fun getConsoleView(toolWindow: ToolWindow, displayName: String): Content? {
            val contents = toolWindow.contentManager.contents
            if (contents.isEmpty()) {
                return null
            }
            return contents.firstOrNull { it.displayName == displayName }
        }

        private fun buildConsoleView(project: Project, displayName: String, contentManager: ContentManager): Content {
            val consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
            return contentManager.factory.createContent(consoleView.component, displayName, false).apply {
                contentManager.addContent(this)
            }
        }

        private fun getToolWindow(project: Project): ToolWindow {
            val toolWindowManager = ToolWindowManager.getInstance(project)
            return toolWindowManager.getToolWindow(TOOL_WINDOW_ID)!!
        }

        fun activeToolWindow(project: Project) {
            val toolWindowManager = ToolWindowManager.getInstance(project)
            val toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID)!!
            if (!toolWindow.isActive) {
                toolWindow.activate(null)
            }
        }
    }
}