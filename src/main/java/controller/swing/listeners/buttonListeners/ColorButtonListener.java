package controller.swing.listeners.buttonListeners;

import controller.swing.listeners.KeyboardListener;
import view.swing.SwingViewImpl;
import view.swing.buttons.ColorButton;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class ColorButtonListener implements ActionListener{
    private SwingViewImpl view;
    private ColorButton button;
    private ColorDysplayButtonListener colorDysplayButtonListener;
    private ChooseColorListener chooseColorListener;

    public ColorButtonListener (SwingViewImpl view, ColorButton button) {
        this.view = view;
        this.button = button;
    }

    public ColorButtonListener (SwingViewImpl view) {
        this.view = view;
        colorDysplayButtonListener = new ColorDysplayButtonListener();
        chooseColorListener = new ChooseColorListener();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.setMainColor(button.getColor());
        view.getColorButton().setBackground(button.getColor());
    }

    private class ColorDysplayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingViewImpl.ColorDialog colorDialog = view.new ColorDialog(view);
            colorDialog.setLocation(170, 40);
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

    public ColorDysplayButtonListener getColorDysplayButtonListener() {
        return colorDysplayButtonListener;
    }

    public ChooseColorListener getChooseColorListener() {
        return chooseColorListener;
    }
}
