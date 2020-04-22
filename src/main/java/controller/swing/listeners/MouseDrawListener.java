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
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static model.Model.*;
import static model.Model.AUTO_ACCURACY_FACTOR;
import static model.Model.CONE_DOTTED_ARC_FINAL_ANGLE;
import static model.Model.CONE_DOTTED_ARC_START_ANGLE;
import static model.Model.CONE_GROW_FACTOR;
import static model.Model.CONE_LEFT_BUG_FACTOR;
import static model.Model.CONE_LINED_ARC_FINAL_ANGLE;
import static model.Model.CONE_LINED_ARC_START_ANGLE;
import static model.Model.CONE_RIGHT_BUG_FACTOR;
import static model.Model.CYLINDER_DOTTED_ARC_FINAL_ANGLE;
import static model.Model.CYLINDER_DOTTED_ARC_START_ANGLE;
import static model.Model.CYLINDER_GROW_FACTOR;
import static model.Model.CYLINDER_LINED_ARC_FINAL_ANGLE;
import static model.Model.CYLINDER_LINED_ARC_START_ANGLE;
import static model.Model.CYLINDER_TOP_LEFT_BUG_FACTOR;
import static model.Model.CYLINDER_TOP_RIGHT_BUG_FACTOR;
import static model.Model.PARALLELEPIPED_CURVE_FACTOR;
import static model.Model.PARALLELEPIPED_FRONT_ANGLE_FACTOR;
import static model.Model.PARALLELEPIPED_SIDE_ANGLE_FACTOR;
import static model.Model.PARALLELOGRAM_FACTOR;
import static model.Model.POLYGON_ACCURACY_FACTOR;
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
    private Point leftPolygonPoint;
    private Point rightPolygonPoint;
    private Point topPolygonPoint;
    private ArrayList<Point> prismTopPoints;
    private boolean pointIsNew;
    private Point currentPoint;
    private Point previousPoint;
    private Point currentTopPoint;
    private Point previousTopPoint;
    private int prismHeight;

    public MouseDrawListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
        mainPanel = view.getMainPanel();
        this.startX = model.getStartX();
        this.startY = model.getStartY();
        this.finalX = model.getFinalX();
        this.finalY = model.getFinalY();
        MOUSE_MOTION_ADAPTER = new MouseMotionAdapter();
        MOUSE_ADAPTER = new MouseAdapter();
    }

    private class MouseMotionAdapter implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if (!model.isCustomMode()) {
                setGraphicsAndColor();
                g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

                if (model.isFigureMode()) {
                    view.loadSavedImage();
                    saveCoordsToModel();
                }
                switch (model.getDrawMode()) {
                    case PENCIL:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(PENCIL_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case MARKER:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(MARKER_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case BRUSH:
                        g2.setStroke(new  BasicStroke(Float.valueOf(Config.getProperty(BRUSH_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
//                    saveCoordsAndView(mouseEvent);
                        break;
                    case ERASER:
                        g2.setStroke(new  BasicStroke(model.getEraserStroke()));
                        g2.setColor(Color.WHITE);
                        g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(ERASER_XPOINT_OFFSET)),
                                mouseEvent.getY() + Integer.parseInt(Config.getProperty(ERASER_YPOINT_OFFSET)),
                                model.getEraserStroke(),
                                model.getEraserStroke());
                        break;
                    case RAG:
                        g2.setStroke(new  BasicStroke(model.getRagStroke()));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case LINE:
                        g2.setStroke(DEFAULT_LINE);
                        if (lineIsHorizontal()) {
                            g2.drawLine(startX, startY, finalX, startY);
                        } else if (lineIsVertical()) {
                            g2.drawLine(startX, startY, startX, finalY);
                        } else {
                            g2.drawLine(startX, startY, finalX, finalY);
                        }
                        break;
                    case DOTTEDLINE:
                        g2.setStroke(DOTTED_LINE);
                        if (lineIsHorizontal()) {
                            g2.drawLine(startX, startY, finalX, startY);
                        } else if (lineIsVertical()) {
                            g2.drawLine(startX, startY, startX, finalY);
                        } else {
                            g2.drawLine(startX, startY, finalX, finalY);
                        }
                        break;
//                    case ARROW:
//                        g2.setStroke(DEFAULT_LINE);
//                        if (lineIsHorizontal()) {
//                            g2.drawLine(startX, startY, finalX, startY);
//                        } else if (lineIsVertical()) {
//                            g2.drawLine(startX, startY, startX, finalY);
//                        } else {
//                            g2.drawLine(startX, startY, finalX, finalY);
//                        }
//                    break;
                    case CIRCLE:
                        g2.setStroke(DEFAULT_LINE);
                        Point center = model.getCircleCenter();
                        int radius = model.getEllipseBigRadius();

                        g2.drawOval(center.x - radius, center.y - radius,
                                abs(center.x - finalX), abs(center.x - finalX));
                        g2.drawLine(center.x, center.y, center.x, center.y);
                        break;
                    case ELLIPSE:
                        g2.setStroke(DEFAULT_LINE);
                        Point ellipseCenter = model.getCircleCenter();
                        int bigRadius = model.getEllipseBigRadius();
                        int smallRadius = model.getEllipseSmallRadius();

                        g2.drawOval(ellipseCenter.x - bigRadius, ellipseCenter.y - smallRadius,
                                abs(ellipseCenter.x - finalX), abs(ellipseCenter.y - finalY));
                        g2.drawLine(ellipseCenter.x, ellipseCenter.y, ellipseCenter.x, ellipseCenter.y);
                        break;
                    case RECT:
                        g2.setStroke(DEFAULT_LINE);
                        g2.drawRect(min(finalX, startX), min(finalY, startY),
                                abs(startX - finalX), abs(startY - finalY));
                        Point rectCenter = model.getRectCenter(!isUpDrawing(), !isBackDrawing());
                        setVerticalIndicator(rectCenter,
                                abs(abs(startX - finalX) - abs(startY - finalY)));
                        setHorizontalIndicator(rectCenter,
                                abs(abs(startX - finalX) - abs(startY - finalY)));
                        break;
                    case PARALLELOGRAM:
                        g2.setStroke(DEFAULT_LINE);

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
                                abs(startX - model.getTetraPyramidBottomCenter().x));
                        break;
                    case PRISM:
                        g2.setStroke(DOTTED_LINE);
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
                        if (isUpDrawing()) {
                            model.getParallelepipedFrontLeftBottom().setLocation(startX, startY);
                            model.getParallelepipedFrontRightBottom().setLocation(
                                    finalX - abs(finalY - startY) * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    startY);
                            model.getParallelepipedFrontLeftTop().setLocation(
                                    startX + abs(finalY - startY) * PARALLELEPIPED_CURVE_FACTOR,
                                    finalY + abs(finalY - startY) * PARALLELEPIPED_SIDE_ANGLE_FACTOR);
                            model.getParallelepipedFrontRightTop().setLocation(
                                    finalX + abs(finalY - startY) * (PARALLELEPIPED_CURVE_FACTOR - PARALLELEPIPED_SIDE_ANGLE_FACTOR),
                                    finalY + abs(finalY - startY) * PARALLELEPIPED_SIDE_ANGLE_FACTOR);

                            model.getParallelepipedBackLeftBottom().setLocation(
                                    startX + abs(finalY - startY) * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    finalY + abs(finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                            model.getParallelepipedBackRightBottom().setLocation(
                                    finalX,
                                    finalY + abs(finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                            model.getParallelepipedBackLeftTop().setLocation(
                                    startX + abs(finalY - startY) * (PARALLELEPIPED_CURVE_FACTOR + PARALLELEPIPED_SIDE_ANGLE_FACTOR),
                                    finalY);
                            model.getParallelepipedBackRightTop().setLocation(
                                    finalX + PARALLELEPIPED_CURVE_FACTOR * abs(finalY - startY),
                                    finalY);
                        } else {
                            model.getParallelepipedFrontLeftTop().setLocation(
                                    startX - drawLine * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    finalY - (finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                            model.getParallelepipedFrontRightTop().setLocation(
                                    finalX,
                                    finalY - (finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                            model.getParallelepipedFrontLeftBottom().setLocation(
                                    startX - drawLine * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    finalY);
                            model.getParallelepipedFrontRightBottom().setLocation(finalX, finalY);
                            model.getParallelepipedBackLeftTop().setLocation(startX, startY);
                            model.getParallelepipedBackRightTop().setLocation(
                                    finalX + drawLine * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    startY);
                            model.getParallelepipedBackLeftBottom().setLocation(
                                    startX,
                                    startY + (finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                            model.getParallelepipedBackRightBottom().setLocation(
                                    finalX + drawLine * PARALLELEPIPED_SIDE_ANGLE_FACTOR,
                                    startY + (finalY - startY) * PARALLELEPIPED_FRONT_ANGLE_FACTOR);
                        }
                        Point frontLeftTop = model.getParallelepipedFrontLeftTop();
                        Point frontRightTop = model.getParallelepipedFrontRightTop();
                        Point frontLeftBottom = model.getParallelepipedFrontLeftBottom();
                        Point frontRightBottom = model.getParallelepipedFrontRightBottom();

                        Point backLeftTop = model.getParallelepipedBackLeftTop();
                        Point backRightTop = model.getParallelepipedBackRightTop();
                        Point backLeftBottom = model.getParallelepipedBackLeftBottom();
                        Point backRightBottom = model.getParallelepipedBackRightBottom();

                        g2.drawLine(backLeftTop.x, backLeftTop.y, backRightTop.x, backRightTop.y);
                        g2.drawLine(backLeftTop.x, backLeftTop.y, frontLeftTop.x, frontLeftTop.y);
                        g2.drawLine(backRightTop.x, backRightTop.y, backRightBottom.x, backRightBottom.y);
                        g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontLeftBottom.x, frontLeftBottom.y);
                        g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontRightTop.x, frontRightTop.y);
                        g2.drawLine(backRightTop.x, backRightTop.y, frontRightTop.x, frontRightTop.y);
                        g2.drawLine(frontLeftBottom.x, frontLeftBottom.y, frontRightBottom.x, frontRightBottom.y);
                        g2.drawLine(backRightBottom.x, backRightBottom.y, frontRightBottom.x, frontRightBottom.y);
                        g2.drawLine(frontRightTop.x, frontRightTop.y, frontRightBottom.x, frontRightBottom.y);
                        break;
                    case CONE:
                        g2.setStroke(DOTTED_LINE);
                        Point coneCenter = model.getCircleCenter();
                        int coneBigRadius = model.getEllipseBigRadius();
                        int coneSmallRadius = model.getEllipseSmallRadius();
                        int coneHeight = (int) (coneBigRadius * CONE_GROW_FACTOR);
                        int coneLeftFix = (int) (coneSmallRadius * CONE_LEFT_BUG_FACTOR);
                        int coneRightFix = (int) (coneSmallRadius * CONE_RIGHT_BUG_FACTOR);
                        g2.drawArc(
                                coneCenter.x - coneBigRadius,
                                coneCenter.y - coneSmallRadius,
                                abs(coneCenter.x - finalX), abs(coneCenter.y - finalY),
                                CONE_LINED_ARC_START_ANGLE, CONE_LINED_ARC_FINAL_ANGLE);
                        g2.drawArc(
                                coneCenter.x - coneBigRadius,
                                coneCenter.y - coneSmallRadius,
                                abs(coneCenter.x - finalX), abs(coneCenter.y - finalY),
                                CONE_DOTTED_ARC_START_ANGLE, CONE_DOTTED_ARC_FINAL_ANGLE);
                        if (finalY < coneCenter.y) {
                            g2.drawLine(coneCenter.x, coneCenter.y - coneHeight,
                                    coneCenter.x - coneBigRadius, coneCenter.y - coneLeftFix);
                            g2.drawLine(coneCenter.x, coneCenter.y - coneHeight,
                                    (coneCenter.x + coneBigRadius), coneCenter.y - coneRightFix);
                        } else {
                            g2.drawLine(coneCenter.x, coneCenter.y + coneHeight,
                                    coneCenter.x - coneBigRadius, coneCenter.y + coneLeftFix);
                            g2.drawLine(coneCenter.x, coneCenter.y + coneHeight,
                                    (coneCenter.x + coneBigRadius), coneCenter.y + coneRightFix);
                        }
                        break;
                    case CYLINDER:
                        g2.setStroke(DOTTED_LINE);
                        Point cylinderCenter = model.getCircleCenter();
                        int cylinderBigRadius = model.getEllipseBigRadius();
                        int cylinderSmallRadius = model.getEllipseSmallRadius();
                        int cylinderHeight = (int) (cylinderBigRadius * CYLINDER_GROW_FACTOR);
                        int cylinderLeftFix = (int) (cylinderSmallRadius * CYLINDER_TOP_LEFT_BUG_FACTOR);
                        int cylinderRightFix = (int) (cylinderSmallRadius * CYLINDER_TOP_RIGHT_BUG_FACTOR);
                        g2.drawOval(
                                cylinderCenter.x - cylinderBigRadius,
                                cylinderCenter.y - cylinderSmallRadius - cylinderHeight,
                                abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY));
                        g2.drawLine(
                                cylinderCenter.x - cylinderBigRadius,
                                cylinderCenter.y,
                                cylinderCenter.x - cylinderBigRadius + cylinderLeftFix,
                                cylinderCenter.y - cylinderHeight);
                        g2.drawLine(
                                cylinderCenter.x + cylinderBigRadius,
                                cylinderCenter.y,
                                cylinderCenter.x + cylinderBigRadius + cylinderRightFix,
                                cylinderCenter.y - cylinderHeight);
                        g2.drawOval(cylinderCenter.x - cylinderBigRadius, cylinderCenter.y - cylinderSmallRadius,
                                abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY));
                        break;
                    case SPHERE:
                        g2.setStroke(DOTTED_LINE);

                        Point sphereCenter = model.getCircleCenter();
                        int sphereRadius = model.getEllipseBigRadius();
                        int sphereSmallRadius = model.getEllipseSmallRadius();

                        g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereRadius,
                                abs(sphereCenter.x - finalX), abs(sphereCenter.x - finalX));
                        g2.drawLine(sphereCenter.x, sphereCenter.y, sphereCenter.x, sphereCenter.y);
                        g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereSmallRadius,
                                abs(sphereCenter.x - finalX), abs(sphereCenter.y - finalY));
                        break;
                }
                finalX = mouseEvent.getX();
                finalY = mouseEvent.getY();
                mainPanel.repaint();
            }
        }

        public void mouseMoved(MouseEvent e) { }
    }

    private class MouseAdapter implements MouseListener {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            listenMouseRightButton(mouseEvent);

            if (model.wasIterated()) {
                view.setPreviousImage(null);
                if (model.isPolygonInWork()) {
                    model.setPolygonInWork(false);
                }
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
            setGraphicsAndColor();
            g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

            if (model.isFigureMode() && !model.isCustomMode()) {
                view.loadSavedImage();
            }
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
                    if (lineIsHorizontal()) {
                        g2.drawLine(startX, startY, finalX, startY);
                    } else if (lineIsVertical()) {
                        g2.drawLine(startX, startY, startX, finalY);
                    } else {
                        g2.drawLine(startX, startY, finalX, finalY);
                    }
                    break;
                case DOTTEDLINE:
                    g2.setStroke(DOTTED_LINE);
                    if (lineIsHorizontal()) {
                        g2.drawLine(startX, startY, finalX, startY);
                    } else if (lineIsVertical()) {
                        g2.drawLine(startX, startY, startX, finalY);
                    } else {
                        g2.drawLine(startX, startY, finalX, finalY);
                    }
                    break;
//                case ARROW:
//                    g2.setStroke(DEFAULT_LINE);
//                    if (lineIsHorizontal()) {
//                        g2.drawLine(startX, startY, finalX, startY);
//                    } else if (lineIsVertical()) {
//                        g2.drawLine(startX, startY, startX, finalY);
//                    } else {
//                        g2.drawLine(startX, startY, finalX, finalY);
//                    }

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
                    int radius = model.getEllipseBigRadius();

                    g2.drawOval(center.x - radius, center.y - radius,
                            abs(center.x - finalX), abs(center.x - finalX));
                    g2.drawLine(center.x, center.y, center.x, center.y);
                    break;
                case ELLIPSE:
                    g2.setStroke(DEFAULT_LINE);
                    Point ellipseCenter = model.getCircleCenter();
                    int bigRadius = model.getEllipseBigRadius();
                    int smallRadius = model.getEllipseSmallRadius();

                    g2.drawOval(ellipseCenter.x - bigRadius, ellipseCenter.y - smallRadius,
                            abs(ellipseCenter.x - finalX), abs(ellipseCenter.y - finalY));
                    g2.drawLine(ellipseCenter.x, ellipseCenter.y, ellipseCenter.x, ellipseCenter.y);
                    break;
                case RECT:
                    g2.setStroke(DEFAULT_LINE);
                    g2.drawRect(min(finalX, startX), min(finalY, startY),
                            abs(startX - finalX), abs(startY - finalY));
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
                    drawPolygonIfPointRepeats();

                    if (pointIsNew) {
                        if (!model.isPolygonInWork()) {
                            model.saveAction(view.getMainImage());
                            model.setPolygonInWork(true);
                        }
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
                    g2.setStroke(DEFAULT_LINE);
                    drawPyramidIfPointRepeats();

                    if (pointIsNew) {
                        if (!model.isPolygonInWork()) {
                            model.saveAction(view.getMainImage());
                            model.setPolygonInWork(true);
                        }
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
                case PARALLELEPIPED:
                    g2.setStroke(DEFAULT_LINE);
                    Point frontLeftTop = model.getParallelepipedFrontLeftTop();
                    Point frontRightTop = model.getParallelepipedFrontRightTop();
                    Point frontLeftBottom = model.getParallelepipedFrontLeftBottom();
                    Point frontRightBottom = model.getParallelepipedFrontRightBottom();

                    Point backLeftTop = model.getParallelepipedBackLeftTop();
                    Point backRightTop = model.getParallelepipedBackRightTop();
                    Point backLeftBottom = model.getParallelepipedBackLeftBottom();
                    Point backRightBottom = model.getParallelepipedBackRightBottom();

                    g2.drawLine(backLeftTop.x, backLeftTop.y, backRightTop.x, backRightTop.y);
                    g2.drawLine(backLeftTop.x, backLeftTop.y, frontLeftTop.x, frontLeftTop.y);
                    g2.drawLine(backRightTop.x, backRightTop.y, backRightBottom.x, backRightBottom.y);
                    g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontLeftBottom.x, frontLeftBottom.y);
                    g2.drawLine(frontLeftTop.x, frontLeftTop.y, frontRightTop.x, frontRightTop.y);
                    g2.drawLine(backRightTop.x, backRightTop.y, frontRightTop.x, frontRightTop.y);
                    g2.drawLine(frontLeftBottom.x, frontLeftBottom.y, frontRightBottom.x, frontRightBottom.y);
                    g2.drawLine(backRightBottom.x, backRightBottom.y, frontRightBottom.x, frontRightBottom.y);
                    g2.drawLine(frontRightTop.x, frontRightTop.y, frontRightBottom.x, frontRightBottom.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawLine(frontLeftBottom.x, frontLeftBottom.y, backLeftBottom.x, backLeftBottom.y);
                    g2.drawLine(backRightBottom.x, backRightBottom.y, backLeftBottom.x, backLeftBottom.y);
                    g2.drawLine(backLeftTop.x, backLeftTop.y, backLeftBottom.x, backLeftBottom.y);
                    break;
                case PRISM_CUSTOM:
                    g2.setStroke(DEFAULT_LINE);

                    drawPrismIfPointRepeats();
                    if (pointIsNew) {
                        if (!model.isPolygonInWork()) {
                            model.saveAction(view.getMainImage());
                            model.setPolygonInWork(true);
                        }
                        g2.drawLine(finalX, finalY, finalX, finalY);
                        model.getPointList().add(new Point(finalX, finalY));
                    }
                    break;
                case CONE:
                    g2.setStroke(DEFAULT_LINE);
                    Point coneCenter = model.getCircleCenter();
                    int coneBigRadius = model.getEllipseBigRadius();
                    int coneSmallRadius = model.getEllipseSmallRadius();
                    int coneHeight = (int) (coneBigRadius * CONE_GROW_FACTOR);
                    int coneLeftFix = (int) (coneSmallRadius * CONE_LEFT_BUG_FACTOR);
                    int coneRightFix = (int) (coneSmallRadius * CONE_RIGHT_BUG_FACTOR);

                    g2.drawArc(
                            coneCenter.x - coneBigRadius,
                            coneCenter.y - coneSmallRadius,
                            abs(coneCenter.x - finalX), abs(coneCenter.y - finalY),
                            CONE_LINED_ARC_START_ANGLE, CONE_LINED_ARC_FINAL_ANGLE);
                    g2.drawLine(coneCenter.x, coneCenter.y, coneCenter.x, coneCenter.y);

                    if (finalY < coneCenter.y) {
                        g2.drawLine(coneCenter.x, coneCenter.y - coneHeight,
                                coneCenter.x - coneBigRadius, coneCenter.y - coneLeftFix);
                        g2.drawLine(coneCenter.x, coneCenter.y - coneHeight,
                                (coneCenter.x + coneBigRadius), coneCenter.y - coneRightFix);
                                            g2.setStroke(DOTTED_LINE);
                    } else {
                        g2.drawLine(coneCenter.x, coneCenter.y + coneHeight,
                                coneCenter.x - coneBigRadius, coneCenter.y + coneLeftFix);
                        g2.drawLine(coneCenter.x, coneCenter.y + coneHeight,
                                (coneCenter.x + coneBigRadius), coneCenter.y + coneRightFix);
                    }
                    g2.drawArc(
                            coneCenter.x - coneBigRadius,
                            coneCenter.y - coneSmallRadius,
                            abs(coneCenter.x - finalX), abs(coneCenter.y - finalY),
                            CONE_DOTTED_ARC_START_ANGLE, CONE_DOTTED_ARC_FINAL_ANGLE);
                    break;
                case CYLINDER:
                    g2.setStroke(DEFAULT_LINE);
                    Point cylinderCenter = model.getCircleCenter();
                    int cylinderBigRadius = model.getEllipseBigRadius();
                    int cylinderSmallRadius = model.getEllipseSmallRadius();
                    int cylinderHeight = (int) (cylinderBigRadius * CYLINDER_GROW_FACTOR);
                    int cylinderLeftFix = (int) (cylinderSmallRadius * CYLINDER_TOP_LEFT_BUG_FACTOR);
                    int cylinderRightFix = (int) (cylinderSmallRadius * CYLINDER_TOP_RIGHT_BUG_FACTOR);

                    g2.drawOval(
                            cylinderCenter.x - cylinderBigRadius,
                            cylinderCenter.y - cylinderSmallRadius - cylinderHeight,
                            abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY));
                    g2.drawLine(
                            cylinderCenter.x - cylinderBigRadius,
                                cylinderCenter.y,
                            cylinderCenter.x - cylinderBigRadius + cylinderLeftFix,
                            cylinderCenter.y - cylinderHeight);
                    g2.drawLine(
                            cylinderCenter.x + cylinderBigRadius,
                                cylinderCenter.y,
                            cylinderCenter.x + cylinderBigRadius + cylinderRightFix,
                            cylinderCenter.y - cylinderHeight);
                    g2.drawArc(
                            cylinderCenter.x - cylinderBigRadius,
                            cylinderCenter.y - cylinderSmallRadius,
                            abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY),
                            CYLINDER_LINED_ARC_START_ANGLE, CYLINDER_LINED_ARC_FINAL_ANGLE);
                    g2.drawLine(cylinderCenter.x, cylinderCenter.y - cylinderHeight,
                            cylinderCenter.x, cylinderCenter.y - cylinderHeight);
                    g2.drawLine(cylinderCenter.x, cylinderCenter.y, cylinderCenter.x, cylinderCenter.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawArc(
                            cylinderCenter.x - cylinderBigRadius,
                            cylinderCenter.y - cylinderSmallRadius,
                            abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY),
                            CYLINDER_DOTTED_ARC_START_ANGLE, CYLINDER_DOTTED_ARC_FINAL_ANGLE);
                    break;
                case SPHERE:
                    g2.setStroke(DEFAULT_LINE);
                    Point sphereCenter = model.getCircleCenter();
                    int sphereRadius = model.getEllipseBigRadius();
                    int sphereSmallRadius = model.getEllipseSmallRadius();

                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereRadius,
                            abs(sphereCenter.x - finalX), abs(sphereCenter.x - finalX));
                    g2.drawLine(sphereCenter.x, sphereCenter.y, sphereCenter.x, sphereCenter.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawOval(sphereCenter.x - sphereRadius, sphereCenter.y - sphereSmallRadius,
                            abs(sphereCenter.x - finalX), abs(sphereCenter.y - finalY));
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
            if (model.isFigureMode() && !model.isCustomMode()) {
                view.saveCurrentImage();
            }
            if (!model.isCustomMode()) {
                model.saveAction(view.getMainImage());
            }
            mainPanel.repaint();
        }

        public void mouseClicked(MouseEvent mouseEvent) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
    }

    private void setGraphicsAndColor() {
        Graphics g = view.getMainImage().getGraphics();
        g2 = (Graphics2D)g;
        g2.setColor(view.getMainColor());
    }

    private void saveCoordsAndView(MouseEvent mouseEvent) {
        finalX = mouseEvent.getX();
        finalY = mouseEvent.getY();
    }

    private  void saveCoordsToModel() {
        model.setStartX(startX);
        model.setStartY(startY);
        model.setFinalX(finalX);
        model.setFinalY(finalY);

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
                        (abs(model.getTetraPyramidBackLeft().x - model.getTetraPyramidFrontRight().x)) / 2,
                model.getTetraPyramidBackLeft().y +
                        (abs(model.getTetraPyramidBackLeft().y - model.getTetraPyramidFrontRight().y)) / 2
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

    private void drawPolygonIfPointRepeats() {
        pointIsNew = true;
        setAccuracyArea();

        for (Point point : areaPoints) {
            if (model.getPointList().contains(point)) {
                model.fillPolygon();
                g2.drawPolygon(model.getPolygon());

                model.setPolygonInWork(false);
                view.saveCurrentImage();
                model.reSaveAction(view.getMainImage());

                model.resetAllCustomPoints();
                pointIsNew = false;
                break;
            }
        }
    }

    private void drawPyramidIfPointRepeats() {
        resetControlAndPeakPoints();
        setAccuracyArea();

        for (Point areaPoint : areaPoints) {
            if (model.getPointList().contains(areaPoint)) {
                setPeakPoints();

                for (Point thisPoint : model.getPointList()) {
                    if (pointIsBehind(thisPoint)) {
                        g2.setStroke(DOTTED_LINE);
                        controlPoints.add(thisPoint);
                    }
                    g2.drawLine(topPolygonPoint.x, topPolygonPoint.y, thisPoint.x, thisPoint.y);

                    drawSectorLine(thisPoint);
                    g2.setStroke(DEFAULT_LINE);
                }
                if (oneOfPointsAboveLeftPoint() && previousPoint != rightPolygonPoint) {
                    g2.setStroke(DOTTED_LINE);
                }
                g2.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);

                model.setPolygonInWork(false);
                view.saveCurrentImage();
                model.reSaveAction(view.getMainImage());

                model.resetAllCustomPoints();
                pointIsNew = false;
                break;
            }
        }
    }

    private void drawPrismIfPointRepeats() {
        resetControlAndPeakPoints();
        setAccuracyArea();

        for (Point point : areaPoints) {
            if (model.getPointList().contains(point)) {
                setPeakPoints();

                for (int i = 0; i < model.getPointList().size(); i++) {
                    if (pointIsBehind(model.getPointList().get(i))) {
                        g2.setStroke(DOTTED_LINE);
                        controlPoints.add(model.getPointList().get(i));
                    }
                    g2.drawLine(
                            model.getPrismTopPointList().get(i).x, model.getPrismTopPointList().get(i).y,
                            model.getPointList().get(i).x, model.getPointList().get(i).y);

                    drawSectorLine(model.getPointList().get(i));
                    g2.setStroke(DEFAULT_LINE);

                    drawTopSectorLine(model.getPrismTopPointList().get(i));
                }
                g2.drawLine(currentTopPoint.x, currentTopPoint.y, previousTopPoint.x, previousTopPoint.y);
                if (oneOfPointsAboveLeftPoint() && previousPoint != rightPolygonPoint) {
                    g2.setStroke(DOTTED_LINE);
                }
                g2.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);

                model.setPolygonInWork(false);
                view.saveCurrentImage();
                model.reSaveAction(view.getMainImage());

                pointIsNew = false;
                model.resetAllCustomPoints();
                break;
            }
        }
    }

    private void resetControlAndPeakPoints() {
        pointIsNew = true;
        controlPoints = new ArrayList<>();

        leftPolygonPoint = new Point(Integer.MAX_VALUE, 0);
        rightPolygonPoint = new Point(0,0);
        topPolygonPoint = new Point();

        previousPoint = null;
        currentPoint = null;
        previousTopPoint = null;
        currentTopPoint = null;
    }

    private void setAccuracyArea() {
        areaPoints = new ArrayList<>();
        for (int i = (0 - (int) (Math.sqrt(POLYGON_ACCURACY_FACTOR / 4)));
             i < (int) (Math.sqrt(POLYGON_ACCURACY_FACTOR / 4)) ; i++) {
            for (int j = (0 - (int) (Math.sqrt(POLYGON_ACCURACY_FACTOR / 4)));
                 j < (int) (Math.sqrt(POLYGON_ACCURACY_FACTOR / 4)); j++) {
                areaPoints.add(new Point(finalX + i, finalY + j));
            }
        }
    }

    private void setPeakPoints() {
        for (Point bottomPoint : model.getPointList()) {
            if (bottomPoint.x > rightPolygonPoint.x) {
                rightPolygonPoint = bottomPoint;
            }
            if (bottomPoint.x < leftPolygonPoint.x) {
                leftPolygonPoint = bottomPoint;
            }
        }
        topPolygonPoint.x = leftPolygonPoint.x + (rightPolygonPoint.x - leftPolygonPoint.x) / 2;
        topPolygonPoint.y = (int) (leftPolygonPoint.y - (rightPolygonPoint.x - leftPolygonPoint.x) * PYRAMID_GROW_FACTOR);
        if (topPolygonPoint.y < PYRAMID_TOP_PADDING) {
            topPolygonPoint.y = PYRAMID_TOP_PADDING;
        }

        prismHeight = (int) ((rightPolygonPoint.x - leftPolygonPoint.x) * PRISM_GROW_FACTOR);
        for (Point bottomPoint : model.getPointList()) {
            model.getPrismTopPointList().add(new Point(bottomPoint.x, bottomPoint.y - prismHeight));
        }
    }

    private boolean pointIsBehind(Point thisPoint) {
        return isNotRight(thisPoint) && isNotLeft(thisPoint)
                && aboveTheCenter(thisPoint);
    }

    private void drawSectorLine(Point thisPoint) {
        if (currentPoint == null) {
            currentPoint = thisPoint;
        }
        if (previousPoint != null) {
            if (oneOfPointIsControl(thisPoint)) {
                g2.setStroke(DOTTED_LINE);
            }
            g2.drawLine(thisPoint.x, thisPoint.y, previousPoint.x, previousPoint.y);
        }
        previousPoint = thisPoint;
    }

    private void drawTopSectorLine(Point thisPoint) {
        if (currentTopPoint == null) {
            currentTopPoint = thisPoint;
        }
        if (previousTopPoint != null) {
            if (controlPoints.contains(thisPoint)
                    || controlPoints.contains(previousTopPoint)) {
                g2.setStroke(DOTTED_LINE);
            }
            g2.drawLine(thisPoint.x, thisPoint.y, previousTopPoint.x, previousTopPoint.y);
        }
        previousTopPoint = thisPoint;
    }

    private boolean oneOfPointIsControl(Point thisPoint) {
        return controlPoints.contains(thisPoint)
                || controlPoints.contains(previousPoint);
    }

    private boolean oneOfPointsAboveLeftPoint() {
        return currentPoint.y < leftPolygonPoint.y
                || previousPoint.y < leftPolygonPoint.y;
    }

    private boolean lineIsHorizontal() {
        return abs(finalY - startY) < AUTO_ACCURACY_FACTOR;
    }

    private boolean lineIsVertical() {
        return abs(finalX - startX) < AUTO_ACCURACY_FACTOR;
    }

    private boolean isNotRight(Point point) {
        return point.x < rightPolygonPoint.x;
    }

    private boolean isNotLeft(Point point) {
        return point.x > leftPolygonPoint.x;
    }

    private boolean aboveTheCenter(Point point) {
        return  point.y < leftPolygonPoint.y - (leftPolygonPoint.y - rightPolygonPoint.y) / 2;
    }

    private boolean isUpDrawing() {
        return finalY < startY;
    }

    private boolean isBackDrawing() {
        return finalX < startX;
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
