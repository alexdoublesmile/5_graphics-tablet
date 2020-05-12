package controller.swing.actions;

import config.Config;
import controller.swing.listeners.KeyboardListener;
import controller.swing.listeners.MouseDrawListener;
import model.Model;
import model.UndoRedoService;
import util.IconBuilder;
import util.TabUtil;
import view.swing.SwingViewImpl;
import view.swing.buttons.ButtonTabComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;

public class TabAction {
    private static final String LAST_TAB_MESSAGE = "Это последняя вкладочка, Катюша :) \n Не нужно её закрывать";
    private static final String RENAME_DIALOD_TITLE = "Rename %s";

    public static final int REMOVE_CURRENT = 1;
    public static final int REMOVE_OTHERS = 2;
    public static final int REMOVE_TO_THE_RIGHT = 3;
    public static final int REMOVE_TO_THE_LEFT = 4;

    public static AddTabAction buildAddTabAction(SwingViewImpl view, Model model, Component tabComponent, boolean needCopyImage) {
        return new AddTabAction(view, model, tabComponent, needCopyImage);
    }

    public static RenameTabAction buildRenameTabAction(SwingViewImpl view, Component tabComponent) {
        return new RenameTabAction(view, tabComponent);
    }

    public static RemoveTabAction buildRemoveTabAction(SwingViewImpl view, Model model, Component tabComponent, int removeMode) {
        return new RemoveTabAction(view, model, tabComponent, removeMode);
    }

    private static class AddTabAction extends AbstractAction {

        private SwingViewImpl view;
        private Model model;
        private JTabbedPane tabs;
        private Component tabComponent;
        private int newTabIndex;
        private int previousTabIndex;
        private boolean needCopyImage;

        public AddTabAction(SwingViewImpl view, Model model, Component tabComponent, boolean needCopyImage) {
            this.view = view;
            this.model = model;
            tabs = view.getTabbedPane();
            this.tabComponent = tabComponent;
            this.needCopyImage = needCopyImage;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (tabComponent != null) {
                previousTabIndex = view.getTabbedPane().indexOfTabComponent(tabComponent);

            } else {
                previousTabIndex = view.getTabbedPane().getSelectedIndex();
            }
            newTabIndex = getNewTabIndex();

            MouseDrawListener mouseDrawListeners = new MouseDrawListener(view, model);
            JPanel panel = view.new MyPanel(previousTabIndex, needCopyImage);
            panel.addMouseMotionListener(mouseDrawListeners.getMOUSE_MOTION_ADAPTER());
            panel.addMouseListener(mouseDrawListeners.getMOUSE_ADAPTER());

            JPanel tabHeader = new ButtonTabComponent(view, model);

            tabs.add(view.getPanelList().get(newTabIndex), newTabIndex);
            if (needCopyImage) {
                tabs.setTitleAt(newTabIndex, TabUtil.getCopyTabName(tabs.getTitleAt(previousTabIndex), getAllTitles()));
            } else {
                tabs.setTitleAt(newTabIndex, TabUtil.getDefaultTabName());
            }
            tabs.setTabComponentAt(newTabIndex, tabHeader);
            tabs.setSelectedIndex(newTabIndex);

            panel.addKeyListener(new KeyboardListener(view, model).getKEY_ADAPTER());

            model.getUndoList().add(newTabIndex, new UndoRedoService());
            model.saveAction(view.getImageList().get(newTabIndex), newTabIndex);
        }

        private String[] getAllTitles() {
            String[] allTitles = new String[tabs.getTabCount()];
            for (int i = 0; i < allTitles.length; i++) {
                allTitles[i] = tabs.getTitleAt(i);
            }

            return allTitles;
        }

        private int getNewTabIndex() {
            return previousTabIndex + 1;
        }
    }



    private static class RenameTabAction extends AbstractAction {
        private SwingViewImpl view;
        private Component tabComponent;
        private int tabIndex;

        public RenameTabAction(SwingViewImpl view, Component tabComponent) {
            this.view = view;
            this.tabComponent = tabComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tabIndex = view.getTabbedPane().indexOfTabComponent(tabComponent);
            JPanel tabPanel = (JPanel) tabComponent;
            JLabel tabLabel = (JLabel) tabPanel.getComponent(0);
            String answer = (String) JOptionPane.showInputDialog(
                    tabComponent,
                    null,
                    String.format(RENAME_DIALOD_TITLE, tabLabel.getText()),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    tabLabel.getText());
            if (answer != null && !answer.isEmpty()) {
                view.getTabbedPane().setTitleAt(tabIndex, answer);
            }

            tabComponent.revalidate();
        }
    }



    private static class RemoveTabAction extends AbstractAction {
        private SwingViewImpl view;
        private Model model;
        private Component tabComponent;
        private int tabIndex;
        private int removeMode;

        public RemoveTabAction(SwingViewImpl view, Model model, Component tabComponent, int removeMode) {
            this.view = view;
            this.model = model;
            this.tabComponent = tabComponent;
            this.removeMode = removeMode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tabComponent != null) {
                tabIndex = view.getTabbedPane().indexOfTabComponent(tabComponent);
            }

            int tabsCount = view.getTabbedPane().getTabCount();
            switch (removeMode) {
                case REMOVE_CURRENT:
                    if (tabIndex != -1 && view.getTabbedPane().getTabCount() > 1)
                        removeTabTotally(tabIndex);
                    else JOptionPane.showMessageDialog(tabComponent, LAST_TAB_MESSAGE);
                    break;
                case REMOVE_OTHERS:
                    for (int i = 0; i < tabsCount;) {
                        if (i != tabIndex && view.getTabbedPane().getTabCount() > i) {
                            removeTabTotally(i);
                            if (tabIndex > 0) tabIndex--;
                        } else {
                            i++;
                        }
                    }
                    break;
                case REMOVE_TO_THE_RIGHT:
                    for (int i = 0; i < tabsCount;) {
                        if (i > tabIndex && view.getTabbedPane().getTabCount() > i) {
                            removeTabTotally(i);
                        } else {
                            i++;
                        }
                    }
                    break;
                case REMOVE_TO_THE_LEFT:
                    for (int i = 0; i < tabsCount;) {
                        if (i < tabIndex && view.getTabbedPane().getTabCount() > i) {
                            removeTabTotally(i);
                            if (tabIndex > 0) tabIndex--;
                        } else {
                            break;
                        }
                    }
                    break;
            }
        }

        private void removeTabTotally(int index) {
            view.getTabbedPane().remove(index);
            view.getPanelList().remove(index);
            view.getImageList().remove(index);
            model.getUndoList().remove(index);
        }
    }

}
