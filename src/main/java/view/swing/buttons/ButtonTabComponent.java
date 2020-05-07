package view.swing.buttons;

import controller.swing.listeners.KeyboardListener;
import controller.swing.listeners.MouseDrawListener;
import model.Model;
import model.UndoRedoService;
import view.swing.SwingViewImpl;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;
    private final SwingViewImpl view;
    public final Model modell;

    public ButtonTabComponent(final JTabbedPane pane, SwingViewImpl view, Model model) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.view = view;
        this.modell = model;

        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);

        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
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

        renameTab.addActionListener(e -> {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            String answer = (String) JOptionPane.showInputDialog(
                    this,
                    null,
                    String.format("Rename %s Tab", label.getText()),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    label.getText());
            if (answer != null && !answer.isEmpty())
                pane.setTitleAt(i, answer);
        });

        copyTab.addActionListener(e -> {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            MouseDrawListener mouseDrawListeners = new MouseDrawListener(view, modell);

            JPanel panel = view.new MyPanel(true);

            panel.addMouseMotionListener(mouseDrawListeners.getMOUSE_MOTION_ADAPTER());
            panel.addMouseListener(mouseDrawListeners.getMOUSE_ADAPTER());

            view.getPanelList().add(i + 1, panel);
            view.addTab(view.getDefaultTabName(), i);
            Graphics g2 = view.getImageList().get(i + 1).getGraphics();
            g2.drawImage(view.getImageList().get(i), 0 , 0, null);

            view.getTabbedPane().setSelectedIndex(i + 1);
            if (modell.getUndoList().size() < pane.getTabCount()) {
                modell.getUndoList().add(i + 1, new UndoRedoService());
            } else {
                modell.getUndoList().set(i + 1, new UndoRedoService());
            }

            panel.addKeyListener(new KeyboardListener(view, modell).getKEY_ADAPTER());
            modell.saveAction(view.getMainImage(), i + 1);
        });

        tabPopupMenu.add(renameTab);
        tabPopupMenu.add(copyTab);
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
//        this.setComponentPopupMenu(tabPopupMenu);
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
//            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1 && pane.getTabCount() > 1) {
                pane.remove(i);
                view.getPanelList().remove(i);
                view.getImageList().remove(i);
                modell.getUndoList().remove(i);

//                JPanel currentPanel = (JPanel) view.getTabbedPane().getTabComponentAt(view.getTabbedPane().getSelectedIndex());
//                currentPanel.getComponent(1).setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "This is the last tab :)");
            }
        }

        //don't want to update UI for this button
//        public void updateUI() {
//        }

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
