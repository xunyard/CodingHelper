package cn.xunyard.idea.coding.doc.dialog;

import cn.xunyard.idea.coding.doc.logic.DocConfig;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-21
 */
public class BuilderTutorial extends JPanel {

    private final Project project;

    /**
     * 文件后缀，用于区分java文件
     */
    private JTextField fileSuffixTextField;

    /**
     * 接口后缀，比如Facade
     */
    private JTextField suffixTextField;

    /**
     * 包路径匹配
     */
    private JTextField prefixTextField;

    /**
     * 输出服务列表
     */
    private JCheckBox logServiceCheckBox;

    /**
     * 输出无法解析的内容
     */
    private JCheckBox logUnsolvedCheckBox;

    /**
     * 文档输出目录
     */
    private TextFieldWithBrowseButton outputDirectoryTextWithBrowseButton;

    /**
     * 文档文件名
     */
    private JTextField outputFileNameTextField;

    /**
     * 允许信息缺失
     */
    private JCheckBox allowInfoMissingCheckBox;

    /**
     * 返回包装
     */
    private JTextArea returnPackTextArea;

    public BuilderTutorial(Project project) {
        this.project = project;
        this.setLayout(new VerticalLayout());
        this.setPreferredSize(new Dimension(600, 500));
        initComponents();
    }

    private void initComponents() {
        renderBasic();
        renderService();
        renderReturn();
        renderOutput();
    }

    public DocConfig makeBuildingContext() {
        DocConfig docConfig = new DocConfig(suffixTextField.getText(), prefixTextField.getText(),
                outputDirectoryTextWithBrowseButton.getText(), outputFileNameTextField.getText());
        docConfig.setLogServiceDetail(logServiceCheckBox.isSelected());
        docConfig.setLogUnresolved(logUnsolvedCheckBox.isSelected());
        docConfig.setAllowInfoMissing(allowInfoMissingCheckBox.isSelected());
        docConfig.setReturnPackList(Arrays.asList(returnPackTextArea.getText().split("\n")));

        return docConfig;
    }

    private JPanel createContainer(String title) {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{25, 575};
        gridBagLayout.rowHeights = new int[]{45, 0};

        TitledSeparator titledSeparator = new TitledSeparator(title);
        titledSeparator.setPreferredSize(new Dimension(600, 45));
        GridBagConstraints gridBagConstraints = buildGeneralConstraints(0, 0, 2, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, JBUI.insets(0));

        JPanel container = new JPanel(gridBagLayout);
        container.add(titledSeparator, gridBagConstraints);

        return container;
    }

    private JPanel createHolder(JPanel container) {
        JPanel holder = new JPanel(new VerticalLayout());
        GridBagConstraints gridBagConstraints = buildGeneralConstraints(1, 1, 1);
        container.add(holder, gridBagConstraints);
        return holder;
    }

    private void addHolderContent(JPanel holder, JComponent content, int rowIndex) {
        GridBagConstraints gridBagConstraints = buildGeneralConstraints(1, rowIndex, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insets(0));
        holder.add(content, gridBagConstraints);
    }

    private void addGroupContent(JComponent content, JPanel groupPanel, int gridX, int gridY) {
        GridBagConstraints constraints = buildGeneralConstraints(gridX, gridY, 1);
        groupPanel.add(content, constraints);
    }

    private GridBagConstraints buildGeneralConstraints(int gridX, int gridY, int gridWidth) {
        return new GridBagConstraints(gridX, gridY, gridWidth, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insetsBottom(5), 0, 0);
    }

    private GridBagConstraints buildGeneralConstraints(int gridX, int gridY, int gridWidth, int gridHeight,
                                                       int anchor, int fill, Insets insets) {
        return new GridBagConstraints(gridX, gridY, gridWidth, gridHeight, 0.0, 0.0, anchor,
                fill, insets, 0, 0);
    }

    private void renderBasic() {
        JPanel container = createContainer("\u8fd0\u884c\u57fa\u7840\u914d\u7f6e");
        JPanel holder = createHolder(container);

        int index = 0;

        allowInfoMissingCheckBox = new JCheckBox();
        allowInfoMissingCheckBox.setText("\u5141\u8bb8\u4fe1\u606f\u7f3a\u5931(\u4f7f\u7528\u540d\u79f0\u4ee3\u66ff)");
        allowInfoMissingCheckBox.setSelected(true);
        addHolderContent(holder, allowInfoMissingCheckBox, index);

        this.add(container);
    }

    private void addLineContent(String title, JComponent content, AbstractButton clickable, JPanel groupPanel, int rowIndex) {
        JLabel titleLabel = new JLabel(title);
        GridBagConstraints leftConstraints = buildGeneralConstraints(0, rowIndex, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insetsRight(5));
        groupPanel.add(titleLabel, leftConstraints);

        GridBagConstraints middleConstraints = buildGeneralConstraints(1, rowIndex, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insetsRight(5));
        groupPanel.add(content, middleConstraints);

        clickable.addActionListener(e -> content.setEnabled(clickable.isSelected()));
        GridBagConstraints rightConstraints = buildGeneralConstraints(2, rowIndex, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, JBUI.insets(0));
        groupPanel.add(clickable, rightConstraints);
    }

