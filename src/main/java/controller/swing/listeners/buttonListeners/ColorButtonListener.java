package controller.swing.listeners.buttonListeners;

import view.swing.SwingViewImpl;
import view.swing.buttons.ColorButton;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class ColorButtonListener implements ActionListener{
    private SwingViewImpl view;
    private ColorButton button;
    private ColorDisplayButtonListener colorDisplayButtonListener;
    private ChooseColorListener chooseColorListener;

    public ColorButtonListener (SwingViewImpl view, ColorButton button) {
        this.view = view;
        this.button = button;
    }

    public ColorButtonListener (SwingViewImpl view) {
        this.view = view;
        colorDisplayButtonListener = new ColorDisplayButtonListener();
        chooseColorListener = new ChooseColorListener();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.setMainColor(button.getColor());
        view.getColorButton().setBackground(view.getMainColor());
    }

    private class ColorDisplayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingViewImpl.ColorDialog colorDialog = view.new ColorDialog(view);
            colorDialog.setLocation(350, 40);
            colorDialog.setVisible(true);
        }
    }

    private class ChooseColorListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            view.setMainColor(view.getColorChooser().getColor());
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    public ColorDisplayButtonListener getColorDisplayButtonListener() {
        return colorDisplayButtonListener;
    }

    public ChooseColorListener getChooseColorListener() {
        return chooseColorListener;
    }
}
