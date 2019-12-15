package cn.xunyard.idea.doc.action;

import cn.xunyard.idea.doc.dialog.BuilderDialogWrapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        new BuilderDialogWrapper().showAndGet();
    }
}
