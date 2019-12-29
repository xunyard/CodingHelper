package cn.xunyard.idea.coding.doc.action;

import cn.xunyard.idea.coding.doc.dialog.BuilderDialogWrapper;
import cn.xunyard.idea.coding.doc.dialog.BuilderTutorial;
import cn.xunyard.idea.coding.doc.logic.DocConfig;
import cn.xunyard.idea.coding.doc.logic.DocumentBuildingService;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.ProjectUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class DocumentBuildingAction extends AnAction {
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);

    @Override
    public void actionPerformed(AnActionEvent event) {
        final Project project = event.getProject();
        ProjectUtils.switchProject(project);
        BuilderDialogWrapper dialogWrapper = new BuilderDialogWrapper(project);
        if (dialogWrapper.showAndGet()) {
            process(dialogWrapper.getBuildingTutorial());
        }
    }

    private void process(BuilderTutorial builderTutorial) {
        DocConfig docConfig = builderTutorial.makeBuildingContext();
        DocumentBuildingService service = new DocumentBuildingService(docConfig);
        service.run();
    }
}
