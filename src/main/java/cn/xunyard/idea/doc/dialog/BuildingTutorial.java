/*
 * Created by JFormDesigner on Sun Dec 15 19:41:01 CST 2019
 */

package cn.xunyard.idea.doc.dialog;

import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author sdfa
 */
public class BuildingTutorial extends JPanel {

    public BuildingTutorial() {
        initComponents();
    }

    private void suffixCheckBoxActionPerformed(ActionEvent e) {
        this.suffixMatch.setEnabled(suffixCheckBox.isSelected());
    }

    private void prefixCheckBoxActionPerformed(ActionEvent e) {
        this.prefixMatch.setEnabled(prefixCheckBox.isSelected());
    }

    private void outputButtonActionPerformed(ActionEvent event) {
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                ProjectUtils.PROJECT.get(), null);

        if (virtualFile != null) {
//            outputDirectory.setText(virtualFile.getPath());
        }
    }

    private void renderBasic() {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - sdfa
        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
        outputPanel2 = new JPanel();
        separator8 = compFactory.createSeparator("\u8fd0\u884c\u57fa\u7840\u914d\u7f6e");
        panel8 = new JPanel();
        allowInfoMissingCheckBox = new JCheckBox();
        returnPackPanel2 = new JPanel();
        separator7 = compFactory.createSeparator("\u670d\u52a1\u63a5\u53e3\u914d\u7f6e");
        logDetailCheckBox = new JCheckBox();
        logUnresolvedCheckBox = new JCheckBox();
        panel14 = new JPanel();
        label3 = new JLabel();
        fileSuffix = new JTextField();
        checkBox2 = new JCheckBox();
        label1 = new JLabel();
        suffixMatch = new JTextField();
        suffixCheckBox = new JCheckBox();
        label2 = new JLabel();
        prefixMatch = new JTextField();
        prefixCheckBox = new JCheckBox();
        returnPackPanel = new JPanel();
        separator6 = compFactory.createSeparator("\u8fd4\u56de\u5305\u88c5\u914d\u7f6e");
        panel9 = new JPanel();
        resultPackScrollPanel = new JScrollPane();
        resultPackTextArea = new JTextArea();
        outputPanel = new JPanel();
        separator5 = compFactory.createSeparator("\u6587\u4ef6\u8f93\u51fa\u914d\u7f6e");
        panel15 = new JPanel();
        label6 = new JLabel();
        textField3 = new JTextField();
        checkBox4 = new JCheckBox();
        label5 = new JLabel();
        textField2 = new JTextField();
        checkBox3 = new JCheckBox();
        label4 = new JLabel();
        textField1 = new JTextField();
        outputButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(600, 400));
        setPreferredSize(new Dimension(600, 400));
        setMaximumSize(new Dimension(600, 400));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new
        javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax
        .swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java
        .awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt
        .Color.red), getBorder())); addPropertyChangeListener(new java.beans.
        PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".
        equals(e.getPropertyName()))throw new RuntimeException();}});
        setLayout(new VerticalLayout());

        //======== outputPanel2 ========
        {
            outputPanel2.setLayout(new GridBagLayout());
            ((GridBagLayout)outputPanel2.getLayout()).columnWidths = new int[] {25, 550, 0};
            ((GridBagLayout)outputPanel2.getLayout()).rowHeights = new int[] {45, 0, 0};
            ((GridBagLayout)outputPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)outputPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            outputPanel2.add(separator8, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel8 ========
            {
                panel8.setLayout(new VerticalLayout());

                //---- allowInfoMissingCheckBox ----
                allowInfoMissingCheckBox.setText("\u5141\u8bb8\u4fe1\u606f\u7f3a\u5931(\u4f7f\u7528\u540d\u79f0\u4ee3\u66ff)");
                allowInfoMissingCheckBox.setSelected(true);
                panel8.add(allowInfoMissingCheckBox);
            }
            outputPanel2.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(outputPanel2);

        //======== returnPackPanel2 ========
        {
            returnPackPanel2.setLayout(new GridBagLayout());
            ((GridBagLayout)returnPackPanel2.getLayout()).columnWidths = new int[] {25, 550, 0};
            ((GridBagLayout)returnPackPanel2.getLayout()).rowHeights = new int[] {45, 0, 0, 0, 0};
            ((GridBagLayout)returnPackPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)returnPackPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
            returnPackPanel2.add(separator7, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- logDetailCheckBox ----
            logDetailCheckBox.setText("\u8f93\u51fa\u670d\u52a1\u5217\u8868");
            returnPackPanel2.add(logDetailCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- logUnresolvedCheckBox ----
            logUnresolvedCheckBox.setText("\u8f93\u51fa\u65e0\u6cd5\u89e3\u6790\u7684\u5185\u5bb9(\u5efa\u8bae\u6253\u5f00)");
            logUnresolvedCheckBox.setSelected(true);
            returnPackPanel2.add(logUnresolvedCheckBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel14 ========
            {
                panel14.setBorder(LineBorder.createBlackLineBorder());
                panel14.setLayout(new GridBagLayout());
                ((GridBagLayout)panel14.getLayout()).columnWidths = new int[] {105, 355, 0, 0};
                ((GridBagLayout)panel14.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel14.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel14.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- label3 ----
                label3.setText("\u6587\u4ef6\u540e\u7f00");
                label3.setBorder(LineBorder.createBlackLineBorder());
                panel14.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- fileSuffix ----
                fileSuffix.setToolTipText("\u5305\u62ec\u7b26\u53f7('.')");
                fileSuffix.setText(".java");
                fileSuffix.setEditable(false);
                fileSuffix.setFocusable(false);
                panel14.add(fileSuffix, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- checkBox2 ----
                checkBox2.setText("\u590d\u5199");
                checkBox2.setEnabled(false);
                panel14.add(checkBox2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label1 ----
                label1.setText("\u63a5\u53e3\u540e\u7f00\u5339\u914d");
                panel14.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- suffixMatch ----
                suffixMatch.setPreferredSize(new Dimension(300, 27));
                suffixMatch.setText("Facade");
                suffixMatch.setEnabled(false);
                panel14.add(suffixMatch, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- suffixCheckBox ----
                suffixCheckBox.setText("\u590d\u5199");
                suffixCheckBox.addActionListener(e -> suffixCheckBoxActionPerformed(e));
                panel14.add(suffixCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- label2 ----
                label2.setText("\u5305\u8def\u5f84\u524d\u7f00\u5339\u914d");
                panel14.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- prefixMatch ----
                prefixMatch.setEnabled(false);
                prefixMatch.setPreferredSize(new Dimension(300, 30));
                panel14.add(prefixMatch, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- prefixCheckBox ----
                prefixCheckBox.setText("\u590d\u5199");
                prefixCheckBox.addActionListener(e -> prefixCheckBoxActionPerformed(e));
                panel14.add(prefixCheckBox, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            returnPackPanel2.add(panel14, new GridBagConstraints(1, 3, 1, 1, 8.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(returnPackPanel2);

        //======== returnPackPanel ========
        {
            returnPackPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)returnPackPanel.getLayout()).columnWidths = new int[] {25, 550, 0};
            ((GridBagLayout)returnPackPanel.getLayout()).rowHeights = new int[] {45, 89, 0};
            ((GridBagLayout)returnPackPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)returnPackPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            returnPackPanel.add(separator6, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel9 ========
            {
                panel9.setLayout(new VerticalLayout());

                //======== resultPackScrollPanel ========
                {
                    resultPackScrollPanel.setPreferredSize(new Dimension(19, 86));

                    //---- resultPackTextArea ----
                    resultPackTextArea.setPreferredSize(null);
                    resultPackTextArea.setText("java.util.List\njava.util.Map");
                    resultPackScrollPanel.setViewportView(resultPackTextArea);
                }
                panel9.add(resultPackScrollPanel);
            }
            returnPackPanel.add(panel9, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(returnPackPanel);

        //======== outputPanel ========
        {
            outputPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)outputPanel.getLayout()).columnWidths = new int[] {25, 550, 0};
            ((GridBagLayout)outputPanel.getLayout()).rowHeights = new int[] {45, 0, 0};
            ((GridBagLayout)outputPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
            ((GridBagLayout)outputPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
            outputPanel.add(separator5, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel15 ========
            {
                panel15.setLayout(new GridBagLayout());
                ((GridBagLayout)panel15.getLayout()).columnWidths = new int[] {105, 355, 0, 0};
                ((GridBagLayout)panel15.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel15.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel15.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- label6 ----
                label6.setText("json\u6587\u4ef6\u540d");
                panel15.add(label6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textField3 ----
                textField3.setText("\u6b64\u529f\u80fd\u6682\u4e0d\u53ef\u7528");
                textField3.setEnabled(false);
                panel15.add(textField3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- checkBox4 ----
                checkBox4.setText("\u590d\u5199");
                checkBox4.setEnabled(false);
                panel15.add(checkBox4, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label5 ----
                label5.setText("\u6587\u4ef6\u540d");
                panel15.add(label5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textField2 ----
                textField2.setEnabled(false);
                textField2.setText("document.md");
                panel15.add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- checkBox3 ----
                checkBox3.setText("\u590d\u5199");
                panel15.add(checkBox3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("\u6587\u6863\u8f93\u51fa\u8def\u5f84");
                panel15.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- textField1 ----
                textField1.setEnabled(false);
                textField1.setText("/tmp/");
                panel15.add(textField1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- outputButton ----
                outputButton.setText("\u6d4f\u89c8");
                outputButton.setActionCommand("\u8f93\u51fa\u76ee\u5f55...");
                outputButton.addActionListener(e -> outputButtonActionPerformed(e));
                panel15.add(outputButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            outputPanel.add(panel15, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(outputPanel);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - sdfa
    private JPanel outputPanel2;
    private JComponent separator8;
    private JPanel panel8;
    private JCheckBox allowInfoMissingCheckBox;
    private JPanel returnPackPanel2;
    private JComponent separator7;
    private JCheckBox logDetailCheckBox;
    private JCheckBox logUnresolvedCheckBox;
    private JPanel panel14;
    private JLabel label3;
    private JTextField fileSuffix;
    private JCheckBox checkBox2;
    private JLabel label1;
    private JTextField suffixMatch;
    private JCheckBox suffixCheckBox;
    private JLabel label2;
    private JTextField prefixMatch;
    private JCheckBox prefixCheckBox;
    private JPanel returnPackPanel;
    private JComponent separator6;
    private JPanel panel9;
    private JScrollPane resultPackScrollPanel;
    private JTextArea resultPackTextArea;
    private JPanel outputPanel;
    private JComponent separator5;
    private JPanel panel15;
    private JLabel label6;
    private JTextField textField3;
    private JCheckBox checkBox4;
    private JLabel label5;
    private JTextField textField2;
    private JCheckBox checkBox3;
    private JLabel label4;
    private JTextField textField1;
    private JButton outputButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
