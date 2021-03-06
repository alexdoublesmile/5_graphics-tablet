package controller.swing.listeners.buttonListeners;
import model.Model;
import view.swing.SwingViewImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoListener {
    private final UndoButtonListener UNDO_BUTTON_LISTENER;
    private final RedoButtonListener REDO_BUTTON_LISTENER;
    private SwingViewImpl view;
    private Model model;

    public UndoListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;

        UNDO_BUTTON_LISTENER = new UndoButtonListener();
        REDO_BUTTON_LISTENER = new RedoButtonListener();
    }

    private class UndoButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getPreviousAction(view.getTabbedPane().getSelectedIndex()));
            if (!model.hasPreviousAction(view.getTabbedPane().getSelectedIndex())) {
                view.getUndoButton().setEnabled(false);
            }
            view.getRedoButton().setEnabled(true);
            view.getMainPanel().repaint();
        }
    }

    private class RedoButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainImage(model.getNextAction(view.getTabbedPane().getSelectedIndex()));
            if (!model.hasNextAction(view.getTabbedPane().getSelectedIndex())) {
                view.getRedoButton().setEnabled(false);
            }
            view.getUndoButton().setEnabled(true);
            view.getMainPanel().repaint();
        }
    }

    public UndoButtonListener getUNDO_BUTTON_LISTENER() {
        return UNDO_BUTTON_LISTENER;
    }
    public RedoButtonListener getREDO_BUTTON_LISTENER() {
        return REDO_BUTTON_LISTENER;
    }
}

