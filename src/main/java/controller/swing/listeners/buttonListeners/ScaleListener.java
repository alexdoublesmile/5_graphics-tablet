package controller.swing.listeners.buttonListeners;
import config.Config;
import model.Model;
import view.swing.SwingViewImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;

import static config.Config.RESIZE_MINUS_FACTOR;
import static config.Config.RESIZE_PLUS_FACTOR;

public class ScaleListener {
    private final PlusButtonListener PLUS_BUTTON_LISTENER;
    private final MinusButtonListener MINUS_BUTTON_LISTENER;
    private SwingViewImpl view;
    private Model model;
    private JPanel mainPanel;

    public ScaleListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        PLUS_BUTTON_LISTENER = new PlusButtonListener();
        MINUS_BUTTON_LISTENER = new MinusButtonListener();
    }

    private class PlusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.resizeImage(
                    mainPanel.getWidth() * Integer.parseInt(Config.getProperty(RESIZE_PLUS_FACTOR)),
                    mainPanel.getHeight() * Integer.parseInt(Config.getProperty(RESIZE_PLUS_FACTOR)));
            mainPanel.repaint();
        }
    }

    private class MinusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.resizeImage(
                    mainPanel.getWidth() / Integer.parseInt(Config.getProperty(RESIZE_MINUS_FACTOR)),
                    mainPanel.getHeight() / Integer.parseInt(Config.getProperty(RESIZE_MINUS_FACTOR)));
            mainPanel.repaint();
        }
    }

    public PlusButtonListener getPLUS_BUTTON_LISTENER() {
        return PLUS_BUTTON_LISTENER;
    }
    public MinusButtonListener getMINUS_BUTTON_LISTENER() {
        return MINUS_BUTTON_LISTENER;
    }
}

