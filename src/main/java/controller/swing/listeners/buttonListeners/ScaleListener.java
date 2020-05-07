package controller.swing.listeners.buttonListeners;
import controller.swing.SwingControllerImpl;
import controller.swing.listeners.KeyboardListener;
import controller.swing.listeners.MouseDrawListener;
import model.Model;
import model.UndoRedoService;
import view.swing.SwingViewImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ScaleListener {
    private final PlusButtonListener PLUS_BUTTON_LISTENER;
    private final MinusButtonListener MINUS_BUTTON_LISTENER;
    private final RefreshButtonListener REFRESH_BUTTON_LISTENER;
    private final ExpandButtonListener EXPAND_BUTTON_LISTENER;

    private SwingViewImpl view;
    private Model model;
    private SwingControllerImpl controller;
//    private JPanel mainPanel;

    public ScaleListener(SwingViewImpl view, Model model, SwingControllerImpl controller) {
        this.view = view;
        this.model = model;
        this.controller = controller;
//        mainPanel = view.getMainPanel();

        PLUS_BUTTON_LISTENER = new PlusButtonListener();
        MINUS_BUTTON_LISTENER = new MinusButtonListener();
        REFRESH_BUTTON_LISTENER = new RefreshButtonListener();
        EXPAND_BUTTON_LISTENER = new ExpandButtonListener();
    }

    private class PlusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

//            if (!model.isScaleMode()) {
//                model.setSpecialMode(true);
//                model.setDrawMode(DrawMode.SCALE);
//                view.getMainPanel().setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.SCALE));
////                view.saveCurrentImage();
//            }
//            model.setScaleMode(true);
//            model.setDrawWidth(view.getMainImage().getWidth());
//            model.setDrawHeight(view.getMainImage().getHeight());



            MouseDrawListener.increaseCustomFactor();
            MouseDrawListener.rebaseDefaultStroke();
        }
    }

    private class MinusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {


//            view.setImageScale(view.getImageScale() / Model.RESIZE_PLUS_FACTOR);
//            model.setCurrentScale(model.getCurrentScale() / Model.RESIZE_PLUS_FACTOR);
//            model.setDrawWidth(view.getWidth());
//            model.setDrawHeight(view.getHeight());
//
////            if (model.isScaleMode()) {
////                view.loadSavedImage();
////            }
//
//            view.getMainPanel().repaint();



            MouseDrawListener.decreaseCustomFactor();
            MouseDrawListener.rebaseDefaultStroke();
        }
    }

    private class RefreshButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
//            view.setImageScale(1);
//            model.setCurrentScale(1);
//
//            model.setDrawWidth(view.getWidth());
//            model.setDrawHeight(view.getHeight());
//            view.setTranslateImage(0, 0);


//            if (model.isScaleMode()) {
//                view.loadSavedImage();
//            }
//            view.getMainPanel().repaint();



            MouseDrawListener.resetCustomFactor();
            MouseDrawListener.rebaseDefaultStroke();
        }
    }

    private class ExpandButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!view.isExtended()) {
                view.setVisible(false);
                view.setUndecorated(true);
                view.setExtended(true);
                view.setVisible(true);

            } else {
                view.setVisible(false);
                view.setUndecorated(false);
                view.setExtended(false);
                view.setVisible(true);
            }

        }
    }

    public ExpandButtonListener getEXPAND_BUTTON_LISTENER() {
        return EXPAND_BUTTON_LISTENER;
    }

    public PlusButtonListener getPLUS_BUTTON_LISTENER() {
        return PLUS_BUTTON_LISTENER;
    }
    public MinusButtonListener getMINUS_BUTTON_LISTENER() {
        return MINUS_BUTTON_LISTENER;
    }

    public RefreshButtonListener getREFRESH_BUTTON_LISTENER() {
        return REFRESH_BUTTON_LISTENER;
    }
}

