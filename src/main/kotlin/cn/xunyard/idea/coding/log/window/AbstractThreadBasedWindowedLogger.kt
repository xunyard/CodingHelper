package cn.xunyard.idea.coding.log.window

import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-26
 */
abstract class AbstractThreadBasedWindowedLogger : AbstractWindowedLogger() {

    private class LogSummit : Runnable {
        private val activatedProject: MutableSet<Project> = HashSet()
        override fun run() {
            var i = 0
            do {
                if (messageQueue.isEmpty()) {
                    Thread.sleep(threadSleepTime)
                    if (i++ > maxWaitingCycleCount) {
                        running = false
                        return
                    }
                } else {
                    i = 0
                    val messages: MutableList<LogMessage> = LinkedList()
                    while (messageQueue.peek() != null) {
                        val message = messageQueue.poll()
                        if (message.operation == LogOperation.DONE) {
                            i = Int.MAX_VALUE
                            break
                        }
                        if (!activatedProject.contains(message.project)) {
                            ApplicationManager.getApplication().invokeLater { activeToolWindow(message.project!!) }
                            activatedProject.add(message.project!!)
                        }
                        messages.add(message)
                    }
                    ApplicationManager.getApplication().invokeLater(LoggerRender(messages))
                }
            } while (true)
        }
    }

    protected fun submitLog(message: LogMessage?) {
        if (!running) {
            synchronized(AbstractThreadBasedWindowedLogger::class.java) {
                if (!running) {
                    running = true
                    executorService.submit(LogSummit())
                }
            }
        }
        messageQueue.add(message)
    }

    companion object {
        private const val threadSleepTime = 200L

        // 最大允许无日志刷出1分钟
        private const val maxWaitingCycleCount = 60 * 1000 / threadSleepTime
        private val messageQueue: Queue<LogMessage> = ConcurrentLinkedQueue()
        private val executorService = Executors.newSingleThreadExecutor()
        private var running = false
    }
}

private class LoggerRender constructor(
        private val logMessages: List<LogMessage>
) : Runnable {
    override fun run() {
        for (logMessage in logMessages) {
            process(logMessage)
        }
    }

    private fun processMessageContent(format: String, vararg args: Any): String {
        return String.format(format, args)
    }

    private fun process(message: LogMessage) {
        val consoleView: ConsoleView = AbstractWindowedLogger.getConsoleView(message.project!!, message.identity)
        if (message.operation == LogOperation.CLEAR) {
            consoleView.clear()
            (consoleView as ConsoleViewImpl).requestScrollingToEnd()
            return
        }
        val contentType: ConsoleViewContentType = when (message.operation) {
            LogOperation.DEBUG -> ConsoleViewContentType.LOG_DEBUG_OUTPUT
            LogOperation.WARN -> ConsoleViewContentType.LOG_WARNING_OUTPUT
            LogOperation.ERROR -> ConsoleViewContentType.LOG_ERROR_OUTPUT
            else -> ConsoleViewContentType.SYSTEM_OUTPUT
        }
        consoleView.print(processMessageContent(message.format!!, *message.arguments), contentType)
    }
}