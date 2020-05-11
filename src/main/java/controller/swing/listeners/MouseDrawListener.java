package controller.swing.listeners;

import config.Config;
import model.DrawMode;
import model.Model;
import util.CursorBuilder;
import view.swing.SwingViewImpl;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static config.Config.*;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static model.Model.*;
import static model.Model.ARROW_MAX_LENGTH;
import static model.Model.ARROW_MIN_LENGTH;
import static model.Model.ARROW_PEAK_FACTOR;
import static model.Model.ARROW_WIDTH_FACTOR;
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
import static model.Model.STICKY_LINE_FACTOR;

public class MouseDrawListener {
    private static float CUSTOM_FACTOR = 1f;

    private static final BasicStroke DOTTED_LINE = new BasicStroke(
            Float.valueOf(Config.getProperty(Config.BASIC_STROKE_WIDTH)),
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER,
            10.0f,
            new float[]{
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_LENGTH)),
                    Float.valueOf(Config.getProperty(Config.BASIC_STROKE_OFFSET_LENGTH))
            },
            0.0f);
    private static BasicStroke DEFAULT_LINE = new  BasicStroke(
            Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));

    private final MouseMotionAdapter MOUSE_MOTION_ADAPTER;
    private final MouseAdapter MOUSE_ADAPTER;


    private SwingViewImpl view;
    private Model model;
//    private JPanel view.getMainPanel();
    private Graphics2D g2;
    private int startX;
    private int startY;
    private int finalX;
    private int finalY;
    private double drawLine;
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
    private ArrayList<Integer> polX;
    private ArrayList<Integer> polY;
    private boolean polBorderDraw;
    private int copyPolX;
    private int copyPolY;
    private int[] polCopyX;
    private int[] polCopyY;
    private BufferedImage copyShape;
    private Polygon mys;

    public MouseDrawListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;
