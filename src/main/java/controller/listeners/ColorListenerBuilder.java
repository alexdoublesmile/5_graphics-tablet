package controller.listeners;

import model.PaintModel;
import view.swing.SwingViewImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorListenerBuilder {
    private final ColorButtonListener COLOR_BUTTON_LISTENER;
    private final RedButtonListener RED_BUTTON_LISTENER;
    private final BlackButtonListener BLACK_BUTTON_LISTENER;
    private final BlueButtonListener BLUE_BUTTON_LISTENER;
    private final GreenButtonListener GREEN_BUTTON_LISTENER;
    private final WhiteButtonListener WHITE_BUTTON_LISTENER;

    private SwingViewImpl view;
    private PaintModel model;

    public ColorListenerBuilder(SwingViewImpl view, PaintModel model) {
        this.view = view;
        this.model = model;

        COLOR_BUTTON_LISTENER = new ColorButtonListener();
        RED_BUTTON_LISTENER = new RedButtonListener();
        BLACK_BUTTON_LISTENER = new BlackButtonListener();
        BLUE_BUTTON_LISTENER = new BlueButtonListener();
        GREEN_BUTTON_LISTENER = new GreenButtonListener();
        WHITE_BUTTON_LISTENER = new WhiteButtonListener();
    }

    private class ColorButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            SwingViewImpl.ColorDialog colorDialog = view.new ColorDialog(view,"Выбор цвета");
            colorDialog.setVisible(true);
        }
    }

    private class RedButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainColor(Color.red);
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    private class BlackButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainColor(Color.black);
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    private class BlueButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainColor(Color.blue);
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    private class GreenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainColor(new Color(0x128D12));
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    private class WhiteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMainColor(Color.white);
            view.getColorButton().setBackground(view.getMainColor());
        }
    }

    public ColorButtonListener getCOLOR_BUTTON_LISTENER() {
        return COLOR_BUTTON_LISTENER;
    }

    public RedButtonListener getRED_BUTTON_LISTENER() {
        return RED_BUTTON_LISTENER;
    }

    public BlackButtonListener getBLACK_BUTTON_LISTENER() {
        return BLACK_BUTTON_LISTENER;
    }

    public BlueButtonListener getBLUE_BUTTON_LISTENER() {
        return BLUE_BUTTON_LISTENER;
    }

    public GreenButtonListener getGREEN_BUTTON_LISTENER() {
        return GREEN_BUTTON_LISTENER;
    }

    public WhiteButtonListener getWHITE_BUTTON_LISTENER() {
        return WHITE_BUTTON_LISTENER;
    }
}
