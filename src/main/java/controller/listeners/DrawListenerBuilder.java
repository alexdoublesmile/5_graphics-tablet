package controller.listeners;

import model.PaintModel;
import view.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawListenerBuilder {
    private final MouseMotionAdapter MOUSE_MOTION_ADAPTER;
    private final MouseAdapter MOUSE_ADAPTER;
    private final KeyAdapter KEY_ADAPTER;


    private SwingViewImpl view;
    private PaintModel model;
    private JPanel mainPanel;
    private int num = 1;

    public DrawListenerBuilder(SwingViewImpl view, PaintModel model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        MOUSE_MOTION_ADAPTER = new MouseMotionAdapter();
        MOUSE_ADAPTER = new MouseAdapter();
        KEY_ADAPTER = new KeyAdapter();
    }

    private class MouseMotionAdapter implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (model.isMouseIsPressed() == true) {
                Graphics g = view.getMainImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(view.getMainColor());
                switch (model.getDrawMode()) {
                    case 0:
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                    case 1:
                        g2.setStroke(new  BasicStroke(5.0f));
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                    case 2:
                        g2.setStroke(new  BasicStroke(30.0f));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                }
                model.setxPad(e.getX());
                model.setyPad(e.getY());
            }
            mainPanel.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    private class MouseAdapter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (view.getMainColor() == Color.red) {
                    view.setMainColor(Color.black);
                } else if (view.getMainColor() == Color.black) {
                    view.setMainColor(Color.red);
                }
                view.getColorButton().setBackground(view.getMainColor());
            }
            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());
            switch (model.getDrawMode()) {
                case 0:
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case 1:
                    g2.setStroke(new  BasicStroke(3.0f));
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case 2:
                    g2.setStroke(new  BasicStroke(15.0f));
                    g2.setColor(Color.WHITE);
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case 3:
                    mainPanel.requestFocus();
                    break;
            }
            model.setxPad(e.getX());
            model.setyPad(e.getY());

            model.setMouseIsPressed(true);
            mainPanel.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            model.setxPad(e.getX());
            model.setyPad(e.getY());
            model.setXf(e.getX());
            model.setYf(e.getY());

            model.setMouseIsPressed(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());
            // Общие рассчеты для овала и прямоугольника
            int  x1 = model.getXf(), x2 = model.getxPad(), y1 = model.getYf(), y2 = model.getyPad();
            if(model.getXf() > model.getxPad()) {
                x2 = model.getXf(); x1 = model.getxPad();
            }
            if(model.getYf() > model.getyPad()) {
                y2 = model.getYf(); y1 = model.getyPad();
            }

            switch(model.getDrawMode()) {
                case 0:
                    model.saveImage(view.getMainImage());
                    break;
                case 1:
                    model.saveImage(view.getMainImage());
                    break;
                case 2:
                    model.saveImage(view.getMainImage());
                    break;
                case 4:
                    g.drawLine(model.getXf(), model.getYf(), e.getX(), e.getY());
                    break;
                case 5:
                    g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                    break;
                case 6:
                    g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                    break;
            }
            model.setxPad(0);
            model.setyPad(0);
            model.setMouseIsPressed(true);
            mainPanel.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class KeyAdapter implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {


            if (model.getDrawMode() == 3){

                Graphics g = view.getMainImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(view.getMainColor());
                g2.setStroke(new  BasicStroke(2.0f));

                String str = new  String("");
                str += e.getKeyChar();
                g2.setFont(new  Font("Arial", 0, 15));
                g2.drawString(str, model.getxPad(), model.getyPad());

                model.setxPad(model.getxPad() + 10);

                mainPanel.requestFocus();
                mainPanel.repaint();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_Z:
                    view.setMainImage(model.getPreviousAction());
                    mainPanel.repaint();
                    break;
                case KeyEvent.VK_X:
                    view.setMainImage(model.getNextAction());
                    mainPanel.repaint();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public MouseMotionAdapter getMOUSE_MOTION_ADAPTER() {
        return MOUSE_MOTION_ADAPTER;
    }

    public MouseAdapter getMOUSE_ADAPTER() {
        return MOUSE_ADAPTER;
    }

    public KeyAdapter getKEY_ADAPTER() {
        return KEY_ADAPTER;
    }
}
