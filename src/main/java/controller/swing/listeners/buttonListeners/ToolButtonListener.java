package controller.swing.listeners.buttonListeners;

import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToolButtonListener implements ActionListener {

    private SwingViewImpl view;
    private Model model;
    private DrawMode drawMode;

    private JButton sourceButton;
    private SwingViewImpl.ToolDialog toolDialog;
    private JLabel strokeLabel;
    private JSlider strokeSlider;
    private JRadioButton solidStyleButton;
    private JRadioButton dottedStyleButton;


    private float modeStrokeFactor = 1;

    private static DrawMode previousDrawMode = DrawMode.PENCIL;


    public ToolButtonListener(SwingViewImpl view, Model model, DrawMode drawMode) {
        this.view = view;
        this.model = model;
        this.drawMode = drawMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (previousDrawMode == drawMode
                && drawMode != DrawMode.PASTE
                && drawMode != DrawMode.SCALE
                && drawMode != DrawMode.FILL) {
            sourceButton = (JButton) e.getSource();

            if (view.getToolDialog() == null || !view.getToolDialog().isVisible()) {
                toolDialog = view.new ToolDialog(
                        view, sourceButton.getToolTipText());
                view.getMainPanel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        toolDialog.dispose();
                    }
                });
                setListeners();

                view.setToolDialog(toolDialog);
                toolDialog.setLocation(
                        sourceButton.getX(),
                        sourceButton.getY() + sourceButton.getHeight()
                );
                setToolValues();

                toolDialog.setVisible(true);
            } else {
                view.getToolDialog().dispose();
            }
        } else {
            if (view.getToolDialog() != null) {
                view.getToolDialog().dispose();
                view.setToolDialog(null);
            }
        }

        view.resetToolButtonBorders();
        model.resetAllCustomPoints();
        view.getToolButtons().get(drawMode.name()).setBorderPainted(true);


        if (model.getCopyModeList().contains(previousDrawMode)
        && !model.getCopyModeList().contains(drawMode)) {
            view.loadSavedImage();
        }
        if (drawMode == DrawMode.FILL) {
            view.setMainColor(Color.white);
            view.getColorButton().setBackground(view.getMainColor());
        }


        if (!model.getCopyModeList().contains(previousDrawMode)
        && model.getShapeModeList().contains(drawMode)) {
            view.saveCurrentImage();
        }

        if (model.getShapeModeList().contains(drawMode)) {
            model.setShapeMode(true);
        } else {
            model.setShapeMode(false);
        }
        if (model.getCustomShapeModeList().contains(drawMode)) {
            model.setCustomShapeMode(true);
        } else {
            model.setCustomShapeMode(false);
        }
        if (model.getCopyModeList().contains(drawMode)) {
            model.setCopyMode(true);
        } else {
            model.setCopyMode(false);
        }
        if (model.isPolygonInWork()) {
            view.setMainImage(model.getPreviousAction(view.getTabbedPane().getSelectedIndex()));
            view.getMainPanel().repaint();
            model.setPolygonInWork(false);
        }

        if (drawMode == DrawMode.CUT) {
            model.setRectExtract(true);
        } else {
            model.setRectExtract(false);
        }

        if (drawMode == DrawMode.CUT_SHAPE
                || drawMode == DrawMode.CUT) {
            model.setCutting(true);
            model.setFirstMove(true);
        }

        if ((previousDrawMode == DrawMode.CUT_SHAPE
                && drawMode != DrawMode.CUT_SHAPE)
                || (previousDrawMode == DrawMode.CUT
                && drawMode != DrawMode.CUT)) {
            model.setCutting(false);
            model.setFirstMove(false);
        }

        if (drawMode == DrawMode.CUT_SHAPE
                || drawMode == DrawMode.CUT) {
            model.setCutting(true);
            model.setFirstMove(true);
        }

        model.setDrawMode(drawMode);
        previousDrawMode = drawMode;
        view.getTabbedPane().setCursor(CursorBuilder.buildCursorByDrawMode(drawMode));
    }

    private void setListeners() {
        if (drawMode != DrawMode.CUT && drawMode != DrawMode.CUT_SHAPE) {
            strokeLabel = toolDialog.getWidthLabel();
            strokeSlider = toolDialog.getWidthSlider();
            solidStyleButton = toolDialog.getSolid();
            dottedStyleButton = toolDialog.getDotted();

            strokeSlider.addChangeListener(e -> {

                strokeLabel.setText(String.format("Line size is %s now",
                        String.valueOf(getStrokeSliderValue())));
                if (!strokeSlider.getValueIsAdjusting()) {
                    model.getStrokeList().put(drawMode, getStrokeSliderValue());
                }
            });

            toolDialog.getDefaultButton().addActionListener(e -> {
                model.getStrokeList().put(drawMode,
                        model.getDefaultStrokeList().get(drawMode)
                );
                strokeLabel.setText(String.format("Line size is %s now",
                        String.valueOf(getStrokeSliderValue())));
                setStrokeSliderValue();

                view.setMainColor(SwingViewImpl.DEFAULT_COLOR);
                view.getColorButton().setBackground(SwingViewImpl.DEFAULT_COLOR);

            });

//            solidStyleButton.addChangeListener(e -> {
//                if (solidStyleButton.isSelected()) {
//                    model.setDotted(false);
//                }
//            });
            dottedStyleButton.addChangeListener(e -> {
                if (dottedStyleButton.isSelected()) {
                    model.setDotted(true);
                }
            });
        } else {
            // TODO
        }

    }

    private float getStrokeSliderValue() {
        refreshModeStrokeFactor();
        return (float) strokeSlider.getValue() / modeStrokeFactor;
    }

    private void setStrokeSliderValue() {
        strokeSlider.setValue(
                (int) (model.getStrokeList().get(drawMode) * modeStrokeFactor));
    }

    private void refreshModeStrokeFactor() {
        switch (drawMode) {
            case RAG:
                modeStrokeFactor = 1;
                break;
            default:
                modeStrokeFactor = 10;
        }
    }

    private void setToolValues() {
        if (drawMode != DrawMode.CUT && drawMode != DrawMode.CUT_SHAPE) {

            refreshModeStrokeFactor();
            Float stroke = model.getStrokeList().get(drawMode);
            int intStroke = (int) (stroke * modeStrokeFactor);

            strokeSlider.setValue(intStroke);
            strokeLabel.setText(
                    String.format("Line size is %s now",
                            String.valueOf(getStrokeSliderValue())));
        } else {
            // TODO
        }
    }
}
