package controller.listeners;

import config.Config;
import model.DrawMode;
import model.PaintModel;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class DrawListenerBuilder {
    private final MouseMotionAdapter MOUSE_MOTION_ADAPTER;
    private final MouseAdapter MOUSE_ADAPTER;
    private final KeyAdapter KEY_ADAPTER;

    private final InputMap inputMap;
    private final ActionMap actionMap;

    private SwingViewImpl view;
    private PaintModel model;
    private JPanel mainPanel;

    public DrawListenerBuilder(SwingViewImpl view, PaintModel model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        inputMap = mainPanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        actionMap = mainPanel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_DOWN_MASK), "redo");

        MOUSE_MOTION_ADAPTER = new MouseMotionAdapter();
        MOUSE_ADAPTER = new MouseAdapter();
        KEY_ADAPTER = new KeyAdapter();

        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                view.setMainImage(model.getPreviousAction());
                mainPanel.repaint();
            }
        });

        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                view.setMainImage(model.getNextAction());
                mainPanel.repaint();
            }
        });
    }

    private class MouseMotionAdapter implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (model.isMouseIsPressed() == true) {
                Graphics g = view.getMainImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(view.getMainColor());
                switch (model.getDrawMode()) {
                    case PENCIL:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                    case MARKER:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                    case BRUSH:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                        g2.drawLine(model.getxPad(), model.getyPad(), e.getX(), e.getY());
                        break;
                    case ERASER:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.ERASER_BASIC_STROKE))));
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
                if (view.getMainColor() != Color.black) {
                    view.setMainColor(Color.black);
                } else {
                    view.setMainColor(Color.red);
                }
                view.getColorButton().setBackground(view.getMainColor());
            }
            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());
            switch (model.getDrawMode()) {
                case PENCIL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case MARKER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case BRUSH:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case ERASER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.ERASER_BASIC_STROKE))));
                    g2.setColor(Color.WHITE);
                    g2.drawLine(model.getxPad(), model.getyPad(), model.getxPad()+1, model.getyPad()+1);
                    break;
                case FILL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FILL_BASIC_STROKE))));
                    g2.setColor(view.getMainColor());
                    g2.fillRect(0, 0, mainPanel.getSize().width, mainPanel.getSize().height);
                    mainPanel.repaint();
                    break;
                case TEXT:
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
                case PENCIL:
                    model.saveImage(view.getMainImage());
                    break;
                case MARKER:
                    model.saveImage(view.getMainImage());
                    break;
                case BRUSH:
                    model.saveImage(view.getMainImage());
                    break;
                case ERASER:
                    model.saveImage(view.getMainImage());
                    break;
                case LINE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g.drawLine(model.getXf(), model.getYf(), e.getX(), e.getY());
                    model.saveImage(view.getMainImage());
                    break;
                case ELLIPSE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
                    model.saveImage(view.getMainImage());
                    break;
                case RECTANGLE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
                    model.saveImage(view.getMainImage());
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
            if (model.getDrawMode().equals(DrawMode.TEXT)){

                Graphics g = view.getMainImage().getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(view.getMainColor());
                g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.TEXT_BASIC_STROKE))));

                String str = new  String("");
                str += e.getKeyChar();
                g2.setFont(new  Font(Config.getProperty(Config.TEXT_BASIC_FONT),
                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_STYLE)),
                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_SIZE))));
                g2.drawString(str, model.getxPad(), model.getyPad());

                model.setxPad(model.getxPad() + Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_OFFSET)));
                mainPanel.requestFocus();

                mainPanel.repaint();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
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
