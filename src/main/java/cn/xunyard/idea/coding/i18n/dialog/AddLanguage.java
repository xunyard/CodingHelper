package cn.xunyard.idea.coding.i18n.dialog;

import cn.xunyard.idea.coding.i18n.logic.InspectionConfigurationOperator;
import cn.xunyard.idea.coding.i18n.logic.LanguageConfiguration;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-10
 */
public class AddLanguage extends JPanel {
    private JTextField languageTextField;
    private TextFieldWithBrowseButton filepathBrowseButton;
    private JPanel wholePanel;

    public JComponent createPanel(Project project) {
        filepathBrowseButton.addActionListener(e -> {
            VirtualFile virtualFile = FileChooser.chooseFile(
                    FileChooserDescriptorFactory.createSingleFileDescriptor(InspectionConfigurationOperator.PROPERTIES_SUFFIX),
                    project, null);

            if (virtualFile != null) {
                filepathBrowseButton.setText(virtualFile.getPath());
            }
        });

        return wholePanel;
    }

    public LanguageConfiguration getLanguageConfiguration() {
        LanguageConfiguration languageConfiguration = new LanguageConfiguration();
        languageConfiguration.setLanguage(languageTextField.getText());
        languageConfiguration.setFilepath(filepathBrowseButton.getText());
        return languageConfiguration;
    }
}
