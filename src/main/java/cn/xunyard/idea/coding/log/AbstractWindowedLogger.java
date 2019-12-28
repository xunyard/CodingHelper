package cn.xunyard.idea.coding.log;

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
    private static ToolWindow TOOL_WINDOW;
    // 标记上一次的返回结果，以快速返回相同结果
    private static Content lastContent;

    protected static ConsoleView getConsoleView(@NotNull Project project, @NotNull String displayName) {
        if (lastContent != null && lastContent.isValid() && lastContent.getDisplayName().equals(displayName)) {
            return (ConsoleView) lastContent.getComponent();
        }

        ToolWindow toolWindow = getToolWindow(project);
        Content content = getContent(toolWindow, displayName);

        if (content != null) {
            lastContent = content;
        } else {
            lastContent = buildContent(project, displayName, toolWindow.getContentManager());
        }

        return (ConsoleView) lastContent.getComponent();
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
        if (TOOL_WINDOW != null) {
            if (!TOOL_WINDOW.isActive()) {
                TOOL_WINDOW.activate(null);
            }

            return TOOL_WINDOW;
        }

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        TOOL_WINDOW = toolWindowManager.getToolWindow(TOOL_WINDOW_ID);
        if (TOOL_WINDOW == null) {
            TOOL_WINDOW = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        }

        if (!TOOL_WINDOW.isActive()) {
            TOOL_WINDOW.activate(null);
        }

        return TOOL_WINDOW;
    }
}
