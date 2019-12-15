package cn.xunyard.idea.doc.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class BuilderDialogWrapper extends DialogWrapper {

    public BuilderDialogWrapper() {
        super(true);
        init();
        setTitle("文档生成");

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return new BuildingTutorial();
    }
}
