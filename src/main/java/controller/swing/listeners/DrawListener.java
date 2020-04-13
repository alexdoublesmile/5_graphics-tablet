package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class DrawListener {
    private final MouseMotionAdapter MOUSE_MOTION_ADAPTER;
    private final MouseAdapter MOUSE_ADAPTER;
    private final KeyAdapter KEY_ADAPTER;

    private final InputMap inputMap;
    private final ActionMap actionMap;

    private SwingViewImpl view;
    private Model model;
    private JPanel mainPanel;

    public DrawListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();

        inputMap = mainPanel.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        actionMap = mainPanel.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), "redo");

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
        public void mouseDragged(MouseEvent mouseEvent) {
            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());

            view.loadSavedImage();

                switch (model.getDrawMode()) {
                    case PENCIL:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                        g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), mouseEvent.getX(), mouseEvent.getY());

                        model.setFinalXPoint(mouseEvent.getX());
                        model.setFinalYPoint(mouseEvent.getY());
                        view.saveCurrentImage();
                        break;
                    case MARKER:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                        g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), mouseEvent.getX(), mouseEvent.getY());

                        model.setFinalXPoint(mouseEvent.getX());
                        model.setFinalYPoint(mouseEvent.getY());
                        view.saveCurrentImage();
                        break;
                    case BRUSH:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                        g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), mouseEvent.getX(), mouseEvent.getY());

                        model.setFinalXPoint(mouseEvent.getX());
                        model.setFinalYPoint(mouseEvent.getY());
                        view.saveCurrentImage();
                        break;
                    case ERASER:
                        g2.setStroke(new  BasicStroke(model.getEraserStroke()));
                        g2.setColor(Color.WHITE);
                        g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(Config.ERASER_XPOINT_OFFSET)),
                                mouseEvent.getY() + Integer.parseInt(Config.getProperty(Config.ERASER_YPOINT_OFFSET)),
                                model.getEraserStroke(),
                                model.getEraserStroke());

                        model.setFinalXPoint(mouseEvent.getX());
                        model.setFinalYPoint(mouseEvent.getY());
                        view.saveCurrentImage();
                        break;
                    case RAG:
                        g2.setStroke(new  BasicStroke(model.getRagStroke()));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), mouseEvent.getX(), mouseEvent.getY());

                        model.setFinalXPoint(mouseEvent.getX());
                        model.setFinalYPoint(mouseEvent.getY());
                        view.saveCurrentImage();
                        break;

                    case LINE:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        g2.drawLine(model.getStartXPoint(), model.getStartYPoint(), model.getFinalXPoint(), model.getFinalYPoint());
                        break;
                    case DOTTEDLINE:
                        g2.setStroke(new BasicStroke(
                                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                new float[]{7.0f},
                                0.0f));
                        g2.drawLine(model.getStartXPoint(), model.getStartYPoint(), model.getFinalXPoint(), model.getFinalYPoint());
                        break;
                    case CIRCLE:

                        int radius = Math.abs(model.getStartXPoint() - model.getFinalXPoint()) / 2;

                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        g2.drawOval(
                                (int) (model.getOvalCenterPoint().getX() - radius),
                                (int) (model.getOvalCenterPoint().getY() - radius),
                                (int) Math.abs(model.getOvalCenterPoint().getX() - model.getFinalXPoint()),
                                (int) Math.abs(model.getOvalCenterPoint().getX() - model.getFinalXPoint())
                        );
                        break;
                    case RECT:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        g2.drawRect(
                                Math.min(model.getFinalXPoint(), model.getStartXPoint()),
                                Math.min(model.getFinalYPoint(), model.getStartYPoint()),
                                Math.abs(model.getStartXPoint() - model.getFinalXPoint()),
                                Math.abs(model.getStartYPoint() - model.getFinalYPoint()));
                        break;
                    case PYRAMID:
                        double mainLineLenght = Math.sqrt(
                                Math.pow(model.getStartXPoint() - model.getFinalXPoint(), 2.0) +
                                        Math.pow(model.getStartYPoint() - model.getFinalYPoint(), 2.0)
                        );

                        g2.setStroke(new BasicStroke(
                                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                new float[]{7.0f},
                                0.0f));

                        g2.drawLine(
                                model.getStartXPoint(),
                                model.getStartYPoint(),
                                (int) (model.getStartXPoint() - mainLineLenght * 0.2),
                                model.getFinalYPoint());

                        g2.drawLine(
                                model.getStartXPoint(),
                                model.getStartYPoint(),
                                (int) (model.getFinalXPoint() - mainLineLenght * 0.223),
                                (int) (model.getFinalYPoint() - mainLineLenght * 0.243));

                        g2.drawLine(
                                model.getStartXPoint(),
                                model.getStartYPoint(),
                                (int) (model.getFinalXPoint() + mainLineLenght * 0.624),
                                (int) (model.getFinalYPoint() - mainLineLenght * 0.243));

                        break;
                    case PRISM:
                        double frontLineLenght = Math.sqrt(
                                Math.pow(model.getStartXPoint() - model.getFinalXPoint(), 2.0) +
                                        Math.pow(model.getStartYPoint() - model.getFinalYPoint(), 2.0)
                        );


                        model.getTriPrismLeftTopPoint().setLocation(
                                (model.getStartXPoint() - frontLineLenght * 0.223),
                                (model.getStartYPoint() - frontLineLenght * 0.243));
                        model.getTriPrismRightTopPoint().setLocation(
                                (model.getStartXPoint() + frontLineLenght * 0.624),
                                (model.getStartYPoint() - frontLineLenght * 0.243));
                        model.getTriPrismLeftBottomPoint().setLocation(
                                (model.getStartXPoint() - frontLineLenght * 0.223),
                                (model.getFinalYPoint() - frontLineLenght * 0.243));
                        model.getTriPrismRightBottomPoint().setLocation(
                                (model.getStartXPoint() + frontLineLenght * 0.624),
                                (model.getFinalYPoint() - frontLineLenght * 0.243));


                        g2.setStroke(new BasicStroke(
                                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                new float[]{7.0f},
                                0.0f));

                        // main vertical line
                        g2.drawLine(
                                model.getFinalXPoint(),
                                model.getStartYPoint(),
                                model.getFinalXPoint(),
                                model.getFinalYPoint());

                        // main horizontal line
                        g2.drawLine(
                                (int) model.getTriPrismLeftTopPoint().getX(),
                                (int) model.getTriPrismLeftTopPoint().getY(),
                                (int) model.getTriPrismRightTopPoint().getX(),
                                (int) model.getTriPrismRightTopPoint().getY());

                        // left vertical line
                        g2.drawLine(
                                (int) model.getTriPrismLeftTopPoint().getX(),
                                (int) model.getTriPrismLeftTopPoint().getY(),
                                (int) model.getTriPrismLeftBottomPoint().getX(),
                                (int) model.getTriPrismLeftBottomPoint().getY());


                        // right vertical line
                        g2.drawLine(
                                (int) model.getTriPrismRightTopPoint().getX(),
                                (int) model.getTriPrismRightTopPoint().getY(),
                                (int) model.getTriPrismRightBottomPoint().getX(),
                                (int) model.getTriPrismRightBottomPoint().getY());


                        // left horizontal line
                        g2.drawLine(
                                model.getFinalXPoint(),
                                model.getFinalYPoint(),
                                (int) model.getTriPrismLeftBottomPoint().getX(),
                                (int) model.getTriPrismLeftBottomPoint().getY());


                        // right horizontal line
                        g2.drawLine(
                                model.getFinalXPoint(),
                                model.getFinalYPoint(),
                                (int) model.getTriPrismRightBottomPoint().getX(),
                                (int) model.getTriPrismRightBottomPoint().getY());
                        break;
                }
                model.setFinalXPoint(mouseEvent.getX());
                model.setFinalYPoint(mouseEvent.getY());
