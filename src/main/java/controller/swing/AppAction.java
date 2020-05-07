package controller.swing;

import config.Config;
import model.Model;
import util.IconBuilder;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AppAction {

    private CloseTabAction closeTabAction;

    private SwingViewImpl view;
    private Model model;

    public AppAction(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;

        closeTabAction = new CloseTabAction();
    }

    private class CloseTabAction extends AbstractAction {

        public CloseTabAction() {
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.SMALL_ICON, IconBuilder.buildIconByPath(Config.getProperty(Config.TAB_CLOSE_ICON_PATH)));
            putValue(Action.SHORT_DESCRIPTION, "Close it");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.getTabbedPane().getTabCount() > 0) {
                view.getPanelList().remove(view.getTabbedPane().getSelectedIndex());
                view.getImageList().remove(view.getTabbedPane().getSelectedIndex());
                model.getUndoList().remove(view.getTabbedPane().getSelectedIndex());

                view.getTabbedPane().remove(view.getTabbedPane().getSelectedIndex());
            }
        }
    }

    public CloseTabAction getCloseTabAction() {
        return closeTabAction;
    }
}
