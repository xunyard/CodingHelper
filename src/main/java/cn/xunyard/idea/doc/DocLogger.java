package cn.xunyard.idea.doc;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
public class DocLogger {

    private static ConsoleView consoleView;
    private static Project project;
    private static Boolean initialized = false;

    public static void setConsoleView(ConsoleView consoleView) {
        DocLogger.consoleView = consoleView;
    }

    public static void setProject(Project project) {
        DocLogger.project = project;
    }

    private static void initialize() {
        if (!initialized) {
            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Doc Generator");
            Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "服务文档", false);
            toolWindow.getContentManager().addContent(content);
            initialized = true;
        }
    }

    public static void clear() {
        initialize();
        consoleView.clear();
        ((ConsoleViewImpl)consoleView).requestScrollingToEnd();
    }

    public static void info(String content) {
        initialize();
        consoleView.print(content + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
    }

    public static void debug(String content) {
        initialize();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
    }

    public static void warn(String content) {
        initialize();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_WARNING_OUTPUT);
    }

    public static void error(String content) {
        initialize();
        consoleView.print(content + "\n", ConsoleViewContentType.LOG_ERROR_OUTPUT);
    }

}
