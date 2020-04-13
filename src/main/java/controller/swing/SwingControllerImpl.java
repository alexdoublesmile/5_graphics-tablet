package controller.swing;

import controller.Controller;
import controller.swing.listeners.FrameListener;
import controller.swing.listeners.UndoListener;
import controller.swing.listeners.buttonListeners.FunctionButtonListener;
import controller.swing.menuActions.FileAction;
import controller.swing.listeners.buttonListeners.ColorButtonListener;
import controller.swing.listeners.DrawListener;
import controller.swing.listeners.buttonListeners.ToolButtonListener;
import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import view.swing.SwingViewImpl;
import view.View;

public class SwingControllerImpl implements Controller {

    private SwingViewImpl view;
    private Model model;

    public SwingControllerImpl(View view, Model model) {
        this.model = model;
        this.view = (SwingViewImpl) view;
    }

    @Override
    public void load() {
        model.saveAction(view.getMainImage());

        setMenuActions();
        setToolListeners();
        setFunctionListeners();
        setDrawListeners();
        setFrameListeners();

        view.setLayout(null);
        view.setVisible(true);
    }

    public void setMenuActions() {
        FileAction fileAction = new FileAction(view, model);

        view.getLoadMenu().setAction(fileAction.getLOAD_MENU_ACTION());
        view.getSaveMenu().setAction(fileAction.getSAVE_MENU_ACTION());
        view.getSaveAsMenu().setAction(fileAction.getSAVE_AS_MENU_ACTION());
    }

    private void setToolListeners() {
        UndoListener undoListener = new UndoListener(view, model);
        ColorButtonListener colorListener = new ColorButtonListener(view);

        for (DrawMode drawMode : DrawMode.values()) {
            view.getToolButtons().get(drawMode.name())
                    .addActionListener(new ToolButtonListener(view, model, drawMode));
        }
        try {
            view.getMainPanel().setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.PENCIL));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        view.getUndoButton().addActionListener(undoListener.getUNDO_BUTTON_LISTENER());
        view.getRedoButton().addActionListener(undoListener.getREDO_BUTTON_LISTENER());

        view.getColorButton().addActionListener(colorListener.getColorDysplayButtonListener());
        view.getColorChooser().getSelectionModel().addChangeListener(colorListener.getChooseColorListener());
        view.getRedButton().addActionListener(new ColorButtonListener(view, view.getRedButton()));
        view.getBlackButton().addActionListener(new ColorButtonListener(view, view.getBlackButton()));
        view.getBlueButton().addActionListener(new ColorButtonListener(view, view.getBlueButton()));
        view.getGreenButton().addActionListener(new ColorButtonListener(view, view.getGreenButton()));
        view.getWhiteButton().addActionListener(new ColorButtonListener(view, view.getWhiteButton()));
    }

    private void setFunctionListeners() {
        FunctionButtonListener buttonListener = new FunctionButtonListener(view, model);

        view.getDiscolorButton().addActionListener(buttonListener.getDISCOLOR_BUTTON_LISTENER());
        view.getCleanButton().addActionListener(buttonListener.getCLEAN_BUTTON_LISTENER());
        view.getCalculatorButton().addActionListener(buttonListener.getCALCULATOR_BUTTON_LISTENER());
    }

    private void setDrawListeners() {
        DrawListener drawListeners = new DrawListener(view, model);

        view.getMainPanel().addMouseMotionListener(drawListeners.getMOUSE_MOTION_ADAPTER());
        view.getMainPanel().addMouseListener(drawListeners.getMOUSE_ADAPTER());
        view.getMainPanel().addKeyListener(drawListeners.getKEY_ADAPTER());
        view.addKeyListener(drawListeners.getKEY_ADAPTER());

    }

    private void setFrameListeners() {
        view.addComponentListener(new FrameListener(view, model).getFrameResizeListener());
    }

    public View getView() {
        return view;
    }
}
