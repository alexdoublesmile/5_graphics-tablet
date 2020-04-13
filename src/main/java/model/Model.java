package model;

import config.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Model {

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

    private Point triPyramidLeftPoint;
    private Point triPyramidRightPoint;
    private Point tetraPyramidLeftPoint;
    private Point tetraPyramidBackPoint;
    private Point tetraPyramidRightPoint;
    private Point triPrismLeftTopPoint;
    private Point triPrismLeftBottomPoint;
    private Point triPrismRightTopPoint;
    private Point triPrismRightBottomPoint;
    private Point circleCenter;

    public Model() {
        undoService = new UndoRedoService();

        drawMode = DrawMode.PENCIL;
        eraserStroke = Integer.valueOf(Config.getProperty(Config.ERASER_BASIC_STROKE));
        ragStroke = Integer.valueOf(Config.getProperty(Config.RAG_BASIC_STROKE));
        triPyramidLeftPoint = new Point(0, 0);
        triPyramidRightPoint = new Point(0, 0);
        tetraPyramidLeftPoint = new Point(0, 0);
        tetraPyramidBackPoint = new Point(0, 0);
        tetraPyramidRightPoint = new Point(0, 0);
        triPrismLeftTopPoint = new Point(0, 0);
        triPrismLeftBottomPoint = new Point(0, 0);
        triPrismRightTopPoint = new Point(0, 0);
        triPrismRightBottomPoint = new Point(0, 0);

        circleCenter = new Point(0, 0);
    }

    public int getPointForCircle(int startPoint, int finalPoint) {
        int radius = Math.abs(startPoint - finalPoint) / 2;
        return (int) circleCenter.getX() - radius;
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

    public Point getTriPyramidLeftPoint() {
        return triPyramidLeftPoint;
    }

    public Point getTriPyramidRightPoint() {
        return triPyramidRightPoint;
    }

    public Point getTetraPyramidLeftPoint() {
        return tetraPyramidLeftPoint;
    }

    public Point getTetraPyramidBackPoint() {
        return tetraPyramidBackPoint;
    }

    public Point getTetraPyramidRightPoint() {
        return tetraPyramidRightPoint;
    }

    public Point getCircleCenter() {
        return circleCenter;
    }

    public void setCircleCenter(Point circleCenter) {
        this.circleCenter = circleCenter;
    }

    public Point getTriPrismLeftTopPoint() {
        return triPrismLeftTopPoint;
    }

    public Point getTriPrismLeftBottomPoint() {
        return triPrismLeftBottomPoint;
    }

    public Point getTriPrismRightTopPoint() {
        return triPrismRightTopPoint;
    }

    public Point getTriPrismRightBottomPoint() {
        return triPrismRightBottomPoint;
    }

}
