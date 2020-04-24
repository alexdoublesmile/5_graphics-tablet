package controller.swing.listeners.buttonListeners;

import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import view.swing.SwingViewImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolButtonListener implements ActionListener {

    private SwingViewImpl view;
    private Model model;
    private DrawMode drawMode;

    private static DrawMode previousDrawMode;


    public ToolButtonListener(SwingViewImpl view, Model model, DrawMode drawMode) {
        this.view = view;
        this.model = model;
        this.drawMode = drawMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        view.resetToolButtonBorders();
        model.resetAllCustomPoints();
        view.getToolButtons().get(drawMode.name()).setBorderPainted(true);
        if (drawMode == DrawMode.FILL) {
            view.setMainColor(Color.white);
            view.getColorButton().setBackground(view.getMainColor());
        }
        if (model.getFigureModeList().contains(drawMode)) {
            view.saveCurrentImage();
            model.setFigureMode(true);
        } else {
            model.setFigureMode(false);
        }
        if (model.getCustomModeList().contains(drawMode)) {
            model.setCustomMode(true);
        } else {
            model.setCustomMode(false);
        }
        if (model.isPolygonInWork()) {
            view.setMainImage(model.getPreviousAction());
            view.getMainPanel().repaint();
            model.setPolygonInWork(false);
        }

        model.setDrawMode(drawMode);
        previousDrawMode = drawMode;
        try {
            view.getMainPanel().setCursor(CursorBuilder.buildCursorByDrawMode(drawMode));
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}
