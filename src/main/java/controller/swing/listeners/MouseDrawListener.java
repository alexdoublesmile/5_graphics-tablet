package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private ArrayList<Point> areaPoints;
    private ArrayList<Point> controlPoints;
    private ArrayList<Point> prismTopPoints;
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
                    saveCoordsToModel();
                    Point ellipseCenter = model.getCircleCenter();
                    int bigRadius = model.getEllipseBigRadius();
                    int smallRadius = model.getEllipseSmallRadius();

                    g2.drawOval(ellipseCenter.x - bigRadius, ellipseCenter.y - smallRadius,
                            Math.abs(ellipseCenter.x - finalX), Math.abs(ellipseCenter.y - finalY));
                    g2.drawLine(ellipseCenter.x, ellipseCenter.y, ellipseCenter.x, ellipseCenter.y);

//                    g2.drawOval(Math.min(finalX, startX), Math.min(finalY, startY),
//                            Math.abs(startX - finalX), Math.abs(startY - finalY));
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
                case CONE:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    Point coneCenter = new Point(startX, startY);
                    int coneBigRadius = model.getEllipseBigRadius();
                    int coneSmallRadius = model.getEllipseSmallRadius();
                    g2.drawOval(coneCenter.x - coneBigRadius, coneCenter.y - coneSmallRadius,
                            Math.abs(coneCenter.x - finalX), Math.abs(coneCenter.y - finalY));
                    g2.drawLine(coneCenter.x, coneCenter.y - coneBigRadius * 2,
                            coneCenter.x - coneBigRadius, coneCenter.y);
                    g2.drawLine(coneCenter.x, coneCenter.y - coneBigRadius * 2,
                            coneCenter.x + coneBigRadius, coneCenter.y);
                    break;
                case CYLINDER:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();
                    Point cylinderCenter = new Point(startX, startY);
                    int cylinderBigRadius = model.getEllipseBigRadius();
                    int cylinderSmallRadius = model.getEllipseSmallRadius();
                    g2.drawOval(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius,
                            Math.abs(cylinderCenter.x - finalX), Math.abs(cylinderCenter.y - finalY));
                    g2.drawLine(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y,
                            cylinderCenter.x - cylinderBigRadius, cylinderCenter.y + cylinderBigRadius * 3);
                    g2.drawLine(cylinderCenter.x + cylinderBigRadius, cylinderCenter.y,
                            cylinderCenter.x + cylinderBigRadius, cylinderCenter.y + cylinderBigRadius * 3);

                    g2.drawOval(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius + cylinderBigRadius * 3,
                            Math.abs(cylinderCenter.x - finalX), Math.abs(cylinderCenter.y - finalY));

                    break;
                case SPHERE:
                    g2.setStroke(DOTTED_LINE);
                    saveCoordsToModel();

                    Point sphereCenter = model.getCircleCenter();
                    int sphereRadius = model.getCircleRadius();
                    int sphereSmallRadius = Math.abs(startY - finalY) / 2;

                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereRadius,
                            Math.abs(sphereCenter.x - finalX), Math.abs(sphereCenter.x - finalX));
                    g2.drawLine(sphereCenter.x, sphereCenter.y, sphereCenter.x, sphereCenter.y);
                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereSmallRadius,
                            Math.abs(sphereCenter.x - finalX), Math.abs(sphereCenter.y - finalY));
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

            if (model.getDrawMode() == DrawMode.CIRCLE
                    || model.getDrawMode() == DrawMode.ELLIPSE
                    || model.getDrawMode() == DrawMode.SPHERE) {
                model.setCircleCenter(new Point(mouseEvent.getX(), mouseEvent.getY()));
            }
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
//                    saveCoordsToModel();
//
//                    model.getArrowLeftPoint().setLocation(
//                            finalX - drawLine * 0.01,
//                            finalY + drawLine * 0.01);
//                    model.getArrowRightPoint().setLocation(
//                            finalX + drawLine * 0.01,
//                            finalY + drawLine * 0.01);
//
//                    Point aLeftPoint = model.getArrowLeftPoint();
//                    Point aRightPoint = model.getArrowRightPoint();
//                    Polygon arrowPeak = new Polygon(
//                            new int[]{finalX, aLeftPoint.x, aRightPoint.x},
//                            new int[]{finalY, aLeftPoint.y, aRightPoint.y},
//                            3);
//                    g2.drawPolygon(arrowPeak);
//                    g2.fillPolygon(arrowPeak);
                    /////////////////////////////
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

                    Point ellipseCenter = model.getCircleCenter();
                    int bigRadius = model.getEllipseBigRadius();
                    int smallRadius = model.getEllipseSmallRadius();

                    g2.drawOval(ellipseCenter.x - bigRadius, ellipseCenter.y - smallRadius,
                            Math.abs(ellipseCenter.x - finalX), Math.abs(ellipseCenter.y - finalY));
                    g2.drawLine(ellipseCenter.x, ellipseCenter.y, ellipseCenter.x, ellipseCenter.y);
