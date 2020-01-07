package cn.xunyard.idea.coding.dialog;

import cn.xunyard.idea.coding.util.ObjectUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-07
 */
public class BindingListWithAdd extends JPanel {

    private JScrollPane scrollPane;
    private JBList<String> jbList;
    private JPanel buttonPanel;
    private Integer selectedIndex;
    private LabelMouseListener addMouseListener;
    private LabelMouseListener removeMouseListener;
    private final List<String> listData;

    public BindingListWithAdd(@NotNull List<String> listData) {
        this.listData = listData;
        initComponent();
    }

    private void addList() {
        jbList = new JBList<>();
        jbList.setModel(new DataModel(listData));
        scrollPane = new JBScrollPane(jbList);
        this.add(scrollPane);
    }

    private void addButtons() {
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(1);
        flowLayout.setVgap(1);
        buttonPanel = new JPanel(flowLayout);

        JLabel addLabel = new JLabel(AllIcons.General.Add);
        addLabel.setPreferredSize(new Dimension(20, 20));

        addMouseListener = new LabelMouseListener(addLabel);
        addLabel.addMouseListener(addMouseListener);
        buttonPanel.add(addLabel);


        JLabel removeLabel = new JLabel(AllIcons.General.Remove);
        removeLabel.setPreferredSize(new Dimension(20, 20));
        removeMouseListener = new LabelMouseListener(removeLabel);
        removeLabel.addMouseListener(removeMouseListener);
        buttonPanel.add(removeLabel);
        this.add(buttonPanel);
    }

    private void initComponent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        this.setLayout(verticalLayout);

        addList();
        addButtons();

        removeMouseListener.setAllowRender(false);
        jbList.addListSelectionListener(l -> {
            selectedIndex = jbList.getSelectedIndex();
            removeMouseListener.setAllowRender(true);
        });

        removeMouseListener.setActionListener(this::deleteData);
    }


    public void setAddActionListener(ActionListener listener) {
        addMouseListener.setActionListener(listener);
    }

    public void addPath(String path) {
        if (listData.contains(path)) {
            Messages.showMessageDialog(String.format("选择路径 %s 重复", path), "添加路径错误", Messages.getErrorIcon());
            return;
        }

        listData.add(path);
        jbList.setModel(new DataModel(listData));
        jbList.ensureIndexIsVisible(listData.size() - 1);
    }

    private void deleteData(ActionEvent e) {
        if (selectedIndex != null) {
            listData.remove(selectedIndex.intValue());
            jbList.setModel(new DataModel(listData));
            selectedIndex = null;
            removeMouseListener.setAllowRender(false);
        }
    }


    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);

        this.scrollPane.setPreferredSize(new Dimension(preferredSize.width, preferredSize.height - 25));
        this.buttonPanel.setPreferredSize(new Dimension(preferredSize.width, 25));
    }

    private static final class LabelMouseListener implements MouseListener {
        private final JLabel label;

        private boolean allowRender = true;

        public void setAllowRender(boolean allowRender) {
            this.allowRender = allowRender;

            if (!allowRender) {
                label.setOpaque(false);
                label.repaint();
            }
        }

        @Setter
        @Nullable
        private ActionListener actionListener;

        private LabelMouseListener(JLabel label) {
            this.label = label;
            label.setBackground(JBColor.LIGHT_GRAY);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (actionListener != null) {
                actionListener.actionPerformed(new ActionEvent(label, ActionEvent.ACTION_PERFORMED, "click"));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!allowRender) {
                return;
            }

            label.setOpaque(true);
            label.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!allowRender) {
                return;
            }

            label.setOpaque(false);
            label.repaint();
        }
    }

    @RequiredArgsConstructor
    private static final class DataModel extends AbstractListModel<String> {

        private final List<String> listData;

        @Override
        public int getSize() {
            return listData.size();
        }

        @Override
        public String getElementAt(int index) {
            return listData.get(index);
        }
    }

    public static final class BindingListWithAddBuilder {
        private final Consumer<List<String>> setter;
        private List<String> init;
        private ActionListener listener;

        private BindingListWithAddBuilder(Consumer<List<String>> setter) {
            this.setter = setter;
        }

        public BindingListWithAddBuilder init(List<String> init) {
            this.init = init;
            return this;
        }

        public BindingListWithAddBuilder addListener(ActionListener listener) {
            this.listener = listener;
            return this;
        }

        public BindingListWithAdd build() {
            List<String> listData = ObjectUtils.firstNonNull(init, ArrayList::new);
            setter.accept(listData);
            BindingListWithAdd bindingListWithAdd = new BindingListWithAdd(listData);
            bindingListWithAdd.setAddActionListener(listener);
            return bindingListWithAdd;
        }


        public static BindingListWithAddBuilder from(Consumer<List<String>> setter) {
            return new BindingListWithAddBuilder(setter);
        }
    }
}
