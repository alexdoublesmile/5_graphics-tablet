package model;

import config.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Model {

    public static final double PARALLELOGRAM_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELOGRAM_FACTOR));

    public static final double PYRAMID_MAIN_FACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_MAIN_LINE_FACTOR));
    public static final double PYRAMID_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_LEFT_XFACTOR));
    public static final double PYRAMID_LEFT_YFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_LEFT_YFACTOR));
    public static final double PYRAMID_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_RIGHT_XFACTOR));
    public static final double PYRAMID_RIGHT_YFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_RIGHT_YFACTOR));

    public static final double PYRAMID_BACK_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_LEFT_XFACTOR));
    public static final double PYRAMID_BACK_LEFT_YFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_LEFT_YFACTOR));
    public static final double PYRAMID_BACK_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_RIGHT_XFACTOR));
    public static final double PYRAMID_BACK_RIGHT_YFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_RIGHT_YFACTOR));
    public static final double PYRAMID_FRONT_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_FRONT_LEFT_XFACTOR));
    public static final double PYRAMID_FRONT_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_FRONT_RIGHT_XFACTOR));

    public static final double PYRAMID_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CUSTOM_PYRAMID_GROW_FACTOR));
    public static final int PYRAMID_TOP_PADDING = (int) Float.parseFloat(Config.getProperty(Config.CUSTOM_PYRAMID_TOP_PADDING));

    public static final double PRISM_LEFT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_XFACTOR));
    public static final double PRISM_LEFT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_YFACTOR));
    public static final double PRISM_RIGHT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_XFACTOR));
    public static final double PRISM_RIGHT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_YFACTOR));
    public static final double PRISM_LEFT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_XFACTOR));
    public static final double PRISM_LEFT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_YFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_XFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_YFACTOR));

    public static final double PARALLELEPIPED_SIDE_ANGLE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_SIDE_ANGLE_FACTOR));
    public static final double PARALLELEPIPED_FRONT_ANGLE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_ANGLE_FACTOR));
    public static final double PARALLELEPIPED_CURVE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_CURVE_FACTOR));

    public static final double PRISM_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CUSTOM_PRISM_GROW_FACTOR));

    public static final double CONE_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_GROW_FACTOR));
    public static final int CONE_DOTTED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_DOTTED_ARC_START_ANGLE));
    public static final int CONE_DOTTED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_DOTTED_ARC_FINAL_ANGLE));
    public static final int CONE_LINED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_LINED_ARC_START_ANGLE));
    public static final int CONE_LINED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_LINED_ARC_FINAL_ANGLE));
    public static final double CONE_LEFT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_LEFT_BUG_FACTOR));
    public static final double CONE_RIGHT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_RIGHT_BUG_FACTOR));

    public static final double CYLINDER_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_GROW_FACTOR));
    public static final int CYLINDER_DOTTED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_DOTTED_ARC_START_ANGLE));
    public static final int CYLINDER_DOTTED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_DOTTED_ARC_FINAL_ANGLE));
    public static final int CYLINDER_LINED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_LINED_ARC_START_ANGLE));
    public static final int CYLINDER_LINED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_LINED_ARC_FINAL_ANGLE));
    public static final double CYLINDER_TOP_LEFT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_TOP_LEFT_BUG_FACTOR));
    public static final double CYLINDER_TOP_RIGHT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_TOP_RIGHT_BUG_FACTOR));
    public static final double CYLINDER_RIGHT_LINE_BUG_CONSTANT = Float.parseFloat(Config.getProperty(Config.CYLINDER_RIGHT_LINE_BUG_CONSTANT));

    public static final int POLYGON_ACCURACY_FACTOR = (int) Float.parseFloat(Config.getProperty(Config.POLYGON_ACCURACY_FACTOR));


    public static final int INDICATOR_WIDTH = Integer.parseInt(Config.getProperty(Config.INDICATOR_WIDTH));
    public static final int INDICATOR_TOP_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_TOP_OFFSET));
    public static final int INDICATOR_BOTTOM_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_BOTTOM_OFFSET));


    private ArrayList<DrawMode> figureModeList;
    private ArrayList<DrawMode> customModeList;

    {
        figureModeList = new ArrayList<>();
        figureModeList.add(DrawMode.LINE);
        figureModeList.add(DrawMode.DOTTEDLINE);
        figureModeList.add(DrawMode.CIRCLE);
        figureModeList.add(DrawMode.ELLIPSE);
        figureModeList.add(DrawMode.RECT);
        figureModeList.add(DrawMode.PARALLELOGRAM);
        figureModeList.add(DrawMode.POLYGON);
        figureModeList.add(DrawMode.PYRAMID);
        figureModeList.add(DrawMode.PYRAMID_TETRA);
        figureModeList.add(DrawMode.PYRAMID_CUSTOM);
        figureModeList.add(DrawMode.PRISM);
        figureModeList.add(DrawMode.PARALLELEPIPED);
        figureModeList.add(DrawMode.PRISM_CUSTOM);
        figureModeList.add(DrawMode.CONE);
        figureModeList.add(DrawMode.CYLINDER);
        figureModeList.add(DrawMode.SPHERE);

        customModeList = new ArrayList<>();
        customModeList.add(DrawMode.POLYGON);
        customModeList.add(DrawMode.PYRAMID_CUSTOM);
        customModeList.add(DrawMode.PRISM_CUSTOM);

        allPoints = new ArrayList<>();
    }

    private UndoRedoService undoService;
    private DrawMode drawMode;
    private int startX;
    private int startY;
    private int finalX;
    private int finalY;

    private int eraserStroke;
    private int ragStroke;
    private String fileName;
    private boolean loading;

    private Point triPyramidFront;
    private Point triPyramidLeft;
    private Point triPyramidRight;
    private Point tetraPyramidFrontLeft;
    private Point tetraPyramidFrontRight;
    private Point tetraPyramidBackLeft;
    private Point tetraPyramidBackRight;
    private Point tetraPyramidBottomCenter;
    private Point triPrismLeftTop;
    private Point triPrismLeftBottom;
    private Point triPrismRightTop;
    private Point triPrismRightBottom;
    private Point parallelepipedFrontLeftTop;
    private Point parallelepipedFrontRightTop;
    private Point parallelepipedFrontLeftBottom;
    private Point parallelepipedFrontRightBottom;
    private Point parallelepipedBackLeftTop;
    private Point parallelepipedBackRightTop;
    private Point parallelepipedBackLeftBottom;
    private Point parallelepipedBackRightBottom;
    private Point parallelogramLeftBottom;
    private Point parallelogramRightTop;
    private Point circleCenter;
    private Point ellipseCenter;
    private Point arrowLeftPoint;
    private Point arrowRightPoint;
    private ArrayList<Point> allPoints;
    private ArrayList<Point> pointList;
    private ArrayList<Point> prismTopPointList;
    private Polygon polygon;
    private boolean figureMode;
    private boolean customMode;
    private boolean polygonInWork;

    public Model() {
        undoService = new UndoRedoService();

        drawMode = DrawMode.PENCIL;
        eraserStroke = Integer.valueOf(Config.getProperty(Config.ERASER_BASIC_STROKE));
        ragStroke = Integer.valueOf(Config.getProperty(Config.RAG_BASIC_STROKE));
        triPyramidFront = new Point(0, 0);
        triPyramidLeft = new Point(0, 0);
        triPyramidRight = new Point(0, 0);
        tetraPyramidFrontLeft = new Point(0, 0);
        tetraPyramidBackLeft = new Point(0, 0);
        tetraPyramidBackRight = new Point(0, 0);
        tetraPyramidFrontRight = new Point(0, 0);
        tetraPyramidBottomCenter = new Point(0, 0);
        triPrismLeftTop = new Point(0, 0);
        triPrismLeftBottom = new Point(0, 0);
        triPrismRightTop = new Point(0, 0);
        triPrismRightBottom = new Point(0, 0);
        parallelepipedFrontLeftTop = new Point(0, 0);
        parallelepipedFrontRightTop = new Point(0, 0);
        parallelepipedFrontLeftBottom = new Point(0, 0);
        parallelepipedFrontRightBottom = new Point(0, 0);
        parallelepipedBackLeftTop = new Point(0, 0);
        parallelepipedBackRightTop = new Point(0, 0);
        parallelepipedBackLeftBottom = new Point(0, 0);
        parallelepipedBackRightBottom = new Point(0, 0);
        parallelogramLeftBottom = new Point(0, 0);
        parallelogramRightTop = new Point(0, 0);

        circleCenter = new Point(0, 0);
        circleCenter = new Point(0, 0);
        arrowLeftPoint = new Point(0, 0);
        arrowRightPoint = new Point(0, 0);

        pointList = new ArrayList<>();
        prismTopPointList = new ArrayList<>();
        polygon = new Polygon();
    }

    public int getEllipseBigRadius() {
        return Math.abs(startX - finalX) / 2;
    }


    public int getEllipseSmallRadius() {
        return Math.abs(startY - finalY) / 2;
    }

    public Point getRectCenter() {
        return new Point(
                startX + Math.abs(startX - finalX) / 2,
                startY + Math.abs(startY - finalY) / 2);
    }

    public double getDrawLine() {
        return Math.sqrt(
                Math.pow(startX - finalX, 2.0) +
                        Math.pow(startY - finalY, 2.0)
        );
    }

    public void resetAllPoints() {
        triPyramidFront = new Point(finalX, finalY);
        triPyramidLeft = new Point(finalX, finalY);
        triPyramidRight = new Point(finalX, finalY);
        tetraPyramidFrontLeft = new Point(finalX, finalY);
        tetraPyramidBackLeft = new Point(finalX, finalY);
        tetraPyramidBackRight = new Point(finalX, finalY);
        tetraPyramidFrontRight = new Point(finalX, finalY);
        tetraPyramidBottomCenter = new Point(finalX, finalY);
        triPrismLeftTop = new Point(finalX, finalY);
        triPrismLeftBottom = new Point(finalX, finalY);
        triPrismRightTop = new Point(finalX, finalY);
        triPrismRightBottom = new Point(finalX, finalY);
        parallelepipedFrontLeftTop = new Point(finalX, finalY);
        parallelepipedFrontRightTop = new Point(finalX, finalY);
        parallelepipedFrontLeftBottom = new Point(finalX, finalY);
        parallelepipedFrontRightBottom = new Point(finalX, finalY);
        parallelepipedBackLeftBottom = new Point(finalX, finalY);
        parallelepipedBackLeftTop = new Point(finalX, finalY);
        parallelepipedBackRightTop = new Point(finalX, finalY);
        parallelepipedBackRightBottom = new Point(finalX, finalY);
        parallelogramLeftBottom = new Point(finalX, finalY);
        parallelogramRightTop = new Point(finalX, finalY);

        circleCenter = new Point(finalX, finalY);
        circleCenter = new Point(finalX, finalY);
    }

    public boolean wasIterated() {
        return undoService.isWasIterated();
    }

    public  void saveAction(BufferedImage action) {
        undoService.saveAction(action);
    }
    public  void reSaveAction(BufferedImage action) {
        undoService.reSaveAction(action);
    }
    public  void removeLastAction() {
        undoService.removeLastAction();
    }

    public BufferedImage getPreviousAction() {
        resetAllCustomPoints();
        return undoService.getPreviousAction();
    }

    public BufferedImage getNextAction() {
        return undoService.getNextAction();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DrawMode getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public int getEraserStroke() {
        return eraserStroke;
    }

    public void setEraserStroke(int eraserStroke) {
        this.eraserStroke = eraserStroke;
    }

    public int getRagStroke() {
        return ragStroke;
    }

    public void setRagStroke(int ragStroke) {
        this.ragStroke = ragStroke;
    }

    public Point getTriPyramidLeft() {
        return triPyramidLeft;
    }

    public Point getTriPyramidRight() {
        return triPyramidRight;
    }

    public Point getTetraPyramidFrontLeft() {
        return tetraPyramidFrontLeft;
    }

    public Point getTetraPyramidBackLeft() {
        return tetraPyramidBackLeft;
    }

    public Point getTetraPyramidBackRight() {
        return tetraPyramidBackRight;
    }

    public Point getCircleCenter() {
        return circleCenter;
    }

    public void setCircleCenter(Point circleCenter) {
        this.circleCenter = circleCenter;
    }

    public Point getTriPrismLeftTop() {
        return triPrismLeftTop;
    }

    public Point getTriPrismLeftBottom() {
        return triPrismLeftBottom;
    }

    public Point getTriPrismRightTop() {
        return triPrismRightTop;
    }

    public Point getTriPrismRightBottom() {
        return triPrismRightBottom;
    }

    public Point getTetraPyramidFrontRight() {
        return tetraPyramidFrontRight;
    }

    public Point getParallelepipedFrontLeftTop() {
        return parallelepipedFrontLeftTop;
    }

    public Point getParallelepipedFrontRightTop() {
        return parallelepipedFrontRightTop;
    }

    public Point getParallelepipedFrontLeftBottom() {
        return parallelepipedFrontLeftBottom;
    }

    public Point getParallelepipedBackLeftBottom() {
        return parallelepipedBackLeftBottom;
    }

    public Point getParallelepipedBackRightTop() {
        return parallelepipedBackRightTop;
    }

    public Point getParallelepipedBackRightBottom() {
        return parallelepipedBackRightBottom;
    }

    public Point getParallelogramLeftBottom() {
        return parallelogramLeftBottom;
    }

    public Point getParallelogramRightTop() {
        return parallelogramRightTop;
    }

    public Point getEllipseCenter() {
        return ellipseCenter;
    }

    public void setTriPyramidLeft(Point triPyramidLeft) {
        this.triPyramidLeft = triPyramidLeft;
    }

    public void setTriPyramidRight(Point triPyramidRight) {
        this.triPyramidRight = triPyramidRight;
    }

    public void setTetraPyramidFrontLeft(Point tetraPyramidFrontLeft) {
        this.tetraPyramidFrontLeft = tetraPyramidFrontLeft;
    }

    public void setTetraPyramidFrontRight(Point tetraPyramidFrontRight) {
        this.tetraPyramidFrontRight = tetraPyramidFrontRight;
    }

    public void setTetraPyramidBackLeft(Point tetraPyramidBackLeft) {
        this.tetraPyramidBackLeft = tetraPyramidBackLeft;
    }

    public void setTetraPyramidBackRight(Point tetraPyramidBackRight) {
        this.tetraPyramidBackRight = tetraPyramidBackRight;
    }

    public void setTriPrismLeftTop(Point triPrismLeftTop) {
        this.triPrismLeftTop = triPrismLeftTop;
    }

    public void setTriPrismLeftBottom(Point triPrismLeftBottom) {
        this.triPrismLeftBottom = triPrismLeftBottom;
    }

    public void setTriPrismRightTop(Point triPrismRightTop) {
        this.triPrismRightTop = triPrismRightTop;
    }

    public void setTriPrismRightBottom(Point triPrismRightBottom) {
        this.triPrismRightBottom = triPrismRightBottom;
    }

    public void setParallelepipedFrontLeftTop(Point parallelepipedFrontLeftTop) {
        this.parallelepipedFrontLeftTop = parallelepipedFrontLeftTop;
    }

    public void setParallelepipedFrontRightTop(Point parallelepipedFrontRightTop) {
        this.parallelepipedFrontRightTop = parallelepipedFrontRightTop;
    }

    public void setParallelepipedFrontLeftBottom(Point parallelepipedFrontLeftBottom) {
        this.parallelepipedFrontLeftBottom = parallelepipedFrontLeftBottom;
    }

    public void setParallelepipedBackLeftBottom(Point parallelepipedBackLeftBottom) {
        this.parallelepipedBackLeftBottom = parallelepipedBackLeftBottom;
    }

    public void setParallelepipedBackRightTop(Point parallelepipedBackRightTop) {
        this.parallelepipedBackRightTop = parallelepipedBackRightTop;
    }

    public void setParallelepipedBackRightBottom(Point parallelepipedBackRightBottom) {
        this.parallelepipedBackRightBottom = parallelepipedBackRightBottom;
    }

    public void setParallelogramLeftBottom(Point parallelogramLeftBottom) {
        this.parallelogramLeftBottom = parallelogramLeftBottom;
    }

    public void setParallelogramRightTop(Point parallelogramRightTop) {
        this.parallelogramRightTop = parallelogramRightTop;
    }

    public void setEllipseCenter(Point ellipseCenter) {
        this.ellipseCenter = ellipseCenter;
    }

    public Point getTriPyramidFront() {
        return triPyramidFront;
    }

    public void setTriPyramidFront(Point triPyramidFront) {
        this.triPyramidFront = triPyramidFront;
    }

    public Point getTetraPyramidBottomCenter() {
        return tetraPyramidBottomCenter;
    }

    public void setTetraPyramidBottomCenter(Point tetraPyramidBottomCenter) {
        this.tetraPyramidBottomCenter = tetraPyramidBottomCenter;
    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public ArrayList<Point> getPrismTopPointList() {
        return prismTopPointList;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void resetAllCustomPoints() {
        this.polygon = new Polygon();
        this.pointList = new ArrayList<>();
        this.prismTopPointList = new ArrayList<>();

    }

    public void fillPolygon() {
        int[] xPoints = new int[pointList.size()];
        int[] yPoints = new int[pointList.size()];

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = pointList.get(i).x;
        }
        for (int i = 0; i < yPoints.length; i++) {
            yPoints[i] = pointList.get(i).y;
        }

        polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

    public Point getArrowLeftPoint() {
        return arrowLeftPoint;
    }

    public void setArrowLeftPoint(Point arrowLeftPoint) {
        this.arrowLeftPoint = arrowLeftPoint;
    }

    public Point getArrowRightPoint() {
        return arrowRightPoint;
    }

    public void setArrowRightPoint(Point arrowRightPoint) {
        this.arrowRightPoint = arrowRightPoint;
    }

    public boolean isFigureMode() {
        return figureMode;
    }

    public void setFigureMode(boolean figureMode) {
        this.figureMode = figureMode;
    }

    public ArrayList<DrawMode> getFigureModeList() {
        return figureModeList;
    }

    public ArrayList<DrawMode> getCustomModeList() {
        return customModeList;
    }

    public boolean isCustomMode() {
        return customMode;
    }

    public void setCustomMode(boolean customMode) {
        this.customMode = customMode;
    }

    public boolean isPolygonInWork() {
        return polygonInWork;
    }

    public void setPolygonInWork(boolean polygonInWork) {
        this.polygonInWork = polygonInWork;
    }

    public Point getParallelepipedFrontRightBottom() {
        return parallelepipedFrontRightBottom;
    }

    public Point getParallelepipedBackLeftTop() {
        return parallelepipedBackLeftTop;
    }
}
