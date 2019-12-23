package cn.xunyard.idea.doc;

import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
public class DocLogger {
    private static final String TOOL_WINDOW_ID = "Doc Generator";

    private static ConsoleView getConsoleView(Project project) {
        if (project == null) {
            return null;
        }

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
        if (toolWindow == null) {
            toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        }

        toolWindow.activate(null);
        Content exist = toolWindow.getContentManager().getContent(0);
        if (exist != null) {
            return (ConsoleView) exist.getComponent();
        }

        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "服务文档", false);
        toolWindow.getContentManager().addContent(content);

        return consoleView;
    }

    private enum LogOperation {
        CLEAR,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    @Getter
    @RequiredArgsConstructor
    private static class LogInfo {
        private final Project project;
        private final LogOperation operation;
        private final String content;
    }

    @RequiredArgsConstructor
    public static class RenderLogger implements Runnable {

        private final LogInfo logInfo;

        @Override
        public void run() {
            ConsoleView consoleView = getConsoleView(logInfo.getProject());

            switch (logInfo.getOperation()) {
                case CLEAR:
                    consoleView.clear();
                    ((ConsoleViewImpl) consoleView).requestScrollingToEnd();
                    break;
                case DEBUG:
                    consoleView.print(logInfo.getContent() + "\n", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
                    break;
                case INFO:
                    consoleView.print(logInfo.getContent() + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
                    break;
                case WARN:
                    consoleView.print(logInfo.getContent() + "\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
                    break;
                case ERROR:
                    consoleView.print(logInfo.getContent() + "\n", ConsoleViewContentType.LOG_ERROR_OUTPUT);
                    break;
            }
        }
    }

    private static void submitLog(LogOperation operation, String content) {
        Project project = ProjectUtils.PROJECT.get();
        LogInfo logInfo = new LogInfo(project, operation, content);
        ApplicationManager.getApplication().invokeLater(new RenderLogger(logInfo));
    }

    public static void clear() {
        submitLog(LogOperation.CLEAR, null);
    }

    public static void info(String content) {
        submitLog(LogOperation.INFO, content);
    }

    public static void debug(String content) {
        submitLog(LogOperation.DEBUG, content);
    }

    public static void warn(String content) {
        submitLog(LogOperation.WARN, content);
    }

    public static void error(String content) {
        submitLog(LogOperation.ERROR, content);
    }

}
