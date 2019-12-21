package cn.xunyard.idea.doc;

import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
public class DocLogger {
    private static final String TOOL_WINDOW_ID = "Doc Generator";

    private static ConsoleView getConsoleView() {
        Project project = ProjectUtils.PROJECT.get();
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

    public static void clear() {
        ConsoleView consoleView = getConsoleView();
        getConsoleView().clear();
        ((ConsoleViewImpl) consoleView).requestScrollingToEnd();
    }

    public static void info(String content) {
        ConsoleView consoleView = getConsoleView();
        consoleView.print(content + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
    }

    public static void debug(String content) {
        ConsoleView consoleView = getConsoleView();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
    }

    public static void warn(String content) {
        ConsoleView consoleView = getConsoleView();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
    }

    public static void error(String content) {
        ConsoleView consoleView = getConsoleView();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_ERROR_OUTPUT);
    }

}
