package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
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
    private JPanel mainPanel;

    private final KeyAdapter KEY_ADAPTER;

    public KeyboardListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();
        KEY_ADAPTER = new KeyAdapter();

        initHotKeys();
    }

    public KeyboardListener(SwingViewImpl view) {
        this.view = view;
        mainPanel = view.getMainPanel();
        KEY_ADAPTER = new KeyAdapter();

        initHotKeys();
    }

    private void initHotKeys() {
        undoListener = new UndoListener(view, model);
        inputMap = mainPanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        actionMap = mainPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "redo");

        actionMap.put("undo", undoListener.getUNDO_BUTTON_LISTENER());
        actionMap.put("redo", undoListener.getREDO_BUTTON_LISTENER());
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
//                g2.drawString(str, model.getFinalXPoint(), model.getFinalYPoint());
//
//                model.setFinalXPoint(model.getFinalXPoint() + Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_OFFSET)));
//                mainPanel.requestFocus();
//
//                model.saveAction(view.getMainImage());
//                mainPanel.repaint();
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
