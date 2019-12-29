package cn.xunyard.idea.coding.log.window;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
public abstract class AbstractWindowedLogger {
    private static final String TOOL_WINDOW_ID = "Coding Helper";

    protected static ConsoleView getConsoleView(@NotNull Project project, @NotNull String displayName) {
        ToolWindow toolWindow = getToolWindow(project);
        Content content = getContent(toolWindow, displayName);

        if (content == null) {
            content = buildContent(project, displayName, toolWindow.getContentManager());
        }

        return (ConsoleView) content.getComponent();
    }

    private static Content getContent(ToolWindow toolWindow, String displayName) {
        Content[] contents = toolWindow.getContentManager().getContents();

        if (contents.length == 0) {
            return null;
        }

        for (Content content : contents) {
            if (content.getDisplayName().equals(displayName)) {
                return content;
            }
        }

        return null;
    }

    private static Content buildContent(Project project, String displayName, ContentManager contentManager) {
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = contentManager.getFactory().createContent(consoleView.getComponent(), displayName, false);
        contentManager.addContent(content);

        return content;
    }

    private static ToolWindow getToolWindow(@NotNull Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);

        if (toolWindow == null) {
            toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
            toolWindow.activate(null);
        }

        return toolWindow;
    }

    public static void activeToolWindow(@NotNull Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);

        if (toolWindow == null) {
            toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        }

        if (!toolWindow.isActive()) {
            toolWindow.activate(null);
        }
    }
}
