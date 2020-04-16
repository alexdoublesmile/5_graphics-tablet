package controller.swing.listeners;

import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class FrameListener {
    private final FrameResizeListener frameResizeListener;

    private SwingViewImpl view;
    private Model model;
    private JPanel mainPanel;

    public FrameListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        frameResizeListener = new FrameResizeListener();
    }

    private class FrameResizeListener extends ComponentAdapter {

        public void componentResized(ComponentEvent evt) {
            if(model.isLoading() == false) {
//                mainPanel.setSize(view.getMainImage().getWidth(), view.getMainImage().getHeight());

                BufferedImage tempImage = new  BufferedImage(
                        mainPanel.getWidth(),
                        mainPanel.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = tempImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, mainPanel.getWidth(), mainPanel.getHeight());

                tempImage.setData(view.getMainImage().getRaster());
                view.setMainImage(tempImage);
                mainPanel.repaint();
            }
            model.setLoading(false);
        }
    }

    public FrameResizeListener getFrameResizeListener() {
        return frameResizeListener;
    }
}
