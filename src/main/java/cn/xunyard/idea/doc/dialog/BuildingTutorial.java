/*
 * Created by JFormDesigner on Sun Dec 15 19:41:01 CST 2019
 */

package cn.xunyard.idea.doc.dialog;

import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author sdfa
 */
public class BuildingTutorial extends JPanel {

    public BuildingTutorial() {
        initComponents();
    }

    public DocBuildingContext getDocBuildingContext() {
        return new DocBuildingContext(suffixMatch.getText(), prefixMatch.getText(), outputDirectory.getText());
    }

    private void suffixCheckBoxActionPerformed(ActionEvent e) {
        this.suffixMatch.setEnabled(suffixCheckBox.isSelected());
    }

    private void prefixCheckBoxActionPerformed(ActionEvent e) {
        this.prefixMatch.setEnabled(prefixCheckBox.isSelected());
    }

    private void outputButtonActionPerformed(ActionEvent event) {
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                ProjectUtils.getCurrentProject(), null);

        if (virtualFile != null) {
            outputDirectory.setText(virtualFile.getPath());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - sdfa
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        panel5 = new JPanel();
        label7 = new JLabel();
        fileSuffix = new JTextField();
        interfaceConfigPanel = new JPanel();
        suffixCheckBox = new JCheckBox();
        suffixMatch = new JTextField();
        prefixCheckBox = new JCheckBox();
        prefixMatch = new JTextField();
        panel4 = new JPanel();
        label3 = new JLabel();
        label4 = new JLabel();
        outputPanel = new JPanel();
        outputButton = new JButton();
        outputDirectory = new JLabel();
        checkBox1 = new JCheckBox();

        //======== this ========
        setMinimumSize(null);
        setPreferredSize(new Dimension(800, 400));
        setMaximumSize(null);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.
                border.EmptyBorder(0, 0, 0, 0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax.swing.border.TitledBorder.CENTER
                , javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("D\u0069al\u006fg", java.awt.Font
                .BOLD, 12), java.awt.Color.red), getBorder()));
        addPropertyChangeListener(
                new java.beans.PropertyChangeListener() {
                    @Override
                    public void propertyChange(java.beans.PropertyChangeEvent e) {
                        if ("\u0062or\u0064er"
                                .equals(e.getPropertyName())) throw new RuntimeException();
                    }
                });
        setLayout(new VerticalLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label1 ----
            label1.setText("\u63a5\u53e3\u57fa\u7840\u914d\u7f6e");
            panel1.add(label1);

            //---- label2 ----
            label2.setText("-------------------");
            panel1.add(label2);
        }
        add(panel1);

        //======== panel5 ========
        {
            panel5.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label7 ----
            label7.setText("\u6587\u4ef6\u540e\u7f00:");
            panel5.add(label7);

            //---- fileSuffix ----
            fileSuffix.setToolTipText("\u5305\u62ec\u7b26\u53f7('.')");
            fileSuffix.setText(".java");
            fileSuffix.setEditable(false);
            fileSuffix.setFocusable(false);
            panel5.add(fileSuffix);
        }
        add(panel5);

        //======== interfaceConfigPanel ========
        {
            interfaceConfigPanel.setLayout(new GridBagLayout());
            ((GridBagLayout) interfaceConfigPanel.getLayout()).columnWidths = new int[]{0, 0, 0};
            ((GridBagLayout) interfaceConfigPanel.getLayout()).rowHeights = new int[]{0, 0, 0};
            ((GridBagLayout) interfaceConfigPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
            ((GridBagLayout) interfaceConfigPanel.getLayout()).rowWeights = new double[]{0.0, 0.0, 1.0E-4};

            //---- suffixCheckBox ----
            suffixCheckBox.setText("\u63a5\u53e3\u540e\u7f00\u5339\u914d");
            suffixCheckBox.setSelected(true);
            suffixCheckBox.addActionListener(e -> suffixCheckBoxActionPerformed(e));
            interfaceConfigPanel.add(suffixCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

            //---- suffixMatch ----
            suffixMatch.setPreferredSize(new Dimension(300, 27));
            suffixMatch.setText("Facade");
            interfaceConfigPanel.add(suffixMatch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

            //---- prefixCheckBox ----
            prefixCheckBox.setText("\u5305\u8def\u5f84\u524d\u7f00\u5339\u914d");
            prefixCheckBox.addActionListener(e -> prefixCheckBoxActionPerformed(e));
            interfaceConfigPanel.add(prefixCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

            //---- prefixMatch ----
            prefixMatch.setEnabled(false);
            interfaceConfigPanel.add(prefixMatch, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        add(interfaceConfigPanel);

        //======== panel4 ========
        {
            panel4.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- label3 ----
            label3.setText("\u6587\u4ef6\u8f93\u51fa\u914d\u7f6e");
            panel4.add(label3);

            //---- label4 ----
            label4.setText("-------------------");
            panel4.add(label4);
        }
        add(panel4);

        //======== outputPanel ========
        {
            outputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            //---- outputButton ----
            outputButton.setText("\u4fdd\u5b58\u76ee\u5f55...");
            outputButton.setActionCommand("\u8f93\u51fa\u76ee\u5f55...");
            outputButton.addActionListener(e -> outputButtonActionPerformed(e));
            outputPanel.add(outputButton);

            //---- outputDirectory ----
            outputDirectory.setText("\u6682\u672a\u9009\u5b9a...");
            outputPanel.add(outputDirectory);
        }
        add(outputPanel);

        //---- checkBox1 ----
        checkBox1.setText("\u4fdd\u5b58json\u6587\u4ef6\uff08\u6682\u4e0d\u53ef\u7528\uff09");
        checkBox1.setEnabled(false);
        add(checkBox1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - sdfa
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JPanel panel5;
    private JLabel label7;
    private JTextField fileSuffix;
    private JPanel interfaceConfigPanel;
    private JCheckBox suffixCheckBox;
    private JTextField suffixMatch;
    private JCheckBox prefixCheckBox;
    private JTextField prefixMatch;
    private JPanel panel4;
    private JLabel label3;
    private JLabel label4;
    private JPanel outputPanel;
    private JButton outputButton;
    private JLabel outputDirectory;
    private JCheckBox checkBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
