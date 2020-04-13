package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MouseDrawListener {
    private static final BasicStroke DOTTED_LINE = new BasicStroke(
            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)),
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,
            10.0f,
            new float[]{
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_LENGHT)),
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_OFFSET_LENGHT))
            },
            0.0f);
    private final MouseMotionAdapter MOUSE_MOTION_ADAPTER;
    private final MouseAdapter MOUSE_ADAPTER;


    private SwingViewImpl view;
    private Model model;
    private JPanel mainPanel;
    private Graphics2D g2;
    private int startX;
    private int startY;
    private int finalX;
    private int finalY;

    public MouseDrawListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();
        this.startX = model.getStartXPoint();
        this.startY = model.getStartYPoint();
        this.finalX = model.getFinalXPoint();
        this.finalY = model.getFinalYPoint();
        MOUSE_MOTION_ADAPTER = new MouseMotionAdapter();
        MOUSE_ADAPTER = new MouseAdapter();
    }

    private class MouseMotionAdapter implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            startDrawing();
            view.loadSavedImage();
            
                switch (model.getDrawMode()) {
                    case PENCIL:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        saveCoordsAndView(mouseEvent);
                        break;
                    case MARKER:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        saveCoordsAndView(mouseEvent);
                        break;
                    case BRUSH:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        saveCoordsAndView(mouseEvent);
                        break;
                    case ERASER:
                        g2.setStroke(new  BasicStroke(model.getEraserStroke()));
                        g2.setColor(Color.WHITE);
                        g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(Config.ERASER_XPOINT_OFFSET)),
                                mouseEvent.getY() + Integer.parseInt(Config.getProperty(Config.ERASER_YPOINT_OFFSET)),
                                model.getEraserStroke(),
                                model.getEraserStroke());
                        saveCoordsAndView(mouseEvent);
                        break;
                    case RAG:
                        g2.setStroke(new  BasicStroke(model.getRagStroke()));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        saveCoordsAndView(mouseEvent);
                        break;
                    case LINE:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        g2.drawLine(startX, startY, finalX, finalY);
                        break;
                    case DOTTEDLINE:
                        g2.setStroke(DOTTED_LINE);
                        g2.drawLine(startX, startY, finalX, finalY);
                        break;
                    case CIRCLE:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        drawCircle(g2);
                        break;
                    case RECT:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                        drawRectangle(g2);
                        break;
                    case PYRAMID:
                        g2.setStroke(DOTTED_LINE);

                        drawTriPyramid(g2);
                        break;
                    case PRISM:
                        g2.setStroke(DOTTED_LINE);

                        drawTriPrism(g2);
                        break;
                }
                finalX = mouseEvent.getX();
                finalY = mouseEvent.getY();
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

            startDrawing();

//            view.loadSavedImage();

            switch (model.getDrawMode()) {
                case PENCIL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX+1, finalY+1);
                    break;
                case MARKER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.MARKER_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX+1, finalY+1);
                    break;
                case BRUSH:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.BRUSH_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX+1, finalY+1);
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
                case TEXT:
