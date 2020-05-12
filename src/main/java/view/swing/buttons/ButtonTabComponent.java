package view.swing.buttons;

import controller.swing.actions.TabAction;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

public class ButtonTabComponent extends JPanel {
    private final SwingViewImpl view;
    public final Model model;
//    public JPanel currentPanel;

    public ButtonTabComponent(SwingViewImpl view, Model model) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.view = view;
        this.model = model;
//        currentPanel = this;

        if (view.getTabbedPane() == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        setOpaque(false);

        JLabel label = new JLabel() {
            public String getText() {
                int i = view.getTabbedPane().indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return view.getTabbedPane().getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        JButton button = new TabButton();
        add(button);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        JPopupMenu tabPopupMenu = new JPopupMenu();
        JMenuItem renameTab = new JMenuItem("Rename");
        JMenuItem copyTab = new JMenuItem("Duplicate");
        JMenuItem deleteOtherTabs = new JMenuItem("Close Others");
        JMenuItem deleteTabsToTheRight = new JMenuItem("Close All to the Right");
        JMenuItem deleteTabsToTheLeft = new JMenuItem("Close All to the Left");
        renameTab.setMnemonic(KeyEvent.VK_N);
        copyTab.setMnemonic(KeyEvent.VK_D);
        deleteOtherTabs.setMnemonic(KeyEvent.VK_O);
        deleteTabsToTheRight.setMnemonic(KeyEvent.VK_R);
        deleteTabsToTheLeft.setMnemonic(KeyEvent.VK_L);

        renameTab.addActionListener(TabAction.buildRenameTabAction(view, ButtonTabComponent.this));
        copyTab.addActionListener(TabAction.buildAddTabAction(view, this.model, ButtonTabComponent.this, true));
        deleteOtherTabs.addActionListener(TabAction.buildRemoveTabAction(view, this.model, ButtonTabComponent.this, TabAction.REMOVE_OTHERS));
        deleteTabsToTheRight.addActionListener(TabAction.buildRemoveTabAction(view, this.model, ButtonTabComponent.this, TabAction.REMOVE_TO_THE_RIGHT));
        deleteTabsToTheLeft.addActionListener(TabAction.buildRemoveTabAction(view, this.model, ButtonTabComponent.this, TabAction.REMOVE_TO_THE_LEFT));


        tabPopupMenu.add(renameTab);
        tabPopupMenu.add(copyTab);
        tabPopupMenu.addSeparator();
        tabPopupMenu.add(deleteOtherTabs);
        tabPopupMenu.add(deleteTabsToTheRight);
        tabPopupMenu.add(deleteTabsToTheLeft);

        view.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    tabPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    tabPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        button.setComponentPopupMenu(tabPopupMenu);
    }

    private class TabButton extends JButton {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(TabAction.buildRemoveTabAction(view, ButtonTabComponent.this.model, ButtonTabComponent.this, TabAction.REMOVE_CURRENT));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }

    };
}
