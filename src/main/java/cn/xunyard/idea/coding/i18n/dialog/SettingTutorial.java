package cn.xunyard.idea.coding.i18n.dialog;

import cn.xunyard.idea.coding.dialog.BindingListWithAdd;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-12
 */
public class SettingTutorial {

    private JPanel wholePanel;
    private BindingListWithAdd<SingleLanguageConfiguration> selectTool;

    public void createUIComponents() {
        selectTool = new BindingListWithAdd<>(null);
    }

    public JComponent createPanel(Project project, List<SingleLanguageConfiguration> configurations, Map<String, String> languageConfigMap) {
        selectTool.setData(configurations);
        selectTool.setAddActionListener(e -> {
            AddLanguageDialogWrapper addLanguageDialogWrapper = new AddLanguageDialogWrapper(project);
            if (addLanguageDialogWrapper.showAndGet()) {
                SingleLanguageConfiguration languageConfiguration = addLanguageDialogWrapper.getLanguageConfiguration();

                if (languageConfigMap.containsKey(languageConfiguration.getLanguage())) {
                    Messages.showMessageDialog(String.format("已包含同名的语言项:%s", languageConfiguration.getLanguage()),
                            "错误", Messages.getErrorIcon());
                    return;
                }

                selectTool.addData(languageConfiguration);
            }
        });

        return wholePanel;
    }

    public List<SingleLanguageConfiguration> getLanguageConfigurations() {
        return this.selectTool.getListData();
    }
}
