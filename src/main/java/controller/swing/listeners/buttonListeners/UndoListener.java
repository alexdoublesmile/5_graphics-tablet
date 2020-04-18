package controller.swing.listeners.buttonListeners;
import model.Model;
import view.swing.SwingViewImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoListener {
    private final UndoButtonListener UNDO_BUTTON_LISTENER;
    private final RedoButtonListener REDO_BUTTON_LISTENER;
    private SwingViewImpl view;
    private Model model;
    private JPanel mainPanel;

    public UndoListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        UNDO_BUTTON_LISTENER = new UndoButtonListener();
        REDO_BUTTON_LISTENER = new RedoButtonListener();
    }

    private class UndoButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getPreviousAction());
            mainPanel.repaint();
        }
    }

    private class RedoButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getNextAction());
            mainPanel.repaint();
        }
    }

    public UndoButtonListener getUNDO_BUTTON_LISTENER() {
        return UNDO_BUTTON_LISTENER;
    }
    public RedoButtonListener getREDO_BUTTON_LISTENER() {
        return REDO_BUTTON_LISTENER;
    }
}

