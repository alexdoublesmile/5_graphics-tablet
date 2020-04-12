package controller;

import controller.actions.LoadMenuAction;
import controller.actions.SaveMenuAction;
import controller.actions.SaveAsMenuAction;
import controller.listeners.ColorListenerBuilder;
import controller.listeners.DrawListenerBuilder;
import controller.listeners.ToolListenerBuilder;
import model.PaintModel;
import view.swing.SwingViewImpl;
import view.View;

public class SwingControllerImpl implements Controller{

    private SwingViewImpl view;
    private PaintModel model;

    public SwingControllerImpl(View view, PaintModel model) {
        this.model = model;
        this.view = (SwingViewImpl) view;
        setControlSettings();
        this.view.drawTablet();
    }

    @Override
    public void setControlSettings() {
        model.saveAction(view.getMainImage());

        setMenuActions();
        setToolListeners();
        setDrawListeners();
        setImageListeners();

        view.setLayout(null);
        view.setVisible(true);
    }

    public void setMenuActions() {
        view.getLoadMenu().setAction(new LoadMenuAction(view, model, "Open"));
        view.getSaveMenu().setAction(new SaveMenuAction(view, model, "Save"));
        view.getSaveAsMenu().setAction(new SaveAsMenuAction(view, model, "Save as.."));
    }

    private void setToolListeners() {
        ToolListenerBuilder tools = new ToolListenerBuilder(view, model);
        ColorListenerBuilder colorTools = new ColorListenerBuilder(view, model);

        view.getPencilButton().addActionListener(tools.getPENCIL_BUTTON_LISTENER());
        view.getMarkerButton().addActionListener(tools.getMARKER_BUTTON_LISTENER());
        view.getBrushButton().addActionListener(tools.getBRUSH_BUTTON_LISTENER());
        view.getEraserButton().addActionListener(tools.getERASER_BUTTON_LISTENER());
        view.getRagButton().addActionListener(tools.getRAG_BUTTON_LISTENER());
        view.getLineButton().addActionListener(tools.getLINE_BUTTON_LISTENER());
        view.getDottedLineButton().addActionListener(tools.getDOTTED_LINE_BUTTON_LISTENER());
        view.getEllipseButton().addActionListener(tools.getELLIPSE_BUTTON_LISTENER());
        view.getRectButton().addActionListener(tools.getRECT_BUTTON_LISTENER());
        view.getPyramidButton().addActionListener(tools.getPYRAMID_BUTTON_LISTENER());
        view.getPrismButton().addActionListener(tools.getPRISM_BUTTON_LISTENER());
        view.getFillButton().addActionListener(tools.getFILL_BUTTON_LISTENER());

        view.getUndoButton().addActionListener(tools.getUNDO_BUTTON_LISTENER());
        view.getRedoButton().addActionListener(tools.getREDO_BUTTON_LISTENER());
        view.getTextButton().addActionListener(tools.getTEXT_BUTTON_LISTENER());

        view.getColorButton().addActionListener(colorTools.getCOLOR_BUTTON_LISTENER());
        view.getRedButton().addActionListener(colorTools.getRED_BUTTON_LISTENER());
        view.getBlackButton().addActionListener(colorTools.getBLACK_BUTTON_LISTENER());
        view.getBlueButton().addActionListener(colorTools.getBLUE_BUTTON_LISTENER());
        view.getGreenButton().addActionListener(colorTools.getGREEN_BUTTON_LISTENER());
        view.getWhiteButton().addActionListener(colorTools.getWHITE_BUTTON_LISTENER());

        view.getTransformImageButton().addActionListener(tools.getTRANSFORM_IMAGE_BUTTON_LISTENER());
        view.getCleanButton().addActionListener(tools.getCLEAN_BUTTON_LISTENER());
        view.getCalculatorButton().addActionListener(tools.getCALCULATOR_BUTTON_LISTENER());

    }

    private void setDrawListeners() {
        DrawListenerBuilder drawListeners = new DrawListenerBuilder(view, model);

        view.getMainPanel().addMouseMotionListener(drawListeners.getMOUSE_MOTION_ADAPTER());
        view.getMainPanel().addMouseListener(drawListeners.getMOUSE_ADAPTER());
        view.getMainPanel().addKeyListener(drawListeners.getKEY_ADAPTER());
        view.addKeyListener(drawListeners.getKEY_ADAPTER());

    }

    private void setImageListeners() {

    }

    public View getView() {
        return view;
    }
}