//            }
            mainPanel.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    private class MouseAdapter implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            listenMouseRightButton(mouseEvent);

            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());

//            view.loadSavedImage();

            switch (model.getDrawMode()) {
                case PENCIL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                    g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), model.getFinalXPoint()+1, model.getFinalYPoint()+1);
                    break;
                case MARKER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                    g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), model.getFinalXPoint()+1, model.getFinalYPoint()+1);
                    break;
                case BRUSH:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                    g2.drawLine(model.getFinalXPoint(), model.getFinalYPoint(), model.getFinalXPoint()+1, model.getFinalYPoint()+1);
                    break;
                case ERASER:
                    g2.setStroke(new  BasicStroke(model.getEraserStroke()));
                    g2.setColor(Color.WHITE);

                    g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(Config.ERASER_XPOINT_OFFSET)),
                            mouseEvent.getY() + Integer.parseInt(Config.getProperty(Config.ERASER_YPOINT_OFFSET)),
                            model.getEraserStroke(),
                            model.getEraserStroke());
                    break;
                case RAG:
                    g2.setStroke(new  BasicStroke(model.getRagStroke()));
                    g2.setColor(Color.WHITE);

                    g2.drawLine(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    break;
//                case FILL:
//                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FILL_BASIC_STROKE))));
//                    g2.setColor(view.getMainColor());
//                    g2.fillRect(0, 0, mainPanel.getSize().width, mainPanel.getSize().height);
//
//                    break;
                case TEXT:
//                    mainPanel.requestFocus();
                    break;
            }
            model.setFinalXPoint(mouseEvent.getX());
            model.setFinalYPoint(mouseEvent.getY());

            view.saveCurrentImage();
            mainPanel.repaint();
        }

        private void listenMouseRightButton(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                if (view.getMainColor() != Color.black) {
                    view.setMainColor(Color.black);
                } else {
                    view.setMainColor(Color.red);
                }
                view.getColorButton().setBackground(view.getMainColor());
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

            if (model.wasIterated()) {
                view.setPreviousImage(null);

            }
            if (view.getPreviousImage() == null) {
                view.saveCurrentImage();
            }

            model.setFinalXPoint(mouseEvent.getX());
            model.setFinalYPoint(mouseEvent.getY());
            model.setStartXPoint(mouseEvent.getX());
            model.setStartYPoint(mouseEvent.getY());

            if (model.getDrawMode() == DrawMode.CIRCLE) {
                model.setOvalCenterPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

            view.loadSavedImage();

//            model.resetIteratedFlag();

            Graphics g = view.getMainImage().getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(view.getMainColor());

            switch(model.getDrawMode()) {
                case LINE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g.drawLine(model.getStartXPoint(), model.getStartYPoint(), mouseEvent.getX(), mouseEvent.getY());

                    view.saveCurrentImage();
                    break;
                case DOTTEDLINE:
                    g2.setStroke(new BasicStroke(
                            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f,
                            new float[]{7.0f},
                            0.0f));
                    g.drawLine(model.getStartXPoint(), model.getStartYPoint(), mouseEvent.getX(), mouseEvent.getY());

                    view.saveCurrentImage();
                    break;
                case CIRCLE:

                    int radius = Math.abs(model.getStartXPoint() - model.getFinalXPoint()) / 2;

                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g2.drawOval(
                            (int) (model.getOvalCenterPoint().getX() - radius),
                            (int) (model.getOvalCenterPoint().getY() - radius),
                            (int) Math.abs(model.getOvalCenterPoint().getX() - model.getFinalXPoint()),
                            (int) Math.abs(model.getOvalCenterPoint().getX() - model.getFinalXPoint())
                    );
                    g2.drawLine(
                            (int) (model.getOvalCenterPoint().getX()),
                            (int) (model.getOvalCenterPoint().getY()),
                            (int) (model.getOvalCenterPoint().getX()),
                            (int) (model.getOvalCenterPoint().getY())
                    );

                    view.saveCurrentImage();
                    break;
                case RECT:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g2.drawRect(
                            Math.min(model.getFinalXPoint(), model.getStartXPoint()),
                            Math.min(model.getFinalYPoint(), model.getStartYPoint()),
                            Math.abs(model.getStartXPoint() - model.getFinalXPoint()),
                            Math.abs(model.getStartYPoint() - model.getFinalYPoint()));
                    g2.drawLine(
                            Math.min(model.getFinalXPoint(), model.getStartXPoint()) + Math.abs(model.getStartXPoint() - model.getFinalXPoint()) / 2,
                            Math.min(model.getFinalYPoint(), model.getStartYPoint()) + Math.abs(model.getStartYPoint() - model.getFinalYPoint()) / 2,
                            Math.min(model.getFinalXPoint(), model.getStartXPoint()) + Math.abs(model.getStartXPoint() - model.getFinalXPoint()) / 2,
                            Math.min(model.getFinalYPoint(), model.getStartYPoint()) + Math.abs(model.getStartYPoint() - model.getFinalYPoint()) / 2
                    );

                    view.saveCurrentImage();
                    break;
                case PYRAMID:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));

                    double mainLineLenght = Math.sqrt(
                            Math.pow(model.getStartXPoint() - model.getFinalXPoint(), 2.0) +
                                    Math.pow(model.getStartYPoint() - model.getFinalYPoint(), 2.0)
                    );

                    model.getTriPyramidLeftPoint().setLocation(
                            (model.getFinalXPoint() - mainLineLenght * 0.223),
                            (model.getFinalYPoint() - mainLineLenght * 0.243));
                    model.getTriPyramidRightPoint().setLocation(
                            (model.getFinalXPoint() + mainLineLenght * 0.624),
                            (model.getFinalYPoint() - mainLineLenght * 0.243));

                    g2.drawLine(
                            model.getStartXPoint(),
                            model.getStartYPoint(),
                            (int) (model.getStartXPoint() - mainLineLenght * 0.2),
                            model.getFinalYPoint());

                    g2.drawLine(
                            model.getStartXPoint(),
                            model.getStartYPoint(),
                            (int) (model.getFinalXPoint() - mainLineLenght * 0.223),
                            (int) (model.getFinalYPoint() - mainLineLenght * 0.243));

                    g2.drawLine(
                            model.getStartXPoint(),
                            model.getStartYPoint(),
                            (int) (model.getFinalXPoint() + mainLineLenght * 0.624),
                            (int) (model.getFinalYPoint() - mainLineLenght * 0.243));

                    g2.drawLine(model.getTriPyramidLeftPoint().x, model.getTriPyramidLeftPoint().y, (int) (model.getStartXPoint() - mainLineLenght * 0.2), model.getFinalYPoint());
                    g2.drawLine(model.getTriPyramidRightPoint().x, model.getTriPyramidRightPoint().y, (int) (model.getStartXPoint() - mainLineLenght * 0.2), model.getFinalYPoint());

                    g2.setStroke(new BasicStroke(
                            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f,
                            new float[]{7.0f},
                            0.0f));
                    g.drawLine(
                            model.getTriPyramidLeftPoint().x, model.getTriPyramidLeftPoint().y,
                            model.getTriPyramidRightPoint().x, model.getTriPyramidRightPoint().y);

                    view.saveCurrentImage();
                    break;
                case PRISM:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));

                    double frontLineLenght = Math.sqrt(
                            Math.pow(model.getStartXPoint() - model.getFinalXPoint(), 2.0) +
                                    Math.pow(model.getStartYPoint() - model.getFinalYPoint(), 2.0)
                    );


                    model.getTriPrismLeftTopPoint().setLocation(
                            (model.getStartXPoint() - frontLineLenght * 0.223),
                            (model.getStartYPoint() - frontLineLenght * 0.243));
                    model.getTriPrismRightTopPoint().setLocation(
                            (model.getStartXPoint() + frontLineLenght * 0.624),
                            (model.getStartYPoint() - frontLineLenght * 0.243));
                    model.getTriPrismLeftBottomPoint().setLocation(
                            (model.getStartXPoint() - frontLineLenght * 0.223),
                            (model.getFinalYPoint() - frontLineLenght * 0.243));
                    model.getTriPrismRightBottomPoint().setLocation(
                            (model.getStartXPoint() + frontLineLenght * 0.624),
                            (model.getFinalYPoint() - frontLineLenght * 0.243));

                    // main vertical line
                    g2.drawLine(
                            model.getFinalXPoint(),
                            model.getStartYPoint(),
                            model.getFinalXPoint(),
                            model.getFinalYPoint());

                    // main horizontal line
                    g2.drawLine(
                            (int) model.getTriPrismLeftTopPoint().getX(),
                            (int) model.getTriPrismLeftTopPoint().getY(),
                            (int) model.getTriPrismRightTopPoint().getX(),
                            (int) model.getTriPrismRightTopPoint().getY());



                    // top left horizontal line
                    g2.drawLine(
                            (int) model.getTriPrismLeftTopPoint().getX(),
                            (int) model.getTriPrismLeftTopPoint().getY(),
                            model.getFinalXPoint(),
                            model.getStartYPoint());

                    // top right horizontal line
                    g2.drawLine(
                            (int) model.getTriPrismRightTopPoint().getX(),
                            (int) model.getTriPrismRightTopPoint().getY(),
                            model.getFinalXPoint(),
                            model.getStartYPoint());


                    if (model.getFinalXPoint() > (int) model.getTriPrismRightBottomPoint().getX()) {
                        g2.setStroke(new BasicStroke(
                                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                new float[]{7.0f},
                                0.0f));
                    }
                    // right vertical line
                    g2.drawLine(
                            (int) model.getTriPrismRightTopPoint().getX(),
                            (int) model.getTriPrismRightTopPoint().getY(),
                            (int) model.getTriPrismRightBottomPoint().getX(),
                            (int) model.getTriPrismRightBottomPoint().getY());



                    // right horizontal line
                    g2.drawLine(
                            model.getFinalXPoint(),
                            model.getFinalYPoint(),
                            (int) model.getTriPrismRightBottomPoint().getX(),
                            (int) model.getTriPrismRightBottomPoint().getY());

                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));


                    if (model.getFinalXPoint() < (int) model.getTriPrismLeftBottomPoint().getX()) {
                        g2.setStroke(new BasicStroke(
                                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                new float[]{7.0f},
                                0.0f));
                    }

                    // left horizontal line
                    g2.drawLine(
                            model.getFinalXPoint(),
                            model.getFinalYPoint(),
                            (int) model.getTriPrismLeftBottomPoint().getX(),
                            (int) model.getTriPrismLeftBottomPoint().getY());

                    // left vertical line
                    g2.drawLine(
                            (int) model.getTriPrismLeftTopPoint().getX(),
                            (int) model.getTriPrismLeftTopPoint().getY(),
                            (int) model.getTriPrismLeftBottomPoint().getX(),
                            (int) model.getTriPrismLeftBottomPoint().getY());

                    g2.setStroke(new BasicStroke(
                            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f,
                            new float[]{7.0f},
                            0.0f));

                    // back horizontal line
                    g2.drawLine(
                            (int) model.getTriPrismLeftBottomPoint().getX(),
                            (int) model.getTriPrismLeftBottomPoint().getY(),
                            (int) model.getTriPrismRightBottomPoint().getX(),
                            (int) model.getTriPrismRightBottomPoint().getY());
                    view.saveCurrentImage();

                    break;
                case FILL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FILL_BASIC_STROKE))));
                    g2.setColor(view.getMainColor());
                    g2.fillRect(0, 0, mainPanel.getSize().width, mainPanel.getSize().height);

                    view.saveCurrentImage();
                    break;
            }

            model.setFinalXPoint(0);
            model.setFinalYPoint(0);

            model.saveAction(view.getMainImage());
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
//            if (model.getDrawMode().equals(DrawMode.TEXT)){
//
//                Graphics g = view.getMainImage().getGraphics();
//                Graphics2D g2 = (Graphics2D)g;
//                g2.setColor(view.getMainColor());
//                g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.TEXT_BASIC_STROKE))));
//
//                String str = new  String("");
//                str += e.getKeyChar();
//                g2.setFont(new  Font(Config.getProperty(Config.TEXT_BASIC_FONT),
//                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_STYLE)),
//                        Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_FONT_SIZE))));
//                g2.drawString(str, model.getFinalXPoint(), model.getFinalYPoint());
//
//                model.setFinalXPoint(model.getFinalXPoint() + Integer.valueOf(Config.getProperty(Config.TEXT_BASIC_OFFSET)));
//                mainPanel.requestFocus();
//
//                mainPanel.repaint();
//            }
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
