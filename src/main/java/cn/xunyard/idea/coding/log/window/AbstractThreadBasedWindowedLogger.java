package cn.xunyard.idea.coding.log.window;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
public abstract class AbstractThreadBasedWindowedLogger extends AbstractWindowedLogger {

    private static final Integer THREAD_SLEEP_TIME = 200;
    // 最大允许无日志刷出1分钟
    private static final Integer MAX_LOG_WAIT_CYCLE_COUNT = 60 * 1000 / THREAD_SLEEP_TIME;

    private static final Queue<LogMessage> messageQueue = new ConcurrentLinkedQueue<>();
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static boolean LOG_RUNNING = false;

    public static class LogSummit implements Runnable {

        private final Set<Project> activatedProject = new HashSet<>();

        @SneakyThrows
        @Override
        public void run() {
            int i = 0;
            do {
                if (messageQueue.isEmpty()) {
                    Thread.sleep(THREAD_SLEEP_TIME);
                    if (i++ > MAX_LOG_WAIT_CYCLE_COUNT) {
                        LOG_RUNNING = false;
                        return;
                    }
                } else {
                    i = 0;
                    List<LogMessage> messages = new LinkedList<>();
                    while (messageQueue.peek() != null) {
                        LogMessage message = messageQueue.poll();

                        if (message.getOperation() == LogOperation.DONE) {
                            i = Integer.MAX_VALUE;
                            break;
                        }

                        if (!activatedProject.contains(message.getProject())) {
                            ApplicationManager.getApplication().invokeLater(() -> activeToolWindow(message.getProject()));
                            activatedProject.add(message.getProject());
                        }

                        messages.add(message);
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
