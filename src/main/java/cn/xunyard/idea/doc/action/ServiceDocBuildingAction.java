package cn.xunyard.idea.doc.action;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.dialog.BuilderDialogWrapper;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceDocBuildingService;
import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        ProjectUtils.PROJECT.set(event.getProject());
        BuilderDialogWrapper dialogWrapper = new BuilderDialogWrapper();
        if (dialogWrapper.showAndGet()) {
            DocLogger.clear();
            DocBuildingContext docBuildingContext = dialogWrapper.getBuildingTutorial().makeBuildingContext();
            new ServiceDocBuildingService(docBuildingContext).run(event.getProject().getBasePath());
        }
        ProjectUtils.PROJECT.remove();
    }
}