//                    mainPanel.requestFocus();
                    break;
            }
            finalX = mouseEvent.getX();
            finalY = mouseEvent.getY();

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

            startX = mouseEvent.getX();
            startY = mouseEvent.getY();
            finalX = mouseEvent.getX();
            finalY = mouseEvent.getY();

            if (model.getDrawMode() == DrawMode.CIRCLE) {
                model.setCircleCenter(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            view.loadSavedImage();
            startDrawing();

            switch(model.getDrawMode()) {
                case LINE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    g2.drawLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());

                    view.saveCurrentImage();
                    break;
                case DOTTEDLINE:
                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());

                    view.saveCurrentImage();
                    break;
                case CIRCLE:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    drawCircle(g2);
                    view.saveCurrentImage();
                    break;
                case RECT:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));
                    drawRectangle(g2);
                    view.saveCurrentImage();
                    break;
                case PYRAMID:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));

                    double mainLineLenght = Math.sqrt(
                            Math.pow(startX - finalX, 2.0) +
                                    Math.pow(startY - finalY, 2.0)
                    );

                    model.getTriPyramidLeftPoint().setLocation(
                            (finalX - mainLineLenght * 0.223),
                            (finalY - mainLineLenght * 0.243));
                    model.getTriPyramidRightPoint().setLocation(
                            (finalX + mainLineLenght * 0.624),
                            (finalY - mainLineLenght * 0.243));

                    g2.drawLine(
                            startX,
                            startY,
                            (int) (startX - mainLineLenght * 0.2),
                            finalY);

                    g2.drawLine(
                            startX,
                            startY,
                            (int) (finalX - mainLineLenght * 0.223),
                            (int) (finalY - mainLineLenght * 0.243));

                    g2.drawLine(
                            startX,
                            startY,
                            (int) (finalX + mainLineLenght * 0.624),
                            (int) (finalY - mainLineLenght * 0.243));

                    g2.drawLine(model.getTriPyramidLeftPoint().x, model.getTriPyramidLeftPoint().y, (int) (startX - mainLineLenght * 0.2), finalY);
                    g2.drawLine(model.getTriPyramidRightPoint().x, model.getTriPyramidRightPoint().y, (int) (startX - mainLineLenght * 0.2), finalY);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(
                            model.getTriPyramidLeftPoint().x, model.getTriPyramidLeftPoint().y,
                            model.getTriPyramidRightPoint().x, model.getTriPyramidRightPoint().y);

                    view.saveCurrentImage();
                    break;
                case PRISM:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));

                    double frontLineLenght = Math.sqrt(
                            Math.pow(startX - finalX, 2.0) +
                                    Math.pow(startY - finalY, 2.0)
                    );

                    model.getTriPrismLeftTopPoint().setLocation(
                            (startX - frontLineLenght * 0.223),
                            (startY - frontLineLenght * 0.243));
                    model.getTriPrismRightTopPoint().setLocation(
                            (startX + frontLineLenght * 0.624),
                            (startY - frontLineLenght * 0.243));
                    model.getTriPrismLeftBottomPoint().setLocation(
                            (startX - frontLineLenght * 0.223),
                            (finalY - frontLineLenght * 0.243));
                    model.getTriPrismRightBottomPoint().setLocation(
                            (startX + frontLineLenght * 0.624),
                            (finalY - frontLineLenght * 0.243));

                    // main vertical line
                    g2.drawLine(
                            finalX,
                            startY,
                            finalX,
                            finalY);

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
                            finalX,
                            startY);

                    // top right horizontal line
                    g2.drawLine(
                            (int) model.getTriPrismRightTopPoint().getX(),
                            (int) model.getTriPrismRightTopPoint().getY(),
                            finalX,
                            startY);


                    if (finalX > (int) model.getTriPrismRightBottomPoint().getX()) {
                        g2.setStroke(DOTTED_LINE);
                    }
                    // right vertical line
                    g2.drawLine(
                            (int) model.getTriPrismRightTopPoint().getX(),
                            (int) model.getTriPrismRightTopPoint().getY(),
                            (int) model.getTriPrismRightBottomPoint().getX(),
                            (int) model.getTriPrismRightBottomPoint().getY());



                    // right horizontal line
                    g2.drawLine(
                            finalX,
                            finalY,
                            (int) model.getTriPrismRightBottomPoint().getX(),
                            (int) model.getTriPrismRightBottomPoint().getY());

                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE))));


                    if (finalX < (int) model.getTriPrismLeftBottomPoint().getX()) {
                        g2.setStroke(DOTTED_LINE);
                    }

                    // left horizontal line
                    g2.drawLine(
                            finalX,
                            finalY,
                            (int) model.getTriPrismLeftBottomPoint().getX(),
                            (int) model.getTriPrismLeftBottomPoint().getY());

                    // left vertical line
                    g2.drawLine(
                            (int) model.getTriPrismLeftTopPoint().getX(),
                            (int) model.getTriPrismLeftTopPoint().getY(),
                            (int) model.getTriPrismLeftBottomPoint().getX(),
                            (int) model.getTriPrismLeftBottomPoint().getY());

                    g2.setStroke(DOTTED_LINE);

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

            finalX = 0;
            finalY = 0;
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

    private void startDrawing() {
        Graphics g = view.getMainImage().getGraphics();
        g2 = (Graphics2D)g;
        g2.setColor(view.getMainColor());
    }

    private void saveCoordsAndView(MouseEvent mouseEvent) {
        finalX = mouseEvent.getX();
        finalY = mouseEvent.getY();
        view.saveCurrentImage();
    }

    private void drawCircle(Graphics2D g2) {
        int radius = Math.abs(startX - finalX) / 2;
        g2.drawOval(
                (int) (model.getCircleCenter().getX() - radius),
                (int) (model.getCircleCenter().getY() - radius),
                (int) Math.abs(model.getCircleCenter().getX() - finalX),
                (int) Math.abs(model.getCircleCenter().getX() - finalX)
        );
        g2.drawLine(
                (int) (model.getCircleCenter().getX()),
                (int) (model.getCircleCenter().getY()),
                (int) (model.getCircleCenter().getX()),
                (int) (model.getCircleCenter().getY())
        );
    }

    private void drawRectangle(Graphics2D g2) {
        g2.drawRect(
                Math.min(finalX, startX),
                Math.min(finalY, startY),
                Math.abs(startX - finalX),
                Math.abs(startY - finalY));
    }


    private void drawTriPyramid(Graphics2D g2) {
        double mainLineLenght = Math.sqrt(
                Math.pow(startX - finalX, 2.0) +
                        Math.pow(startY - finalY, 2.0)
        );


        g2.drawLine(
                startX,
                startY,
                (int) (startX - mainLineLenght * 0.2),
                finalY);

        g2.drawLine(
                startX,
                startY,
                (int) (finalX - mainLineLenght * 0.223),
                (int) (finalY - mainLineLenght * 0.243));

        g2.drawLine(
                startX,
                startY,
                (int) (finalX + mainLineLenght * 0.624),
                (int) (finalY - mainLineLenght * 0.243));

    }

    private void drawTriPrism(Graphics2D g2) {

        double frontLineLenght = Math.sqrt(
                Math.pow(startX - finalX, 2.0) +
                        Math.pow(startY - finalY, 2.0)
        );


        model.getTriPrismLeftTopPoint().setLocation(
                (startX - frontLineLenght * 0.223),
                (startY - frontLineLenght * 0.243));
        model.getTriPrismRightTopPoint().setLocation(
                (startX + frontLineLenght * 0.624),
                (startY - frontLineLenght * 0.243));
        model.getTriPrismLeftBottomPoint().setLocation(
                (startX - frontLineLenght * 0.223),
                (finalY - frontLineLenght * 0.243));
        model.getTriPrismRightBottomPoint().setLocation(
                (startX + frontLineLenght * 0.624),
                (finalY - frontLineLenght * 0.243));




        // main vertical line
        g2.drawLine(
                finalX,
                startY,
                finalX,
                finalY);

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
                finalX,
                finalY,
                (int) model.getTriPrismLeftBottomPoint().getX(),
                (int) model.getTriPrismLeftBottomPoint().getY());


        // right horizontal line
        g2.drawLine(
                finalX,
                finalY,
                (int) model.getTriPrismRightBottomPoint().getX(),
                (int) model.getTriPrismRightBottomPoint().getY());
    }

    public MouseMotionAdapter getMOUSE_MOTION_ADAPTER() {
        return MOUSE_MOTION_ADAPTER;
    }

    public MouseAdapter getMOUSE_ADAPTER() {
        return MOUSE_ADAPTER;
    }
}
