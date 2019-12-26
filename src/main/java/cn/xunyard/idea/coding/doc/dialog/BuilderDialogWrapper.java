package cn.xunyard.idea.coding.doc.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class BuilderDialogWrapper extends DialogWrapper {

    private BuilderTutorial buildingTutorial;

    public BuilderDialogWrapper() {
        super(true);
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
        buildingTutorial = new BuilderTutorial();
        return buildingTutorial;
    }
}
