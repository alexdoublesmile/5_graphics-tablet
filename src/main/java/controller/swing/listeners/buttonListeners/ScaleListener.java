package controller.swing.listeners.buttonListeners;
import controller.swing.SwingControllerImpl;
import controller.swing.listeners.MouseDrawListener;
import model.Model;
import view.swing.SwingViewImpl;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ScaleListener {
    private final PlusButtonListener PLUS_BUTTON_LISTENER;
    private final MinusButtonListener MINUS_BUTTON_LISTENER;
    private final RefreshButtonListener REFRESH_BUTTON_LISTENER;

    private SwingViewImpl view;
    private Model model;
    private SwingControllerImpl controller;

    public ScaleListener(SwingViewImpl view, Model model, SwingControllerImpl controller) {
        this.view = view;
        this.model = model;
        this.controller = controller;

        PLUS_BUTTON_LISTENER = new PlusButtonListener();
        MINUS_BUTTON_LISTENER = new MinusButtonListener();
        REFRESH_BUTTON_LISTENER = new RefreshButtonListener();
    }

    private class PlusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
//            if (!model.isScaleMode()) {
//                model.setNoButtonMode(true);
//                model.setDrawMode(DrawMode.SCALE);
//                view.getMainPanel().setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.SCALE));
//                view.saveCurrentImage();
//            }
//            model.setScaleMode(true);
//            model.setDrawPanelWidth(view.getMainImage().getWidth());
//            model.setDrawPanelHeight(view.getMainImage().getHeight());

//            view.setImageScale(2);
//            view.getMainPanel().repaint();
//
//            view.testSizes();
        }
    }

    private class MinusButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            view.setImageScale(view.getImageScale() / Model.RESIZE_PLUS_FACTOR);
            model.setCurrentScale(model.getCurrentScale() / Model.RESIZE_PLUS_FACTOR);
            model.setDrawPanelWidth(view.getWidth());
            model.setDrawPanelHeight(view.getHeight());

            if (model.isScaleMode()) {
                view.loadSavedImage();
            }


            view.setImageScale(0);
            view.getMainPanel().repaint();

            view.testSizes();
        }
    }

    private class RefreshButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setImageScale(1);
            model.setCurrentScale(1);

            model.setDrawPanelWidth(view.getWidth());
            model.setDrawPanelHeight(view.getHeight());
            view.setTranslateImage(0, 0);


            if (model.isScaleMode()) {
                view.loadSavedImage();
            }
            view.getMainPanel().repaint();

            view.testSizes();

        }
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