    private JTextField createDisableTextField(String text) {
        JTextField jTextField = new JTextField(text);
        jTextField.setEnabled(false);

        return jTextField;
    }

    private void renderService() {
        JPanel container = createContainer("\u670d\u52a1\u63a5\u53e3\u914d\u7f6e");
        JPanel holder = createHolder(container);

        // 输出服务列表
        logServiceCheckBox = new JCheckBox("\u8f93\u51fa\u670d\u52a1\u5217\u8868");
        addHolderContent(holder, logServiceCheckBox, 0);

        // 输出无法解析的内容
        logUnsolvedCheckBox = new JCheckBox("\u8f93\u51fa\u65e0\u6cd5\u89e3\u6790\u7684\u5185\u5bb9(\u5efa\u8bae\u6253\u5f00)");
        logUnsolvedCheckBox.setSelected(true);
        addHolderContent(holder, logUnsolvedCheckBox, 1);

        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{105, 390, 80};
            JPanel groupPanel = new JPanel(gridBagLayout);

            // 文件后缀
            fileSuffixTextField = createDisableTextField(".java");

            JCheckBox fileSuffixCheckBox = new JCheckBox("\u590d\u5199");
            fileSuffixCheckBox.setEnabled(false);
            addLineContent("\u6587\u4ef6\u540e\u7f00", fileSuffixTextField, fileSuffixCheckBox, groupPanel, 0);

            // 接口后缀匹配
            suffixTextField = createDisableTextField("Facade");

            JCheckBox suffixCheckBox = new JCheckBox("\u590d\u5199");
            addLineContent("\u63a5\u53e3\u540e\u7f00\u5339\u914d", suffixTextField, suffixCheckBox, groupPanel, 1);

            // 包路径前缀匹配
            prefixTextField = createDisableTextField(null);

            JCheckBox prefixCheckBox = new JCheckBox("\u590d\u5199");
            addLineContent("\u5305\u8def\u5f84\u524d\u7f00\u5339\u914d", prefixTextField, prefixCheckBox,
                    groupPanel, 2);

            addHolderContent(holder, groupPanel, 2);
        }

        this.add(container);
    }

    private void renderReturn() {
        JPanel container = createContainer("\u8fd4\u56de\u5305\u88c5\u914d\u7f6e");
        JPanel holder = createHolder(container);

        JScrollPane scrollPane = new JBScrollPane();
        scrollPane.setPreferredSize(new Dimension(19, 86));
        scrollPane.setMaximumSize(new Dimension(560, 86));
        returnPackTextArea = new JTextArea("java.util.List");
        scrollPane.setViewportView(returnPackTextArea);

        addHolderContent(holder, scrollPane, 0);

        this.add(container);
    }

    private void renderOutput() {
        JPanel container = createContainer("\u6587\u4ef6\u8f93\u51fa\u914d\u7f6e");
        JPanel holder = createHolder(container);

        {
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{105, 390, 80};
            JPanel groupPanel = new JPanel(gridBagLayout);

            // json文件名
            JTextField jsonFileNameTextField = createDisableTextField("\u6b64\u529f\u80fd\u6682\u4e0d\u53ef\u7528");

            JCheckBox jsonFileNameCheckBox = new JCheckBox("\u590d\u5199");
            jsonFileNameCheckBox.setEnabled(false);
            addLineContent("json\u6587\u4ef6\u540d", jsonFileNameTextField, jsonFileNameCheckBox, groupPanel, 0);

            // 文件名
            outputFileNameTextField = createDisableTextField("document.md");

            JCheckBox outputFileNameCheckBox = new JCheckBox("\u590d\u5199");
            addLineContent("\u6587\u6863\u6587\u4ef6\u540d", outputFileNameTextField, outputFileNameCheckBox,
                    groupPanel, 1);

            // 文档输出路径
            outputDirectoryTextWithBrowseButton = new TextFieldWithBrowseButton();
            outputDirectoryTextWithBrowseButton.setEnabled(false);
            outputDirectoryTextWithBrowseButton.setText("/tmp");
            outputDirectoryTextWithBrowseButton.addActionListener(e -> {
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                        project, null);

                if (virtualFile != null) {
                    outputDirectoryTextWithBrowseButton.setText(virtualFile.getPath());
                }
            });

            JCheckBox outputDirectoryCheckBox = new JCheckBox("\u590d\u5199");

            addLineContent("\u6587\u6863\u8f93\u51fa\u8def\u5f84", outputDirectoryTextWithBrowseButton, outputDirectoryCheckBox,
                    groupPanel, 2);

            addHolderContent(holder, groupPanel, 0);
        }

        this.add(container);
    }
}
