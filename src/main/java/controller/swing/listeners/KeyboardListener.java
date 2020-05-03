package controller.swing.listeners;

import controller.swing.listeners.buttonListeners.UndoListener;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class KeyboardListener {


    private InputMap inputMap;
    private ActionMap actionMap;
    private UndoListener undoListener;

    private SwingViewImpl view;
    private Model model;
//    private JPanel mainPanel;

    private final KeyAdapter KEY_ADAPTER;

    public KeyboardListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
//        mainPanel = view.getMainPanel();
        KEY_ADAPTER = new KeyAdapter();

        initHotKeys();
    }

    public KeyboardListener(SwingViewImpl view) {
        this.view = view;
//        mainPanel = view.getMainPanel();
        KEY_ADAPTER = new KeyAdapter();

        initHotKeys();
    }

    private void initHotKeys() {
        undoListener = new UndoListener(view, model);
        inputMap = view.getMainPanel().getInputMap(WHEN_IN_FOCUSED_WINDOW);
        actionMap = view.getMainPanel().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "redo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "leftTab");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "rightTab");

        actionMap.put("undo", undoListener.getUNDO_BUTTON_LISTENER());
        actionMap.put("redo", undoListener.getREDO_BUTTON_LISTENER());
        actionMap.put("rightTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getTabbedPane().getSelectedIndex() < view.getTabbedPane().getTabCount() - 1) {

                    view.getTabbedPane().setSelectedIndex(view.getTabbedPane().getSelectedIndex() + 1);
                }
            }
        });
        actionMap.put("leftTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (view.getTabbedPane().getSelectedIndex() > 0) {

                    view.getTabbedPane().setSelectedIndex(view.getTabbedPane().getSelectedIndex() - 1);
                }
            }
        });

    }


    private class KeyAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
//            if (model.getDrawMode().equals(DrawMode.TEXT)){
//
//                Graphics g = view.getMainImage().getGraphics();
//                Graphics2D g2 = (Graphics2D)g;
//                g2.setColor(view.getMainColor());
//                g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.TEXT_BASIC_STROKE))));
//
//                String str = new  String("");
//                str += e.getKeyChar();
//                g2.setFont(new  Font(Config.getProperty(Config.TEXT_BASIC_FONT),
//                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_STYLE)),
//                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_SIZE))));
//                g2.drawString(str, model.getFinalX(), model.getFinalY());
//
//                model.setFinalX(model.getFinalX() + Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_OFFSET)));
//                view.getMainPanel().requestFocus();
//
//                model.saveAction(view.getMainImage());
//                view.getMainPanel().repaint();
//            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public KeyAdapter getKEY_ADAPTER() {
        return KEY_ADAPTER;
    }
}
