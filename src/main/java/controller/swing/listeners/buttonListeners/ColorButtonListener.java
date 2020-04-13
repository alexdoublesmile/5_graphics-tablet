package controller.swing.listeners.buttonListeners;

import view.swing.SwingViewImpl;
import view.swing.buttons.ColorButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorButtonListener implements ActionListener{
    private static final String COLOR_DIALOG_TITLE = "Выбор цвета";
    private final int COLOR_DIALOG_WIDTH = 200;
    private final int COLOR_DIALOG_HEIGHT = 200;

    private SwingViewImpl view;
    private ColorButton button;
    private ChooseColorListener chooseColorListener;

    public ColorButtonListener (SwingViewImpl view, ColorButton button) {
        this.view = view;
        this.button = button;
    }

    public ColorButtonListener (SwingViewImpl view) {
        this.view = view;
        chooseColorListener = new ChooseColorListener();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.setMainColor(button.getColor());
        view.getColorButton().setBackground(button.getColor());
    }

    private class ChooseColorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingViewImpl.ColorDialog colorDialog = view.new ColorDialog(view, COLOR_DIALOG_TITLE, COLOR_DIALOG_WIDTH, COLOR_DIALOG_HEIGHT);
            colorDialog.setVisible(true);
        }
    }

    public ChooseColorListener getChooseColorListener() {
        return chooseColorListener;
    }
}
