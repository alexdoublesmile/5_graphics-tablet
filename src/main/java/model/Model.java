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

    public static final double PRISM_LEFT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_XFACTOR));
    public static final double PRISM_LEFT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_YFACTOR));
    public static final double PRISM_RIGHT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_XFACTOR));
    public static final double PRISM_RIGHT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_YFACTOR));
    public static final double PRISM_LEFT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_XFACTOR));
    public static final double PRISM_LEFT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_YFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_XFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_YFACTOR));

    public static final double PARALLELEPIPED_LEFT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_LEFT_TOP_XFACTOR));
    public static final double PARALLELEPIPED_LEFT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_LEFT_TOP_YFACTOR));
    public static final double PARALLELEPIPED_RIGHT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_BACK_RIGHT_TOP_XFACTOR));
    public static final double PARALLELEPIPED_LEFT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_LEFT_BOTTOM_XFACTOR));
    public static final double PARALLELEPIPED_RIGHT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_BACK_RIGHT_BOTTOM_XFACTOR));
    public static final double PARALLELEPIPED_RIGHT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_BACK_RIGHT_BOTTOM_YFACTOR));
    public static final double PARALLELEPIPED_FRONT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_RIGHT_TOP_YFACTOR));
    public static final double PARALLELEPIPED_BACK_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_BACK_LEFT_BOTTOM_YFACTOR));

    public static final int INDICATOR_WIDTH = Integer.parseInt(Config.getProperty(Config.INDICATOR_WIDTH));
    public static final int INDICATOR_TOP_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_TOP_OFFSET));
    public static final int INDICATOR_BOTTOM_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_BOTTOM_OFFSET));

    private UndoRedoService undoService;
    private DrawMode drawMode;
    private int startXPoint;
    private int startYPoint;
    private int finalXPoint;
    private int finalYPoint;

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
    private Point parallelepipedBackLeftBottom;
    private Point parallelepipedBackRightTop;
    private Point parallelepipedBackRightBottom;
    private Point parallelogramLeftBottom;
    private Point parallelogramRightTop;
    private Point circleCenter;
    private Point ellipseCenter;
    private ArrayList<Point> pointList;
    private Polygon polygon;

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
        parallelepipedBackLeftBottom = new Point(0, 0);
        parallelepipedBackRightTop = new Point(0, 0);
        parallelepipedBackRightBottom = new Point(0, 0);
        parallelogramLeftBottom = new Point(0, 0);
        parallelogramRightTop = new Point(0, 0);

        circleCenter = new Point(0, 0);
        circleCenter = new Point(0, 0);

        pointList = new ArrayList<>();
        polygon = new Polygon();
    }

    public int getCircleRadius() {
        return Math.abs(startXPoint - finalXPoint) / 2;
    }

    public double getPyramidLineLenght() {
        return Math.sqrt(
                Math.pow(startXPoint - finalXPoint, 2.0) +
                        Math.pow(startYPoint - finalYPoint, 2.0)
        );
    }

    public double getDrawLine() {
        return Math.sqrt(
                Math.pow(startXPoint - finalXPoint, 2.0) +
                        Math.pow(startYPoint - finalYPoint, 2.0)
        );
    }

    public void resetAllPoints() {
        triPyramidFront = new Point(finalXPoint, finalYPoint);
        triPyramidLeft = new Point(finalXPoint, finalYPoint);
        triPyramidRight = new Point(finalXPoint, finalYPoint);
        tetraPyramidFrontLeft = new Point(finalXPoint, finalYPoint);
        tetraPyramidBackLeft = new Point(finalXPoint, finalYPoint);
        tetraPyramidBackRight = new Point(finalXPoint, finalYPoint);
        tetraPyramidFrontRight = new Point(finalXPoint, finalYPoint);
        tetraPyramidBottomCenter = new Point(finalXPoint, finalYPoint);
        triPrismLeftTop = new Point(finalXPoint, finalYPoint);
        triPrismLeftBottom = new Point(finalXPoint, finalYPoint);
        triPrismRightTop = new Point(finalXPoint, finalYPoint);
        triPrismRightBottom = new Point(finalXPoint, finalYPoint);
        parallelepipedFrontLeftTop = new Point(finalXPoint, finalYPoint);
        parallelepipedFrontRightTop = new Point(finalXPoint, finalYPoint);
        parallelepipedFrontLeftBottom = new Point(finalXPoint, finalYPoint);
        parallelepipedBackLeftBottom = new Point(finalXPoint, finalYPoint);
        parallelepipedBackRightTop = new Point(finalXPoint, finalYPoint);
        parallelepipedBackRightBottom = new Point(finalXPoint, finalYPoint);
        parallelogramLeftBottom = new Point(finalXPoint, finalYPoint);
        parallelogramRightTop = new Point(finalXPoint, finalYPoint);

        circleCenter = new Point(finalXPoint, finalYPoint);
        circleCenter = new Point(finalXPoint, finalYPoint);
    }

    public int getSizeForCircle(int finalPoint) {
        return (int) Math.abs(circleCenter.getX() - finalPoint);
    }

    public boolean wasIterated() {
        return undoService.isWasIterated();
    }

    public  void saveAction(BufferedImage action) {
        undoService.saveAction(action);
    }

    public BufferedImage getPreviousAction() {
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

    public int getFinalXPoint() {
        return finalXPoint;
    }

    public void setFinalXPoint(int finalXPoint) {
        this.finalXPoint = finalXPoint;
    }

    public int getStartXPoint() {
        return startXPoint;
    }

    public void setStartXPoint(int startXPoint) {
        this.startXPoint = startXPoint;
    }

    public int getStartYPoint() {
        return startYPoint;
    }

    public void setStartYPoint(int startYPoint) {
        this.startYPoint = startYPoint;
    }

    public int getFinalYPoint() {
        return finalYPoint;
    }

    public void setFinalYPoint(int finalYPoint) {
        this.finalYPoint = finalYPoint;
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

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void resetPolygon() {
        this.polygon = new Polygon();
        this.pointList = new ArrayList<>();
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
}