//        view.getMainPanel() = view.getview.getMainPanel()();
        this.startX = model.getStartX();
        this.startY = model.getStartY();
        this.finalX = model.getFinalX();
        this.finalY = model.getFinalY();
        MOUSE_MOTION_ADAPTER = new MouseMotionAdapter();
        MOUSE_ADAPTER = new MouseAdapter();

        polX = new ArrayList<>(1000);
        polY = new ArrayList<>(1000);
    }

    private class MouseMotionAdapter implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

            switch (model.getDrawMode()) {
                case SCALE:
                    saveCoordsToModel();

                    view.loadSavedImage();
                    setGraphicsAndColor();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int scaleStartX = model.getScaleRectX();
                    int scaleStartY = model.getScaleRectY();
                    int scaleWidth = model.getScaleRectWidth();
                    int scaleHeight = model.getScaleRectHeight();

                    g2.drawRect(scaleStartX, scaleStartY, scaleWidth, scaleHeight);
                    finalX = mouseEvent.getX() < 0 + scaleWidth / 2 ? 0 + scaleWidth / 2 :
                            mouseEvent.getX() > model.getDrawWidth() - scaleWidth / 2 ?
                                    model.getDrawWidth() - scaleWidth / 2 : mouseEvent.getX();
                    finalY = mouseEvent.getY() < 0 + scaleHeight / 2 ? 0 + scaleHeight / 2 :
                            mouseEvent.getY() > model.getDrawHeight() - scaleHeight / 2 ?
                                    model.getDrawHeight() - scaleHeight / 2 : mouseEvent.getY();
                    finalX = mouseEvent.getX();
                    finalY = mouseEvent.getY();
                    view.getMainPanel().repaint();
                    break;
            }
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            if (!model.isCustomMode()) {
                setGraphicsAndColor();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (model.isFigureMode() && model.getDrawMode() != DrawMode.COPY_SHAPE && model.getDrawMode() != DrawMode.CUT_SHAPE) {
                    view.loadSavedImage();
                    saveCoordsToModel();
                }

                g2.setStroke(DOTTED_LINE);
                switch (model.getDrawMode()) {
                    case PENCIL:
//                        mouseEvent = SwingUtilities
//                        mouseEvent = SwingUtilities.convertMouseEvent(
//                                mouseEvent.getComponent(), mouseEvent, SwingUtilities.windowForComponent(mouseEvent.getComponent()));
                        g2.setStroke(new BasicStroke(Float.valueOf(Config.getProperty(PENCIL_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case MARKER:
                        g2.setStroke(new BasicStroke(Float.valueOf(Config.getProperty(MARKER_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case BRUSH:
                        g2.setStroke(new BasicStroke(Float.valueOf(Config.getProperty(BRUSH_BASIC_STROKE))));
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case ERASER:
                        g2.setStroke(new BasicStroke(model.getEraserStroke()));
                        g2.setColor(Color.WHITE);
                        g2.fillOval(mouseEvent.getX() - Integer.parseInt(Config.getProperty(ERASER_XPOINT_OFFSET)),
                                mouseEvent.getY() + Integer.parseInt(Config.getProperty(ERASER_YPOINT_OFFSET)),
                                model.getEraserStroke(),
                                model.getEraserStroke());
                        break;
                    case RAG:
                        g2.setStroke(new BasicStroke(model.getRagStroke()));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(finalX, finalY, mouseEvent.getX(), mouseEvent.getY());
                        break;
                    case COPY:
                        g2.setStroke(DOTTED_LINE);
                        g2.setColor(Color.BLUE);
                        finalX = mouseEvent.getX();
                        finalY = mouseEvent.getY();
                        g2.drawRect(Math.min(startX, finalX), Math.min(startY, finalY), Math.abs(finalX - startX), Math.abs(finalY - startY));
                        break;
                    case CUT:
                        g2.setStroke(DOTTED_LINE);
                        g2.setColor(Color.BLACK);
                        finalX = mouseEvent.getX();
                        finalY = mouseEvent.getY();
                        g2.drawRect(Math.min(startX, finalX), Math.min(startY, finalY), Math.abs(finalX - startX), Math.abs(finalY - startY));
                        break;
                    case COPY_SHAPE:
                    case CUT_SHAPE:
                        polX.add(mouseEvent.getX());
                        polY.add(mouseEvent.getY());
                        g2.setColor(Color.BLUE);
                        if (model.isCutting()) {
                            g2.setColor(Color.BLACK);
                        }
                        g2.drawLine(startX, startY, mouseEvent.getX(),mouseEvent.getY());
                        startX = mouseEvent.getX();
                        startY = mouseEvent.getY();
                        break;
                    case PASTE:
                        view.getTabbedPane().setCursor(CursorBuilder.buildICursorByPath(Config.getProperty(Config.GRAB_CURSOR_PATH),
                                Integer.parseInt(Config.getProperty(GRAB_CURSOR_XPOINT)),
                                Integer.parseInt(Config.getProperty(GRAB_CURSOR_YPOINT)),
                                                ""));

                        finalX = mouseEvent.getX();
                        finalY = mouseEvent.getY();
                        polBorderDraw = true;
                        pasteDefaultShape(finalX, finalY);
                        break;
                    case LINE:
                        g2.setStroke(DEFAULT_LINE);
                    case DOTTED_LINE:
                    case ARROW:
                        drawStickyLine();
                        break;
                    case CIRCLE:
                        Point center = model.getCircleCenter();
                        int radius = model.getEllipseBigRadius();

                        g2.drawOval(center.x - radius, center.y - radius,
                                abs(center.x - finalX), abs(center.x - finalX));
                        g2.drawLine(center.x, center.y, center.x, center.y);
                        break;
                    case ELLIPSE:
                        Point ellipseCenter = model.getCircleCenter();
                        int bigRadius = model.getEllipseBigRadius();
                        int smallRadius = model.getEllipseSmallRadius();

                        g2.drawOval(ellipseCenter.x - bigRadius, ellipseCenter.y - smallRadius,
                                abs(ellipseCenter.x - finalX), abs(ellipseCenter.y - finalY));
                        g2.drawLine(ellipseCenter.x, ellipseCenter.y, ellipseCenter.x, ellipseCenter.y);
                        break;
                    case RECT:
                        g2.drawRect(min(finalX, startX), min(finalY, startY),
                                abs(startX - finalX), abs(startY - finalY));
                        Point rectCenter = model.getRectCenter(!isUpDrawing(), !isBackDrawing());
                        setVerticalIndicator(rectCenter,
                                abs(abs(startX - finalX) - abs(startY - finalY)));
                        setHorizontalIndicator(rectCenter,
                                abs(abs(startX - finalX) - abs(startY - finalY)));
                        break;
                    case PARALLELOGRAM:
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
                        if (leftPoint.x > frontPoint.x) {
                            leftPoint.x = frontPoint.x - 10;
                        }
                        if (rightPoint.x < startX) {
                            rightPoint.x = startX;
                        }
                        if (leftPoint.y < startY) {
                            leftPoint.y = startY + 10;
                            rightPoint.y = startY + 10;
                        }
                        if (frontPoint.y < startY + 10) {
                            frontPoint.y = startY + 20;
                        }

                        g2.drawLine(startX, startY, frontPoint.x, frontPoint.y);
                        g2.drawLine(startX, startY, leftPoint.x, leftPoint.y);
                        g2.drawLine(startX, startY, rightPoint.x, rightPoint.y);
                        break;
                    case PYRAMID_TETRA:
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
                        Point backLeft = model.getTetraPyramidBackLeft();
                        Point frontLeft = model.getTetraPyramidFrontLeft();
                        Point frontRight = model.getTetraPyramidFrontRight();

                        if (frontLeft.x > startX) {
                            frontLeft.x = startX;
                        }
                        if ((backLeft.y - startY) * 2 < frontRight.y - startY) {
                            backLeft.y = startY + (frontRight.y - startY) / 2;
                        }
                        setPyramidCenter();


                        g2.drawLine(startX, startY, frontRight.x, finalY);
                        g2.drawLine(startX, startY, frontLeft.x, frontLeft.y);
                        g2.drawLine(startX, startY, backRight.x, backRight.y);
                        setVerticalIndicator(model.getTetraPyramidBottomCenter(),
                                abs(startX - model.getTetraPyramidBottomCenter().x));
                        break;
                    case PRISM:
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
                view.getMainPanel().repaint();
                g2.dispose();

            }
        }
    }

    private class MouseAdapter implements MouseListener {

        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            listenMouseRightButton(mouseEvent);

            if (model.wasIterated(view.getTabbedPane().getSelectedIndex())) {
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
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
                    g2.drawLine(mouseEvent.getX(), mouseEvent.getY(),
                            mouseEvent.getX(), mouseEvent.getY());
                    break;
                case COPY:
                    g2.setStroke(DOTTED_LINE);
//                    g2.setColor(Color.BLACK);
                    copyPart(Math.min(startX , finalX), Math.min(startY, finalY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    break;
                case CUT:
                    g2.setStroke(DOTTED_LINE);
                    g2.setColor(Color.BLACK);
                    cutPart(Math.min(startX , finalX), Math.min(startY, finalY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    g2.drawRect(Math.min(startX , finalX), Math.min(startY, finalY),
                            Math.abs(startX - finalX), Math.abs(startY - finalY));
                    break;
                case COPY_SHAPE:
                case CUT_SHAPE:
                    g2.setStroke(DOTTED_LINE);
                    view.saveCurrentImage();
                    cutDefaultShape();
                    if (model.isCutting()) {
                        g2.drawPolygon(mys);
                    }
                    break;
                case PASTE:
                    view.getTabbedPane().setCursor(
                            CursorBuilder.buildCursorByDrawMode(model.getDrawMode()));
                    finalX = mouseEvent.getX();
                    finalY = mouseEvent.getY();

                    polBorderDraw = false;
                    pasteDefaultShape(finalX, finalY);
                    view.saveCurrentImage();
                    model.setFirstMove(true);
                    break;
                case LINE:
                    g2.setStroke(DEFAULT_LINE);
                    drawStickyLine();
                    break;
                case DOTTED_LINE:
                    g2.setStroke(DOTTED_LINE);
                    drawStickyLine();
                    break;
                case ARROW:
                    g2.setStroke(DEFAULT_LINE);
                    drawStickyLine();
                    drawArrowPeak();
                    break;
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
                            model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
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
                            model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
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
                            model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
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
                            cylinderCenter.x - abs(cylinderCenter.x - finalX) / 2,
                            cylinderCenter.y - cylinderSmallRadius - cylinderHeight,
                            abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY));
                    g2.drawLine(
                            cylinderCenter.x - abs(cylinderCenter.x - finalX) / 2,
                                cylinderCenter.y,
                            cylinderCenter.x - abs(cylinderCenter.x - finalX) / 2 + cylinderLeftFix,
                            cylinderCenter.y - cylinderHeight);
                    g2.drawLine(
                            cylinderCenter.x + abs(cylinderCenter.x - finalX) / 2 + cylinderRightFix,
                                cylinderCenter.y,
                            cylinderCenter.x + abs(cylinderCenter.x - finalX) / 2 + cylinderRightFix,
                            cylinderCenter.y - cylinderHeight);
                    g2.drawArc(
                            cylinderCenter.x - abs(cylinderCenter.x - finalX) / 2,
                            cylinderCenter.y - cylinderSmallRadius,
                            abs(cylinderCenter.x - finalX), abs(cylinderCenter.y - finalY),
                            CYLINDER_LINED_ARC_START_ANGLE, CYLINDER_LINED_ARC_FINAL_ANGLE);
                    g2.drawLine(cylinderCenter.x, cylinderCenter.y - cylinderHeight,
                            cylinderCenter.x, cylinderCenter.y - cylinderHeight);
                    g2.drawLine(cylinderCenter.x, cylinderCenter.y, cylinderCenter.x, cylinderCenter.y);

                    g2.setStroke(DOTTED_LINE);
                    g2.drawArc(
                            cylinderCenter.x - abs(cylinderCenter.x - finalX) / 2,
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
                    g2.fillRect(0, 0, view.getMainPanel().getSize().width, view.getMainPanel().getSize().height);
                    break;
//                case TEXT:
//                    view.getMainPanel().requestFocus();
//                    break;
                case SCALE:
                    view.setImageScale(view.getImageScale() * Model.RESIZE_PLUS_FACTOR);
                    model.setCurrentScale(model.getCurrentScale() * Model.RESIZE_PLUS_FACTOR);

                    int scaledX = model.getScaleRectX();
                    int scaledY = model.getScaleRectY();
//                    view.loadSavedImage();
//                    g2.drawRect(model.getScaleRectX(), model.getScaleRectY(), model.getScaleRectWidth(), model.getScaleRectHeight());
                    view.setTranslateImage(0 - scaledX, 0 - scaledY);
                    break;
            }
            finalX = 0;
            finalY = 0;
            if (model.isFigureMode() && !model.isCustomMode() && !model.isCopyMode()) {
                view.saveCurrentImage();
            }
            if (!model.isCustomMode() && !model.isScaleMode()) {
                model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
            }

            view.getMainPanel().repaint();
            g2.dispose();

            view.testSizes();
        }

        public void mouseClicked(MouseEvent mouseEvent) { }
    }

    private void setGraphicsAndColor() {
        g2 = view.getMainImage().createGraphics();
        g2.setColor(view.getMainColor());
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
            if (view.getMainColor() != SwingViewImpl.getDefaultColor()) {
                view.setMainColor(SwingViewImpl.getDefaultColor());
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
                model.reSaveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());

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
                drawDottedPolygonAndFinish();
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
                drawDottedPolygonAndFinish();
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

    private void drawDottedPolygonAndFinish() {
        if (oneOfPointsAboveLeftPoint() && previousPoint != rightPolygonPoint) {
            g2.setStroke(DOTTED_LINE);
        }
        g2.drawLine(currentPoint.x, currentPoint.y, previousPoint.x, previousPoint.y);

        model.setPolygonInWork(false);
        view.saveCurrentImage();
        model.reSaveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());

        pointIsNew = false;
        model.resetAllCustomPoints();
    }

    private boolean oneOfPointIsControl(Point thisPoint) {
        return controlPoints.contains(thisPoint)
                || controlPoints.contains(previousPoint);
    }

    private boolean oneOfPointsAboveLeftPoint() {
        return currentPoint.y < leftPolygonPoint.y
                || previousPoint.y < leftPolygonPoint.y;
    }

    private void drawStickyLine() {
        if (lineIsHorizontal() && drawLine > STICKY_LINE_FACTOR) {
            g2.drawLine(startX, startY, finalX, startY);
            saveCoordsToModel();
            model.setFinalY(startY);
        } else if (lineIsVertical() && drawLine > STICKY_LINE_FACTOR) {
            g2.drawLine(startX, startY, startX, finalY);
            saveCoordsToModel();
            model.setFinalX(startX);
        } else if (lineIsDiagonal() && drawLine > STICKY_LINE_FACTOR) {
            int diagonalFactor =
                    abs(finalX - startX) - abs(finalY - startY) > 0 ?
                    abs(abs(finalX - startX) - abs(finalY - startY)) :
                    abs(abs(finalX - startX) - abs(finalY - startY)) * -1;

            int diagonalX = finalX;
            int diagonalY = finalY;
            if (finalX > startX) {
                diagonalX = finalX - diagonalFactor;
            } else if (finalX < startX && finalY > startY){
                diagonalY = finalY + diagonalFactor;
            } else {
                diagonalY = finalY - diagonalFactor;
            }

            g2.drawLine(startX, startY, diagonalX, diagonalY);
            saveCoordsToModel();
            model.setFinalX(diagonalX);
            model.setFinalY(diagonalY);
        } else {
            g2.drawLine(startX, startY, finalX, finalY);
            saveCoordsToModel();
        }

        drawLine = model.getDrawLine();
    }

    private void drawArrowPeak() {
        Point arrowEndPoint = new Point(model.getFinalX(), model.getFinalY());
        double arrowSideFactor = drawLine * ARROW_PEAK_FACTOR;
        arrowSideFactor = arrowSideFactor < ARROW_MIN_LENGTH ? ARROW_MIN_LENGTH : arrowSideFactor > ARROW_MAX_LENGTH ? ARROW_MAX_LENGTH : arrowSideFactor;
        double arrowWidthFactor = arrowSideFactor / ARROW_WIDTH_FACTOR;

        Point mainVector = new Point(arrowEndPoint.x - startX, arrowEndPoint.y - startY);
        double unitMainVectorX = mainVector.x / drawLine;
        double unitMainVectorY = mainVector.y / drawLine;

        double arrowMainVectorX = unitMainVectorX * arrowSideFactor;
        double arrowMainVectorY = unitMainVectorY * arrowSideFactor;

        Point arrowMainPoint = new Point(arrowEndPoint.x - (int) arrowMainVectorX, arrowEndPoint.y - (int) arrowMainVectorY);

        Point mainNormal = new Point(arrowEndPoint.y - startY, startX - arrowEndPoint.x);
        double unitMainNormalX = mainNormal.x / drawLine;
        double unitMainNormalY = mainNormal.y / drawLine;

        double arrowWidthVectorX = unitMainNormalX * arrowWidthFactor;
        double arrowWidthVectorY = unitMainNormalY * arrowWidthFactor;

        Point arrowLeftPoint = new Point(arrowMainPoint.x + (int) arrowWidthVectorX, arrowMainPoint.y + (int) arrowWidthVectorY);
        Point arrowRightPoint = new Point(arrowMainPoint.x - (int) arrowWidthVectorX, arrowMainPoint.y - (int) arrowWidthVectorY);

        Polygon arrowPeak = new Polygon(
                new int[]{arrowEndPoint.x, arrowLeftPoint.x, arrowRightPoint.x},
                new int[]{arrowEndPoint.y, arrowLeftPoint.y, arrowRightPoint.y},
                3);
        g2.drawPolygon(arrowPeak);
        g2.fillPolygon(arrowPeak);
    }

    private boolean lineIsHorizontal() {
        return abs(finalY - startY) < AUTO_ACCURACY_FACTOR;
    }

    private boolean lineIsVertical() {
        return abs(finalX - startX) < AUTO_ACCURACY_FACTOR;
    }

    private boolean lineIsDiagonal() {
        return abs(abs(finalX - startX) - abs(finalY - startY)) < AUTO_ACCURACY_FACTOR;
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

    public static void decreaseCustomFactor() {
        if (CUSTOM_FACTOR > 0.5)
        CUSTOM_FACTOR -= 0.5;
    }

    public static void increaseCustomFactor() {
        CUSTOM_FACTOR += 0.5;
    }

    public static void resetCustomFactor() {
        CUSTOM_FACTOR = 1f;
    }

    public static void rebaseDefaultStroke() {
        DEFAULT_LINE = new  BasicStroke(
                Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)) * CUSTOM_FACTOR);
    }

    public void copyPart(int x1, int y1, int h, int w) {
        polX.add(x1);
        polY.add(y1);
        polX.add(x1 + h);
        polY.add(y1);
        polX.add(x1 + h);
        polY.add(y1 + w);
        polX.add(x1);
        polY.add(y1 + w);
        cutDefaultShape();
    }

    public void cutPart(int x1, int y1, int h, int w) {
        view.loadSavedImage();
        copyPart(x1, y1, h, w);
        view.loadSavedImage();
        Polygon mys = new Polygon();
        for (int i = 0; i < polCopyX.length; i++) {
            mys.addPoint(polCopyX[i], polCopyY[i]);
        }

        Graphics g1 = view.getMainImage().getGraphics();
        g1.fillPolygon(mys);

        view.saveCurrentImage();
        pasteDefaultShape(finalX - (polCopyX[1] - polCopyX[0]) / 2, finalY - (polCopyY[2] - polCopyY[1]) / 2);
        view.resetToolButtonBorders();
        view.getToolButtons().get("PASTE").setBorderPainted(true);
        model.setDrawMode(DrawMode.PASTE);
        view.getTabbedPane().setCursor(CursorBuilder.buildCursorByDrawMode(DrawMode.PASTE));
        try {
            new Robot().mouseMove(finalX - (polCopyX[1] - polCopyX[0]) / 2, finalY - (polCopyY[2] - polCopyY[1]) / 2);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }

    public void cutDefaultShape() {
        copyShape = new BufferedImage(
                view.getMainPanel().getWidth(),
                view.getMainPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyShape.getGraphics();
        g.drawImage(view.getMainImage(), 0, 0, null);

        polCopyX = new int[polX.size()];
        polCopyY = new int[polY.size()];
        for (int i = 0; i < polX.size(); i++) {
            polCopyX[i] = polX.get(i);
            polCopyY[i] = polY.get(i);
        }
        mys = new Polygon(polCopyX, polCopyY, polCopyX.length);
        Graphics g1 = view.getMainImage().getGraphics();
        g1.setColor(Color.BLUE);
        ((Graphics2D)g1).setStroke(DOTTED_LINE);

        copyPolX = mys.getBounds().x;
        copyPolY = mys.getBounds().y;

        if (model.isCutting()) {
            g1.setColor(Color.WHITE);
            g1.fillPolygon(mys);
            view.saveCurrentImage();

            int minX = Collections.min(polX);
            int minY = Collections.min(polY);
            int maxX = Collections.max(polX);
            int maxY = Collections.max(polY);
            pasteDefaultShape(
                    minX + (maxX - minX) / 2,
                    minY + (maxY - minY) / 2);
        }
        g1.drawPolygon(mys);



        view.resetToolButtonBorders();
        view.getToolButtons().get("PASTE").setBorderPainted(true);
        model.setDrawMode(DrawMode.PASTE);
        view.getTabbedPane().setCursor(
                CursorBuilder.buildCursorByDrawMode(DrawMode.PASTE));
        if (model.isRectExtract()) {
            try {
                new Robot().mouseMove(
                        finalX - (polX.get(1) - polX.get(0)) / 2,
                        finalY - (polY.get(2) - polY.get(1)) / 2);
            } catch (AWTException e1) { e1.printStackTrace(); }
        } else {
            try {
                int minX = Collections.min(polX);
                int minY = Collections.min(polY);
                int maxX = Collections.max(polX);
                int maxY = Collections.max(polY);
                new Robot().mouseMove(
                        minX + (maxX - minX) / 2,
                        minY + (maxY - minY) / 2
                );
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }


        polX.clear();
        polY.clear();
    }

    private void pasteDefaultShape(int finalX, int finalY) {
        int dx = 0;
        int dy = 0;
        if (model.isRectExtract()) {
            dx = finalX - copyPolX - (polCopyX[1] - polCopyX[0]) / 2;
            dy = finalY - copyPolY - (polCopyY[2] - polCopyY[1]) / 2;
        } else {
            ArrayList<Integer> xList = new ArrayList<>();
            for (int i = 0; i < polCopyX.length; i++) {
                xList.add(polCopyX[i]);
            }
            ArrayList<Integer> yList = new ArrayList<>();
            for (int i = 0; i < polCopyY.length; i++) {
                yList.add(polCopyY[i]);
            }

            dx = finalX - copyPolX - (Collections.max(xList) - Collections.min(xList)) / 2;
            dy = finalY - copyPolY - (Collections.max(yList) - Collections.min(yList)) / 2;
        }

        Polygon mys = new Polygon();
        for (int i = 0; i < polCopyX.length; i++) {
            mys.addPoint(polCopyX[i] + dx, polCopyY[i] + dy);
        }

        Graphics g1 = view.getMainImage().getGraphics();

        if (model.isCutting() && model.isFirstMove()) {
            g1.fillPolygon(mys);
            view.saveCurrentImage();
            model.setFirstMove(false);
        }


        g1.setClip(mys);
        g1.drawImage(copyShape, dx, dy, view.getMainPanel());
        if (polBorderDraw) {
            g1.setClip(0, 0, view.getMainImage().getWidth(), view.getMainImage().getHeight());
            g1.setColor(Color.BLUE);
            if (model.isCutting()) {
                g1.setColor(Color.BLACK);
            }
            ((Graphics2D)g1).setStroke(DOTTED_LINE);
            g1.drawPolygon(mys);
        }
    }
}
