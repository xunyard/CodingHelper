package cn.xunyard.idea.doc.action;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.dialog.BuilderDialogWrapper;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceDocBuildingService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        BuilderDialogWrapper dialogWrapper = new BuilderDialogWrapper();
        if (dialogWrapper.showAndGet()) {
            DocLogger.setProject(event.getProject());
            DocLogger.clear();
            ToolWindowManager.getInstance(event.getProject()).getToolWindow("Doc Generator").activate(null);
            DocBuildingContext docBuildingContext = dialogWrapper.getBuildingTutorial().getDocBuildingContext();
            new ServiceDocBuildingService(docBuildingContext).run(event.getProject().getBasePath());
        }
    }
}
