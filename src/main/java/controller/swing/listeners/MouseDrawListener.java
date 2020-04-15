package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

import static config.Config.*;
import static model.Model.*;
import static model.Model.PARALLELOGRAM_FACTOR;
import static model.Model.PYRAMID_LEFT_XFACTOR;
import static model.Model.PYRAMID_LEFT_YFACTOR;
import static model.Model.PYRAMID_RIGHT_XFACTOR;
import static model.Model.PYRAMID_RIGHT_YFACTOR;

public class MouseDrawListener {
    private static final BasicStroke DOTTED_LINE = new BasicStroke(
            Float.valueOf(Config.getProperty(Config.BASIC_STROKE_WIDTH)),
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER,
            10.0f,
            new float[]{
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_LENGHT)),
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_OFFSET_LENGHT))
            },
            0.0f);
    private static final BasicStroke DEFAULT_LINE = new  BasicStroke(
            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
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
    private int drawLine;
    private int xMin;
    private int xMax;
    private ArrayList<Point> customPoints;
    private boolean pointIsNew;

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
            view.loadSavedImage();
            startDrawing();

            switch (model.getDrawMode()) {
                case PENCIL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(PENCIL_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                    saveCoordsAndView(mouseEvent);
                    break;
                case MARKER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(MARKER_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                    saveCoordsAndView(mouseEvent);
                    break;
                case BRUSH:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(BRUSH_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                    saveCoordsAndView(mouseEvent);
                    break;
                case ERASER:
                    g2.setStroke(new  BasicStroke(model.getEraserStroke()));
                    g2.setColor(Color.WHITE);
                    g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(ERASER_XPOINT_OFFSET)),
                            mouseEvent.getY() + Integer.parseInt(Config.getProperty(ERASER_YPOINT_OFFSET)),
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
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawLine(startX, startY, finalX, finalY);
                    break;
                case DOTTEDLINE:
                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(startX, startY, finalX, finalY);
                    break;
//                case ARROW:
//                    g2.setStroke(DEFAULT_LINE);
//                    g2.drawLine(startX, startY, finalX, finalY);
//                    break;
                case CIRCLE:
                    g2.setStroke(DEFAULT_LINE);
                    saveCoordsToModel();
                    Point center = model.getCircleCenter();
                    int radius = model.getCircleRadius();

                    g2.drawOval(center.x - radius, center.y - radius,
                            Math.abs(center.x - finalX), Math.abs(center.x - finalX));
                    g2.drawLine(center.x, center.y, center.x, center.y);
                    break;
                case ELLIPSE:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawOval(Math.min(finalX, startX), Math.min(finalY, startY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    break;
                case RECT:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawRect(Math.min(finalX, startX), Math.min(finalY, startY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    Point rectCenter = new Point(
                            startX + Math.abs(startX - finalX) / 2,
                            startY + Math.abs(startY - finalY) / 2);
                    setVerticalIndicator(rectCenter,
                            Math.abs(Math.abs(startX - finalX) - Math.abs(startY - finalY)));
                    setHorizontalIndicator(rectCenter,
                            Math.abs(Math.abs(startX - finalX) - Math.abs(startY - finalY)));
                    break;
                case PARALLELOGRAM:
                    g2.setStroke(DEFAULT_LINE);
                    saveCoordsToModel();

                    model.getParallelogramRightTop().setLocation(
                            finalX + drawLine * PARALLELOGRAM_FACTOR,
                            startY);
                    model.getParallelogramLeftBottom().setLocation(
                            startX - drawLine * PARALLELOGRAM_FACTOR,
                            finalY);
                    Point pmLeftBottom = model.getParallelogramLeftBottom();
                    Point pmRightTop = model.getParallelogramRightTop();

                    g2.drawLine(startX, startY, pmRightTop.x, pmRightTop.y);
                    g2.drawLine(startX, startY, pmLeftBottom.x, pmLeftBottom.y);
                    g2.drawLine(pmLeftBottom.x, pmLeftBottom.y, finalX, finalY);
                    g2.drawLine(pmRightTop.x, pmRightTop.y, finalX, finalY);
                    break;
                case PYRAMID:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    model.getTriPyramidFront().setLocation(
                            startX + drawLine * PYRAMID_MAIN_FACTOR, finalY);
                    model.getTriPyramidLeft().setLocation(
                            finalX + drawLine * PYRAMID_LEFT_XFACTOR,
                            finalY + drawLine * PYRAMID_LEFT_YFACTOR);
                    model.getTriPyramidRight().setLocation(
                            finalX + drawLine * PYRAMID_RIGHT_XFACTOR,
                            finalY + drawLine * PYRAMID_RIGHT_YFACTOR);
                    Point frontPoint = model.getTriPyramidFront();
                    Point leftPoint = model.getTriPyramidLeft();
                    Point rightPoint = model.getTriPyramidRight();

                    g2.drawLine(startX, startY, frontPoint.x, frontPoint.y);
                    g2.drawLine(startX, startY, leftPoint.x, leftPoint.y);
                    g2.drawLine(startX, startY, rightPoint.x, rightPoint.y);
                    break;
                case PYRAMID_TETRA:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    model.getTetraPyramidBackLeft().setLocation(
                            finalX + drawLine * PYRAMID_BACK_LEFT_XFACTOR,
                            finalY + drawLine * PYRAMID_BACK_LEFT_YFACTOR);
                    model.getTetraPyramidBackRight().setLocation(
                            finalX + drawLine * PYRAMID_BACK_RIGHT_XFACTOR,
                            finalY + drawLine * PYRAMID_BACK_RIGHT_YFACTOR);
                    model.getTetraPyramidFrontLeft().setLocation(
                            finalX + drawLine * PYRAMID_FRONT_LEFT_XFACTOR,
                            finalY);
                    model.getTetraPyramidFrontRight().setLocation(
                            finalX + drawLine * PYRAMID_FRONT_RIGHT_XFACTOR,
                            finalY);
                    Point backRight = model.getTetraPyramidBackRight();
                    Point frontLeft = model.getTetraPyramidFrontLeft();
                    Point frontRight= model.getTetraPyramidFrontRight();
                    setPyramidCenter();

                    g2.drawLine(startX, startY, frontRight.x, finalY);
                    g2.drawLine(startX, startY, frontLeft.x, frontLeft.y);
                    g2.drawLine(startX, startY, backRight.x, backRight.y);
                    setVerticalIndicator(model.getTetraPyramidBottomCenter(),
                            Math.abs(startX - model.getTetraPyramidBottomCenter().x));
                    break;
                case PRISM:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    model.getTriPrismLeftTop().setLocation(
                            (startX + drawLine * PRISM_LEFT_TOP_XFACTOR),
                            (startY + drawLine * PRISM_LEFT_TOP_YFACTOR));
                    model.getTriPrismRightTop().setLocation(
                            (startX + drawLine * PRISM_RIGHT_TOP_XFACTOR),
                            (startY + drawLine * PRISM_RIGHT_TOP_YFACTOR));
                    model.getTriPrismLeftBottom().setLocation(
                            (startX + drawLine * PRISM_LEFT_BOTTOM_XFACTOR),
                            (finalY + drawLine * PRISM_LEFT_BOTTOM_YFACTOR));
                    model.getTriPrismRightBottom().setLocation(
                            (startX + drawLine * PRISM_RIGHT_BOTTOM_XFACTOR),
                            (finalY + drawLine * PRISM_RIGHT_BOTTOM_YFACTOR));
                    Point leftTop = model.getTriPrismLeftTop();
                    Point rightTop = model.getTriPrismRightTop();
                    Point leftBottom = model.getTriPrismLeftBottom();
                    Point rightBottom = model.getTriPrismRightBottom();

                    g2.drawLine(finalX, startY, finalX, finalY);
                    g2.drawLine(leftTop.x, leftTop.y, rightTop.x, rightTop.y);
                    g2.drawLine(leftTop.x, leftTop.y, leftBottom.x, leftBottom.y);
                    g2.drawLine(rightTop.x, rightTop.y, rightBottom.x, rightBottom.y);
                    g2.drawLine(finalX, finalY, leftBottom.x, leftBottom.y);
                    g2.drawLine(finalX, finalY, rightBottom.x, rightBottom.y);
                    break;
                case PARALLELEPIPED:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    model.getParallelepipedFrontLeftTop().setLocation(
                            startX + drawLine * PARALLELEPIPED_LEFT_TOP_XFACTOR,
                            finalY + drawLine * PARALLELEPIPED_LEFT_TOP_YFACTOR);
                    model.getParallelepipedBackRightTop().setLocation(
                            finalX + drawLine * PARALLELEPIPED_RIGHT_TOP_XFACTOR,
                            startY);
                    model.getParallelepipedFrontLeftBottom().setLocation(
                            startX + drawLine * PARALLELEPIPED_LEFT_BOTTOM_XFACTOR,
                            finalY);
                    model.getParallelepipedBackRightBottom().setLocation(
                            finalX + drawLine * PARALLELEPIPED_RIGHT_BOTTOM_XFACTOR,
                            finalY + drawLine * PARALLELEPIPED_RIGHT_BOTTOM_YFACTOR);
                    model.getParallelepipedFrontRightTop().setLocation(
                            finalX,
                            finalY + drawLine * PARALLELEPIPED_FRONT_TOP_YFACTOR);
                    model.getParallelepipedBackLeftBottom().setLocation(
                            startX,
                            finalY + drawLine * PARALLELEPIPED_BACK_BOTTOM_YFACTOR);
                    Point frontLeftTop = model.getParallelepipedFrontLeftTop();
                    Point backRightTop = model.getParallelepipedBackRightTop();
                    Point frontLeftBottom = model.getParallelepipedFrontLeftBottom();
                    Point backRightBottom = model.getParallelepipedBackRightBottom();
                    Point frontRightTop = model.getParallelepipedFrontRightTop();
                    Point backLeftBottom = model.getParallelepipedBackLeftBottom();

                    g2.drawLine(startX, startY, backRightTop.x, backRightTop.y);
                    g2.drawLine(startX, startY, frontLeftTop.x, frontLeftTop.y);
                    g2.drawLine(backRightTop.x, backRightTop.y, backRightBottom.x, backRightBottom.y);
                    g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontLeftBottom.x, frontLeftBottom.y);
                    break;
                }
                finalX = mouseEvent.getX();
                finalY = mouseEvent.getY();
            mainPanel.repaint();
        }

        public void mouseMoved(MouseEvent e) { }
    }

    private class MouseAdapter implements MouseListener {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            listenMouseRightButton(mouseEvent);

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

            saveCoordsToModel();
            model.resetAllPoints();

            if (model.getDrawMode() == DrawMode.CIRCLE) {
                model.setCircleCenter(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
//
//            if (model.getDrawMode() == DrawMode.POLYGON) {
//
//            }
        }


        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            view.loadSavedImage();
            startDrawing();

            switch(model.getDrawMode()) {
                case PENCIL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(PENCIL_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX + 1, finalY + 1);
                    break;
                case MARKER:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(MARKER_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX + 1, finalY + 1);
                    break;
                case BRUSH:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(BRUSH_BASIC_STROKE))));
                    g2.drawLine(finalX, finalY, finalX + 1, finalY + 1);
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
                case LINE:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
                    break;
                case DOTTEDLINE:
                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(startX, startY, mouseEvent.getX(), mouseEvent.getY());
                    break;
//                case ARROW:
//                    g2.setStroke(DEFAULT_LINE);
//                    g2.drawLine(startX, startY, finalX, finalY);

//                    int nPoints = 3;
//                    int[] xPeakPoints = new int[nPoints + 1];
//                    int[] yPeakPoints = new int[nPoints + 1];
//                    for (int i = 0; i < nPoints; i++) {
//                        double angle = 2 * Math.PI * i / nPoints + 90;
//                        xPeakPoints[i] = (int)(finalX + 5 * Math.sin(angle));
//                        yPeakPoints[i] = (int)(finalY - 5 * Math.cos(angle));
//
//                    }
//                    Polygon arrowPeak = new Polygon(xPeakPoints, yPeakPoints, nPoints);
//                    g2.drawPolygon(arrowPeak);
//                    g2.fillPolygon(arrowPeak);

//                    break;
                case CIRCLE:
                    g2.setStroke(DEFAULT_LINE);
                    Point center = model.getCircleCenter();
                    int radius = model.getCircleRadius();

                    g2.drawOval(center.x - radius, center.y - radius,
                            Math.abs(center.x - finalX), Math.abs(center.x - finalX));
                    g2.drawLine(center.x, center.y, center.x, center.y);

                    break;
                case ELLIPSE:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawOval(Math.min(finalX, startX), Math.min(finalY, startY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    g2.drawLine(Math.min(finalX, startX) + Math.abs(startX - finalX) / 2,
                            Math.min(finalY, startY) + Math.abs(startY - finalY) / 2,
                            Math.min(finalX, startX) + Math.abs(startX - finalX) / 2,
                            Math.min(finalY, startY) + Math.abs(startY - finalY) / 2);
                    break;
                case RECT:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawRect(Math.min(finalX, startX), Math.min(finalY, startY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    break;
                case PARALLELOGRAM:
                    g2.setStroke(DEFAULT_LINE);
                    Point pmLeftBottom = model.getParallelogramLeftBottom();
                    Point pmRightTop = model.getParallelogramRightTop();

                    g2.drawLine(startX, startY, pmRightTop.x, pmRightTop.y);
                    g2.drawLine(startX, startY, pmLeftBottom.x, pmLeftBottom.y);
                    g2.drawLine(pmLeftBottom.x, pmLeftBottom.y, finalX, finalY);
                    g2.drawLine(pmRightTop.x, pmRightTop.y, finalX, finalY);
                    break;
                case POLYGON:
                    g2.setStroke(DEFAULT_LINE);

                    pointIsNew = true;
                    customPoints = new ArrayList<>();
                    for (int i = -10; i < 10; i++) {
                        for (int j = -10; j < 10; j++) {
                            customPoints.add(new Point(finalX + i, finalY + j));
                        }
                    }
                    for (Point point : customPoints) {
                        if (model.getPointList().contains(point)) {
                            model.fillPolygon();
                            g2.drawPolygon(model.getPolygon());

                            model.resetPolygon();
                            pointIsNew = false;
                            break;
                        }
                    }
                    if (pointIsNew) {
                        g2.drawLine(finalX, finalY, finalX + 1, finalY + 1);
                        model.getPointList().add(new Point(finalX + 1, finalY + 1));
                    }

                    break;
                case PYRAMID:
                    g2.setStroke(DEFAULT_LINE);
                    Point frontPoint = model.getTriPyramidFront();
                    Point leftPoint = model.getTriPyramidLeft();
                    Point rightPoint = model.getTriPyramidRight();

                    g2.drawLine(startX, startY, frontPoint.x, frontPoint.y);
                    g2.drawLine(startX, startY, leftPoint.x, leftPoint.y);
                    g2.drawLine(startX, startY, rightPoint.x, rightPoint.y);
                    g2.drawLine(leftPoint.x, leftPoint.y, frontPoint.x, frontPoint.y);
                    g2.drawLine(rightPoint.x, rightPoint.y, frontPoint.x, frontPoint.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y);
                    break;
                case PYRAMID_TETRA:
                    g2.setStroke(DEFAULT_LINE);
                    Point backLeft = model.getTetraPyramidBackLeft();
                    Point backRight = model.getTetraPyramidBackRight();
                    Point frontLeft = model.getTetraPyramidFrontLeft();
                    Point frontRight= model.getTetraPyramidFrontRight();

                    g2.drawLine(startX, startY, frontRight.x, frontRight.y);
                    g2.drawLine(startX, startY, frontLeft.x, frontLeft.y);
                    g2.drawLine(startX, startY, backRight.x, backRight.y);
                    g2.drawLine(frontLeft.x, frontLeft.y, frontRight.x, frontRight.y);
                    g2.drawLine(backRight.x, backRight.y, frontRight.x, frontRight.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(startX, startY, backLeft.x, backLeft.y);
                    g2.drawLine(frontLeft.x, frontLeft.y, backLeft.x, backLeft.y);
                    g2.drawLine(backRight.x, backRight.y, backLeft.x, backLeft.y);
                    break;
//                case PYRAMID_CUSTOM:
//                    g2.setStroke(DEFAULT_LINE);
//
//                    pointIsNew = true;
//                    customPoints = new ArrayList<>();
//                    Point customTopPoint = new Point();
//                    Point minXPoint = new Point();
//                    Point maxXPoint = new Point();
//                    for (int i = -10; i < 10; i++) {
//                        for (int j = -10; j < 10; j++) {
//                            customPoints.add(new Point(finalX + i, finalY + j));
//                        }
//                    }
//                    for (Point point : customPoints) {
//                        if (model.getPointList().contains(point)) {
//                            model.fillPolygon();
//                            for (Point bottomPoint : model.getPointList()) {
//                                minXPoint = bottomPoint;
//                                if (bottomPoint.x > maxXPoint.x) {
//                                    maxXPoint = bottomPoint;
//                                }
//                                if (bottomPoint.x < minXPoint.x) {
//                                    minXPoint = bottomPoint;
//                                }
//                            }
//                            customTopPoint.x = minXPoint.x + (maxXPoint.x - minXPoint.x) / 2;
//                            customTopPoint.y = (int) (minXPoint.y - (maxXPoint.x - minXPoint.x) * 1.5);
//                            g2.drawPolygon(model.getPolygon());
//                            for (Point bottomPoint : model.getPointList()) {
//                                if (bottomPoint.x < maxXPoint.x && bottomPoint.x > minXPoint.x && bottomPoint.y < minXPoint.y) {
//                                    g2.setStroke(DOTTED_LINE);
//                                }
//                                g2.drawLine(customTopPoint.x, customTopPoint.y, bottomPoint.x, bottomPoint.y);
//                                g2.setStroke(DEFAULT_LINE);
//                            }
//                            model.resetPolygon();
//                            pointIsNew = false;
//                            break;
//                        }
//                    }
//                    if (pointIsNew) {
//                        g2.drawLine(finalX, finalY, finalX + 1, finalY + 1);
//                        model.getPointList().add(new Point(finalX + 1, finalY + 1));
//                    }
//
//                    break;

                case PRISM:
                    g2.setStroke(DEFAULT_LINE);
                    Point leftTop = model.getTriPrismLeftTop();
                    Point rightTop = model.getTriPrismRightTop();
                    Point leftBottom = model.getTriPrismLeftBottom();
                    Point rightBottom = model.getTriPrismRightBottom();
                    xMax = rightBottom.x;
                    xMin = leftBottom.x;

                    g2.setStroke(DEFAULT_LINE);
                    g2.drawLine(leftTop.x, leftTop.y, finalX, startY);
                    g2.drawLine(rightTop.x, rightTop.y, finalX, startY);
                    g2.drawLine(finalX, startY, finalX, finalY);

                    if (isCentral() && isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(leftTop.x, leftTop.y, rightTop.x, rightTop.y);
                    g2.setStroke(DEFAULT_LINE);

                    if (isLeftDrawing() && !isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(leftTop.x, leftTop.y, leftBottom.x, leftBottom.y);

                    if (isRightDrawing() && isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(finalX, finalY, leftBottom.x, leftBottom.y);
                    g2.setStroke(DEFAULT_LINE);

                    if (isRightDrawing() && !isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(rightTop.x, rightTop.y, rightBottom.x, rightBottom.y);

                    if (isLeftDrawing() && isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(finalX, finalY, rightBottom.x, rightBottom.y);
                    g2.setStroke(DEFAULT_LINE);

                    if (!isUpDrawing()) { g2.setStroke(DOTTED_LINE); }
                    g2.drawLine(leftBottom.x, leftBottom.y, rightBottom.x, rightBottom.y);
                    break;
                case PARALLELEPIPED:
                    g2.setStroke(DEFAULT_LINE);
                    Point frontLeftTop = model.getParallelepipedFrontLeftTop();
                    Point backRightTop = model.getParallelepipedBackRightTop();
                    Point frontLeftBottom = model.getParallelepipedFrontLeftBottom();
                    Point backRightBottom = model.getParallelepipedBackRightBottom();
                    Point frontRightTop = model.getParallelepipedFrontRightTop();
                    Point backLeftBottom = model.getParallelepipedBackLeftBottom();

                    g2.drawLine(startX, startY, backRightTop.x, backRightTop.y);
                    g2.drawLine(startX, startY, frontLeftTop.x, frontLeftTop.y);
                    g2.drawLine(backRightTop.x, backRightTop.y, backRightBottom.x, backRightBottom.y);
                    g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontLeftBottom.x, frontLeftBottom.y);
                    g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontRightTop.x, frontRightTop.y);
                    g2.drawLine(backRightTop.x, backRightTop.y, frontRightTop.x, frontRightTop.y);
                    g2.drawLine(frontLeftBottom.x, frontLeftBottom.y, finalX, finalY);
                    g2.drawLine(backRightBottom.x, backRightBottom.y, finalX, finalY);
                    g2.drawLine(frontRightTop.x, frontRightTop.y, finalX, finalY);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(frontLeftBottom.x, frontLeftBottom.y, backLeftBottom.x, backLeftBottom.y);
                    g2.drawLine(backRightBottom.x, backRightBottom.y, backLeftBottom.x, backLeftBottom.y);
                    g2.drawLine(startX, startY, backLeftBottom.x, backLeftBottom.y);
                    break;
                case FILL:
                    g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(Config.FILL_BASIC_STROKE))));
                    g2.setColor(view.getMainColor());
                    g2.fillRect(0, 0, mainPanel.getSize().width, mainPanel.getSize().height);
                    break;
//                case TEXT:
//                    mainPanel.requestFocus();
//                    break;
            }
            finalX = 0;
            finalY = 0;
            view.saveCurrentImage();
            model.saveAction(view.getMainImage());
            mainPanel.repaint();
        }
        public void mouseClicked(MouseEvent mouseEvent) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
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

    private  void saveCoordsToModel() {
        model.setStartXPoint(startX);
        model.setStartYPoint(startY);
        model.setFinalXPoint(finalX);
        model.setFinalYPoint(finalY);

        drawLine = (int) model.getDrawLine();
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

    private void setPyramidCenter() {
        model.getTetraPyramidBottomCenter().setLocation(
                model.getTetraPyramidBackLeft().x +
                        (Math.abs(model.getTetraPyramidBackLeft().x - model.getTetraPyramidFrontRight().x)) / 2,
                model.getTetraPyramidBackLeft().y +
                        (Math.abs(model.getTetraPyramidBackLeft().y - model.getTetraPyramidFrontRight().y)) / 2
        );
    }

    private void setVerticalIndicator(Point controlPoint, int controlValue) {
        if (controlValue < Model.INDICATOR_WIDTH) {
            g2.setColor(Color.red);
            g2.setStroke(DOTTED_LINE);
            g2.drawLine(controlPoint.x, controlPoint.y - Model.INDICATOR_TOP_OFFSET, controlPoint.x,
                    controlPoint.y + Model.INDICATOR_BOTTOM_OFFSET);
        }
    }

    private void setHorizontalIndicator(Point controlPoint, int controlValue) {
        if (controlValue < Model.INDICATOR_WIDTH) {
            g2.setColor(Color.red);
            g2.setStroke(DOTTED_LINE);
            g2.drawLine(controlPoint.x - Model.INDICATOR_TOP_OFFSET, controlPoint.y,
                    controlPoint.x + Model.INDICATOR_BOTTOM_OFFSET, controlPoint.y);
        }
    }

    private boolean isUpDrawing() {
        return finalY < startY;
    }

    private boolean isLeftDrawing() {
        return finalX < xMin;
    }

    private boolean isRightDrawing() {
        return finalX > xMax;
    }

    private boolean isCentral() {
        return !isLeftDrawing() && !isRightDrawing();
    }


    public MouseMotionAdapter getMOUSE_MOTION_ADAPTER() {
        return MOUSE_MOTION_ADAPTER;
    }

    public MouseAdapter getMOUSE_ADAPTER() {
        return MOUSE_ADAPTER;
    }
}