//                    g2.drawOval(Math.min(finalX, startX), Math.min(finalY, startY),
//                            Math.abs(startX - finalX), Math.abs(startY - finalY));
//                    g2.drawLine(Math.min(finalX, startX) + Math.abs(startX - finalX) / 2,
//                            Math.min(finalY, startY) + Math.abs(startY - finalY) / 2,
//                            Math.min(finalX, startX) + Math.abs(startX - finalX) / 2,
//                            Math.min(finalY, startY) + Math.abs(startY - finalY) / 2);
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
                    areaPoints = new ArrayList<>();
                    for (int i = -10; i < 10; i++) {
                        for (int j = -10; j < 10; j++) {
                            areaPoints.add(new Point(finalX + i, finalY + j));
                        }
                    }
                    for (Point point : areaPoints) {
                        if (model.getPointList().contains(point)) {
                            model.fillPolygon();
                            g2.drawPolygon(model.getPolygon());

                            model.resetPolygon();
                            pointIsNew = false;
                            break;
                        }
                    }
                    if (pointIsNew) {
                        g2.drawLine(finalX, finalY, finalX, finalY);
                        model.getPointList().add(new Point(finalX, finalY));
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
                case PYRAMID_CUSTOM:
                    if (model.wasIterated()) {
                        model.resetPointList();
                    }
                    g2.setStroke(DEFAULT_LINE);

                    pointIsNew = true;
                    areaPoints = new ArrayList<>();
                    controlPoints = new ArrayList<>();
                    Point topPoint = new Point();
                    Point minXPoint = new Point(Integer.MAX_VALUE, 0);
                    Point maxXPoint = new Point(0,0);
                    for (int i = -5; i < 5; i++) {
                        for (int j = -5; j < 5; j++) {
                            areaPoints.add(new Point(mouseEvent.getX() + i, mouseEvent.getY() + j));
                        }
                    }
                    for (Point point : areaPoints) {
                        if (model.getPointList().contains(point)) {
                            for (Point bottomPoint : model.getPointList()) {
                                if (bottomPoint.x > maxXPoint.x) {
                                    maxXPoint = bottomPoint;
                                }
                                if (bottomPoint.x < minXPoint.x) {
                                    minXPoint = bottomPoint;
                                }
                            }
                            topPoint.x = minXPoint.x + (maxXPoint.x - minXPoint.x) / 2;
                            topPoint.y = (int) (minXPoint.y - (maxXPoint.x - minXPoint.x) * 1.5);
                            if (topPoint.y < 10) {
                                topPoint.y = 10;
                            }
                            Point lastPoint = null;
                            Point firstPoint = null;
                            for (Point bottomPoint : model.getPointList()) {
                                if (bottomPoint.x < maxXPoint.x
                                        && bottomPoint.x > minXPoint.x
                                        && bottomPoint.y < minXPoint.y - (minXPoint.y - maxXPoint.y) / 2) {
                                    g2.setStroke(DOTTED_LINE);
                                    controlPoints.add(bottomPoint);
                                }
                                g2.drawLine(topPoint.x, topPoint.y, bottomPoint.x, bottomPoint.y);
                                if (firstPoint == null) {
                                    firstPoint = bottomPoint;
                                }
                                if (lastPoint != null) {
                                    if (controlPoints.contains(bottomPoint)
                                    || controlPoints.contains(lastPoint)) {
                                        g2.setStroke(DOTTED_LINE);
                                    }
                                    g2.drawLine(bottomPoint.x, bottomPoint.y, lastPoint.x, lastPoint.y);
                                }
                                lastPoint = bottomPoint;
                                g2.setStroke(DEFAULT_LINE);
                            }
                            if (firstPoint.y < minXPoint.y
                                    || (lastPoint.y < minXPoint.y && lastPoint != maxXPoint)) {
                                g2.setStroke(DOTTED_LINE);
                            }
                            g2.drawLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);
                            pointIsNew = false;
                            model.resetPointList();
                            break;
                        }
                    }

                    if (pointIsNew) {
                        g2.drawLine(finalX, finalY, finalX, finalY);
                        model.getPointList().add(new Point(finalX, finalY));
                    }
                    break;
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
                case PRISM_CUSTOM:
                    if (model.wasIterated()) {
                        model.resetPointList();
                        model.resetPrismTopPointList();
                    }
                    g2.setStroke(DEFAULT_LINE);

                    pointIsNew = true;
                    areaPoints = new ArrayList<>();
                    controlPoints = new ArrayList<>();
                    Point prismLeftPoint = new Point(Integer.MAX_VALUE, 0);
                    Point prismRightPoint = new Point(0,0);
                    for (int i = -5; i < 5; i++) {
                        for (int j = -5; j < 5; j++) {
                            areaPoints.add(new Point(mouseEvent.getX() + i, mouseEvent.getY() + j));
                        }
                    }
                    for (Point point : areaPoints) {
                        if (model.getPointList().contains(point)) {
                            for (Point bottomPoint : model.getPointList()) {
                                if (bottomPoint.x > prismRightPoint.x) {
                                    prismRightPoint = bottomPoint;
                                }
                                if (bottomPoint.x < prismLeftPoint.x) {
                                    prismLeftPoint = bottomPoint;
                                }
                            }
                            Point lastPoint = null;
                            Point lastTopPoint = null;
                            Point firstPoint = null;
                            Point firstTopPoint = null;
                            int prismHeightFactor = (int) ((prismRightPoint.x - prismLeftPoint.x) * 0.7);
                            for (Point bottomPoint : model.getPointList()) {
//                                int xCoord = bottomPoint.x;
//                                int yCoord = (bottomPoint.y - prismHeightFactor) > 10 ?
//                                        bottomPoint.y - prismHeightFactor :
//                                        bottomPoint.y - prismHeightFactor / 2;
                                model.getPrismTopPointList().add(new Point(bottomPoint.x, bottomPoint.y - prismHeightFactor));
                            }
                            for (int i = 0; i < model.getPointList().size(); i++) {
                                if (model.getPointList().get(i).x < prismRightPoint.x
                                        && model.getPointList().get(i).x > prismLeftPoint.x
                                        && model.getPointList().get(i).y < prismLeftPoint.y - (prismLeftPoint.y - prismRightPoint.y) / 2) {
                                    g2.setStroke(DOTTED_LINE);
                                    controlPoints.add(model.getPointList().get(i));
                                }
                                g2.drawLine(model.getPrismTopPointList().get(i).x, model.getPrismTopPointList().get(i).y, model.getPointList().get(i).x, model.getPointList().get(i).y);
                                if (firstPoint == null) {
                                    firstPoint = model.getPointList().get(i);
                                }
                                if (lastPoint != null) {
                                    if (controlPoints.contains(model.getPointList().get(i))
                                            || controlPoints.contains(lastPoint)) {
                                        g2.setStroke(DOTTED_LINE);
                                    }
                                    g2.drawLine(model.getPointList().get(i).x, model.getPointList().get(i).y, lastPoint.x, lastPoint.y);
                                }
                                lastPoint = model.getPointList().get(i);
                                g2.setStroke(DEFAULT_LINE);

                                if (firstTopPoint == null) {
                                    firstTopPoint = model.getPrismTopPointList().get(i);
                                }
                                if (lastTopPoint != null) {
                                    g2.drawLine(model.getPrismTopPointList().get(i).x, model.getPrismTopPointList().get(i).y, lastTopPoint.x, lastTopPoint.y);
                                }
                                lastTopPoint = model.getPrismTopPointList().get(i);
                            }
                            g2.drawLine(firstTopPoint.x, firstTopPoint.y, lastTopPoint.x, lastTopPoint.y);
                            if (firstPoint.y < prismLeftPoint.y
                                    || (lastPoint.y < prismLeftPoint.y && lastPoint != prismRightPoint)) {
                                g2.setStroke(DOTTED_LINE);
                            }
                            g2.drawLine(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);
                            pointIsNew = false;
                            model.resetPointList();
                            model.resetPrismTopPointList();
                            break;
                        }
                    }
                    if (pointIsNew) {
                        g2.drawLine(finalX, finalY, finalX, finalY);
                        model.getPointList().add(new Point(finalX, finalY));
                    }
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
                case CONE:
                    g2.setStroke(DEFAULT_LINE);
                    Point coneCenter = new Point(startX, startY);
                    int coneBigRadius = model.getEllipseBigRadius();
                    int coneSmallRadius = model.getEllipseSmallRadius();
                    g2.drawArc(coneCenter.x - coneBigRadius, coneCenter.y - coneSmallRadius,
                            Math.abs(coneCenter.x - finalX), Math.abs(coneCenter.y - finalY),
                            0, -180);

                    g2.drawLine(coneCenter.x, coneCenter.y - coneBigRadius * 2,
                            coneCenter.x - coneBigRadius, coneCenter.y);
                    g2.drawLine(coneCenter.x, coneCenter.y - coneBigRadius * 2,
                            coneCenter.x + coneBigRadius, coneCenter.y);
                    g2.drawLine(coneCenter.x, coneCenter.y, coneCenter.x, coneCenter.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawArc(coneCenter.x - coneBigRadius, coneCenter.y - coneSmallRadius,
                            Math.abs(coneCenter.x - finalX), Math.abs(coneCenter.y - finalY),
                            0, 180);
                    break;
                case CYLINDER:
                    g2.setStroke(DEFAULT_LINE);
                    saveCoordsToModel();
                    Point cylinderCenter = new Point(startX, startY);
                    int cylinderBigRadius = model.getEllipseBigRadius();
                    int cylinderSmallRadius = model.getEllipseSmallRadius();

                    g2.drawOval(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius,
                            Math.abs(cylinderCenter.x - finalX), Math.abs(cylinderCenter.y - finalY));
                    g2.drawLine(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y,
                            cylinderCenter.x - cylinderBigRadius, cylinderCenter.y + cylinderBigRadius * 3);
                    g2.drawLine(cylinderCenter.x + cylinderBigRadius, cylinderCenter.y,
                            cylinderCenter.x + cylinderBigRadius, cylinderCenter.y + cylinderBigRadius * 3);

                    g2.drawArc(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius + cylinderBigRadius * 3,
                            Math.abs(cylinderCenter.x - finalX), Math.abs(cylinderCenter.y - finalY), 0, -180);

                    g2.drawLine(cylinderCenter.x, cylinderCenter.y + cylinderBigRadius * 3, cylinderCenter.x, cylinderCenter.y + cylinderBigRadius * 3);

                    g2.setStroke(DOTTED_LINE);

                    g2.drawArc(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius + cylinderBigRadius * 3,
                            Math.abs(cylinderCenter.x - finalX), Math.abs(cylinderCenter.y - finalY), 0, 180);

                    break;
                case SPHERE:
                    g2.setStroke(DEFAULT_LINE);
                    Point sphereCenter = model.getCircleCenter();
                    int sphereRadius = model.getCircleRadius();
                    int sphereSmallRadius = Math.abs(startY - finalY) / 2;

                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereRadius,
                            Math.abs(sphereCenter.x - finalX), Math.abs(sphereCenter.x - finalX));
                    g2.drawLine(sphereCenter.x, sphereCenter.y, sphereCenter.x, sphereCenter.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereSmallRadius,
                            Math.abs(sphereCenter.x - finalX), Math.abs(sphereCenter.y - finalY));
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
