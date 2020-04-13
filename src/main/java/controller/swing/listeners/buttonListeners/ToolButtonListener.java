package controller.swing.listeners.buttonListeners;

import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolButtonListener implements ActionListener {

    private SwingViewImpl view;
    private Model model;
    private DrawMode drawMode;

    public ToolButtonListener(SwingViewImpl view, Model model, DrawMode drawMode) {
        this.view = view;
        this.model = model;
        this.drawMode = drawMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.resetToolButtonBorders();
        view.getToolButtons().get(drawMode.name()).setBorderPainted(true);

        model.setDrawMode(drawMode);
        try {
            view.getMainPanel().setCursor(CursorBuilder.buildCursorByDrawMode(drawMode));
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}
