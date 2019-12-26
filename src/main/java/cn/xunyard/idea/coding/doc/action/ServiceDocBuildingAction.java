package cn.xunyard.idea.coding.doc.action;

import cn.xunyard.idea.coding.doc.DocConfig;
import cn.xunyard.idea.coding.doc.ServiceDocBuildingService;
import cn.xunyard.idea.coding.doc.dialog.BuilderDialogWrapper;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.ProjectUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingAction extends AnAction {
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
    @Override
    public void actionPerformed(AnActionEvent event) {
        ProjectUtils.PROJECT.set(event.getProject());
        BuilderDialogWrapper dialogWrapper = new BuilderDialogWrapper();
        if (dialogWrapper.showAndGet()) {
            log.clear();
            DocConfig docConfig = dialogWrapper.getBuildingTutorial().makeBuildingContext();
            new ServiceDocBuildingService(docConfig).run(event.getProject());
        }
        ProjectUtils.PROJECT.remove();
    }
}
