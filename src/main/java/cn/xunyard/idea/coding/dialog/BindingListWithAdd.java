package cn.xunyard.idea.coding.dialog;

import com.intellij.icons.AllIcons;
import com.intellij.ui.Colors;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import com.jgoodies.forms.factories.Borders;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-07
 */
public class BindingListWithAdd extends JPanel {

    public BindingListWithAdd() {
        GridBagLayout gridBagLayout = new GridBagLayout();
//        gridBagLayout.columnWidths = new int[]{1000};
//        gridBagLayout.rowHeights = new int[]{100, 20};
        this.setLayout(gridBagLayout);
        this.setBorder(Borders.TABBED_DIALOG_BORDER);
        this.setBackground(JBColor.BLACK);

        addList();
        addButtons();
    }

    private void addList() {
        GridBagConstraints constraints = new GridBagConstraints(0, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                JBUI.insets(0), 0, 0);

        JBList<String> jbList = new JBList<>();
//        jbList.setMaximumSize(new Dimension(1000, 100));
//        jbList.setMinimumSize(new Dimension(100, 100));
        jbList.setListData(new String[]{"111", "222", "333", "444"});
        this.add(jbList, constraints);
    }

    private void addButtons() {
        GridBagConstraints constraints = new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                JBUI.insets(0), 0, 0);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(0);
        flowLayout.setVgap(0);
        JPanel buttonPanel = new JPanel(flowLayout);


        JButton addButton = new JButton(AllIcons.General.Add);
        addButton.setBackground(JBColor.BLACK);
        addButton.setOpaque(false);
        addButton.setPreferredSize(new Dimension(26, 26));


        JButton removeButton = new JButton(AllIcons.General.Remove);
        removeButton.setPreferredSize(new Dimension(26, 26));
        removeButton.setContentAreaFilled(false);
        removeButton.setMargin(JBUI.emptyInsets());
        removeButton.setIconTextGap(0);
        removeButton.setBorderPainted(false);
        removeButton.setBorder(null);
        removeButton.setFocusPainted(false);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        this.add(buttonPanel, constraints);
    }

    public void onAdd() {

    }

    public void onDelete() {

    }



}
