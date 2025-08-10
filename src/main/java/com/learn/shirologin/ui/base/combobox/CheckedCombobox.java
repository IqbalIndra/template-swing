package com.learn.shirologin.ui.base.combobox;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CheckedCombobox <E extends CheckItem> extends JComboBox<E> {
    protected boolean keepOpen;
    private final JPanel panel = new JPanel(new BorderLayout());

    public CheckedCombobox(ComboBoxModel<E> model) {
        super(model);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(200, 20);
    }

    @Override public void updateUI() {
        setRenderer(null);
        super.updateUI();
        Accessible a = getAccessibleContext().getAccessibleChild(0);
        if (a instanceof ComboPopup) {
            ((ComboPopup) a).getList().addMouseListener(new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) {
                    JList<?> list = (JList<?>) e.getComponent();
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        keepOpen = true;
                        updateItem(list.locationToIndex(e.getPoint()));
                    }
                }
            });
        }
        setRenderer(new CheckItemListCellRenderer());
        initActionMap();
    }

    protected void initActionMap() {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        getInputMap(WHEN_FOCUSED).put(ks, "checkbox-select");
        getActionMap().put("checkbox-select", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Accessible a = getAccessibleContext().getAccessibleChild(0);
                if (a instanceof ComboPopup) {
                    updateItem(((ComboPopup) a).getList().getSelectedIndex());
                }
            }
        });
    }

    protected void updateItem(int index) {
        if (isPopupVisible() && index >= 0) {
            E item = getItemAt(index);
            item.setSelected(!item.isSelected());
            setSelectedIndex(-1);
            setSelectedItem(item);
        }
    }

    @Override public void setPopupVisible(boolean v) {
        if (keepOpen) {
            keepOpen = false;
        } else {
            super.setPopupVisible(v);
        }
    }

    protected static <E extends CheckItem> String getCheckItemString(ListModel<E> model) {
        return IntStream.range(0, model.getSize())
                .mapToObj(model::getElementAt)
                .filter(CheckItem::isSelected)
                .map(Objects::toString)
                .collect(Collectors.joining(", "));
    }


    private final class CheckItemListCellRenderer implements ListCellRenderer<E> {
        private final DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        private final JCheckBox check = new JCheckBox();

        @Override public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
            panel.removeAll();
            check.setOpaque(false);
            Component c = renderer.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (index < 0) {
                String txt = getCheckItemString(list.getModel());
                JLabel l = (JLabel) c;
                l.setText(txt.isEmpty() ? " " : txt);
                l.setOpaque(false);
                l.setForeground(list.getForeground());
                panel.setOpaque(false);
            } else {
                check.setSelected(value.isSelected());
                panel.add(check, BorderLayout.WEST);
                panel.setOpaque(true);
                panel.setBackground(c.getBackground());
            }
            panel.add(c);
            return panel;
        }
    }
}
