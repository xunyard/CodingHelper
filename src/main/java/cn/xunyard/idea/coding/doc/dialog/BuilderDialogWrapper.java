package cn.xunyard.idea.coding.doc.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class BuilderDialogWrapper extends DialogWrapper {

    private final Project project;
    private BuilderTutorial buildingTutorial;

    public BuilderDialogWrapper(Project project) {
        super(true);
        this.project = project;
        init();
        setTitle("文档生成");
        this.setResizable(false);
    }

    public BuilderTutorial getBuildingTutorial() {
        return buildingTutorial;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        buildingTutorial = new BuilderTutorial(project);
        return buildingTutorial;
    }
}
