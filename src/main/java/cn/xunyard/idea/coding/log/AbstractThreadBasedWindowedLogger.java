package cn.xunyard.idea.coding.log;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.application.ApplicationManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
public abstract class AbstractThreadBasedWindowedLogger extends AbstractWindowedLogger {

    // 最大允许无日志刷出1分钟
    private static final Integer MAX_LOG_WAIT_CYCLE_COUNT = 60 * 1000 / 50;

    private static final Queue<LogMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static boolean LOG_RUNNING = false;

    public static class LogSummit implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            int i = 0;
            do {

                if (messageQueue.isEmpty()) {
                    Thread.sleep(50);
                    if (i++ > MAX_LOG_WAIT_CYCLE_COUNT) {
                        LOG_RUNNING = false;
                        return;
                    }
                } else {
                    i = 0;
                    List<LogMessage> messages = new LinkedList<>();
                    while (messageQueue.peek() != null) {
                        messages.add(messageQueue.poll());
                    }

                    ApplicationManager.getApplication().invokeLater(new RenderLogger(messages));
                }
            } while (true);
        }
    }

    @RequiredArgsConstructor
    public static class RenderLogger implements Runnable {

        private final List<LogMessage> logMessages;

        @SneakyThrows
        @Override
        public void run() {
            for (LogMessage logMessage : logMessages) {
                process(logMessage);
            }
        }

        private String processMessageContent(String format, Object... args) {
            return String.format(format + "\n", args);
        }

        private void process(LogMessage message) {
            ConsoleView consoleView = getConsoleView(message.getProject(), message.getIdentity());

            if (message.getOperation() == LogOperation.CLEAR) {
                consoleView.clear();
                ((ConsoleViewImpl) consoleView).requestScrollingToEnd();
                return;
            }

            ConsoleViewContentType contentType;
            switch (message.getOperation()) {
                case DEBUG:
                    contentType = ConsoleViewContentType.LOG_DEBUG_OUTPUT;
                    break;
                case WARN:
                    contentType = ConsoleViewContentType.LOG_WARNING_OUTPUT;
                    break;
                case ERROR:
                    contentType = ConsoleViewContentType.LOG_ERROR_OUTPUT;
                    break;
                default:
                    contentType = ConsoleViewContentType.SYSTEM_OUTPUT;
            }

            consoleView.print(processMessageContent(message.getFormat(), message.getArguments()), contentType);
        }
    }

    protected void submitLog(LogMessage message) {
        if (!LOG_RUNNING) {
            synchronized (AbstractThreadBasedWindowedLogger.class) {
                if (!LOG_RUNNING) {
                    LOG_RUNNING = true;
                    executorService.submit(new LogSummit());
                }
            }
        }
        messageQueue.add(message);
    }
}
