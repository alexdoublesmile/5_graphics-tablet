package view.swing;

import model.Model;

import javax.swing.*;

public class CustomTabbedPane extends JTabbedPane {

    private SwingViewImpl view;
    private Model model;

    public CustomTabbedPane(int tabPlacement, int tabsLayoutPolicy, SwingViewImpl view, Model model) {
        super(tabPlacement, tabsLayoutPolicy);
        this.view = view;
        this.model = model;
    }

    @Override
    public void remove(int index) {
        super.remove(index);
        view.getPanelList().remove(view.getTabbedPane().getSelectedIndex());
        view.getImageList().remove(view.getTabbedPane().getSelectedIndex());
        model.getUndoList().remove(view.getTabbedPane().getSelectedIndex());

        view.getTabbedPane().remove(view.getTabbedPane().getSelectedIndex());

        JPanel currentPanel = (JPanel) view.getTabbedPane().getTabComponentAt(view.getTabbedPane().getSelectedIndex());
        currentPanel.getComponent(1).setEnabled(true);

    }
}
