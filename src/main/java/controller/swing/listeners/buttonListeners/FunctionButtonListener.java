package controller.swing.listeners.buttonListeners;

import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class FunctionButtonListener {

    private final DiscolorButtonListener DISCOLOR_BUTTON_LISTENER;
    private final CleanButtonListener CLEAN_BUTTON_LISTENER;
    private final CalculatorButtonListener CALCULATOR_BUTTON_LISTENER;

    private SwingViewImpl view;
    private Model model;

    public FunctionButtonListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        DISCOLOR_BUTTON_LISTENER = new DiscolorButtonListener();
        CLEAN_BUTTON_LISTENER = new CleanButtonListener();
        CALCULATOR_BUTTON_LISTENER = new CalculatorButtonListener();
    }





    private class DiscolorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BufferedImage source = view.getMainImage();
            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

            for (int x = 0; x < source.getWidth(); x++) {
                for (int y = 0; y < source.getHeight(); y++) {

                    Color color = new Color(source.getRGB(x, y));
                    int blue = color.getBlue();
                    int red = color.getRed();
                    int green = color.getGreen();

                    int grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                    int newRed = grey;
                    int newGreen = grey;
                    int newBlue = grey;
                    Color newColor = new Color(newRed, newGreen, newBlue);
                    result.setRGB(x, y, newColor.getRGB());
                }
            }

            view.setMainImage(result);
            model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
            view.saveCurrentImage();
            view.getMainPanel().repaint();
        }
    }

    private class CleanButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (model.isPolygonInWork()) {
                view.setMainImage(model.getPreviousAction(view.getTabbedPane().getSelectedIndex()));
                view.getMainPanel().repaint();
                model.setPolygonInWork(false);

            }
            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.white);
            g2.fillRect(0, 0, view.getMainPanel().getSize().width, view.getMainPanel().getSize().height);
            model.resetAllCustomPoints();
            g2.setColor(Color.black);

            model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
            view.saveCurrentImage();
            view.getMainPanel().repaint();
        }
    }

    private class CalculatorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // TODO: 10.04.2020
            JOptionPane.showMessageDialog(view.getMainFrame(),
                    "Этот фукционал еще в нескорой разработке, Катюша :)");
        }
    }

    public CleanButtonListener getCLEAN_BUTTON_LISTENER() {
        return CLEAN_BUTTON_LISTENER;
    }

    public DiscolorButtonListener getDISCOLOR_BUTTON_LISTENER() {
        return DISCOLOR_BUTTON_LISTENER;
    }

    public CalculatorButtonListener getCALCULATOR_BUTTON_LISTENER() {
        return CALCULATOR_BUTTON_LISTENER;
    }
}