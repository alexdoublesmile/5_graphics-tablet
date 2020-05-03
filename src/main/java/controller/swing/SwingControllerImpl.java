package controller.swing;

import controller.Controller;
import controller.swing.listeners.*;
import controller.swing.listeners.buttonListeners.*;
import controller.swing.menuActions.FileAction;
import model.DrawMode;
import model.Model;
import model.UndoRedoService;
import util.CursorBuilder;
import view.swing.SwingViewImpl;
import view.View;

import javax.swing.*;

public class SwingControllerImpl implements Controller {

    private SwingViewImpl view;
    private Model model;

    public SwingControllerImpl(View view, Model model) {
        this.model = model;
        this.view = (SwingViewImpl) view;
    }

    @Override
    public void load() {
        SwingUtilities.invokeLater(() -> initAndShowGUI());
    }

    private void initAndShowGUI() {
        view.compareCanvas();
        setMenuActions();
        setToolListeners();
        setFunctionListeners();
        setDrawListeners();
        setFrameListeners();
        view.setVisible(true);

        model.getUndoList().add(new UndoRedoService());
        model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
//        model.saveAction(view.getMainPanel(), view.getTabbedPane().getSelectedIndex());

    }

    public void setMenuActions() {
        FileAction fileAction = new FileAction(view, model);

        view.getLoadMenu().setAction(fileAction.getLOAD_MENU_ACTION());
        view.getSaveMenu().setAction(fileAction.getSAVE_MENU_ACTION());
        view.getSaveAsMenu().setAction(fileAction.getSAVE_AS_MENU_ACTION());
    }

    private void setToolListeners() {
        UndoListener undoListener = new UndoListener(view, model);
        ScaleListener scaleListener = new ScaleListener(view, model, this);
        ColorButtonListener colorListener = new ColorButtonListener(view);

        for (DrawMode drawMode : DrawMode.values()) {
            if (!model.getSpecialModeList().contains(drawMode)) {
                view.getToolButtons().get(drawMode.name())
                        .addActionListener(new ToolButtonListener(view, model, drawMode));
            }
        }

        view.getTabbedPane().setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.PENCIL));

        view.getUndoButton().addActionListener(undoListener.getUNDO_BUTTON_LISTENER());
        view.getRedoButton().addActionListener(undoListener.getREDO_BUTTON_LISTENER());
        view.getPlusButton().addActionListener(scaleListener.getPLUS_BUTTON_LISTENER());
        view.getMinusButton().addActionListener(scaleListener.getMINUS_BUTTON_LISTENER());
        view.getRefreshButton().addActionListener(scaleListener.getREFRESH_BUTTON_LISTENER());
        view.getExpandButton().addActionListener(scaleListener.getEXPAND_BUTTON_LISTENER());

        view.getColorButton().addActionListener(colorListener.getColorDisplayButtonListener());
        view.getColorChooser().getSelectionModel().addChangeListener(colorListener.getChooseColorListener());
        view.getRedButton().addActionListener(new ColorButtonListener(view, view.getRedButton()));
        view.getBlackButton().addActionListener(new ColorButtonListener(view, view.getBlackButton()));
        view.getGreyButton().addActionListener(new ColorButtonListener(view, view.getGreyButton()));
        view.getBlueButton().addActionListener(new ColorButtonListener(view, view.getBlueButton()));
        view.getGreenButton().addActionListener(new ColorButtonListener(view, view.getGreenButton()));
        view.getOrangeButton().addActionListener(new ColorButtonListener(view, view.getOrangeButton()));
        view.getWhiteButton().addActionListener(new ColorButtonListener(view, view.getWhiteButton()));
    }

    private void setFunctionListeners() {
        FunctionButtonListener buttonListener = new FunctionButtonListener(view, model);

        view.getDiscolorButton().addActionListener(buttonListener.getDISCOLOR_BUTTON_LISTENER());
        view.getCleanButton().addActionListener(buttonListener.getCLEAN_BUTTON_LISTENER());
        view.getCalculatorButton().addActionListener(buttonListener.getCALCULATOR_BUTTON_LISTENER());
    }

    public void setDrawListeners() {
        MouseDrawListener mouseDrawListeners = new MouseDrawListener(view, model);

        view.getMainPanel().addMouseMotionListener(mouseDrawListeners.getMOUSE_MOTION_ADAPTER());
        view.getMainPanel().addMouseListener(mouseDrawListeners.getMOUSE_ADAPTER());
        view.getMainPanel().addKeyListener(new KeyboardListener(view, model).getKEY_ADAPTER());
    }

    private void setFrameListeners() {
        view.addComponentListener(new FrameListener(view, model).getFRAME_RESIZE_LISTENER());
    }

    public View getView() {
        return view;
    }

}
