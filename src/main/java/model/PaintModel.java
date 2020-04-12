package model;

import config.Config;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintModel {

    private String fileName;
    private DrawMode drawMode;
    private int  xPad;
    private int  xf;
    private int  yf;
    private int  yPad;
//    private boolean mouseIsPressed;
    private boolean loading;
    private int eraserStroke;
    private int ragStroke;
    private Point triPyramidLeftPoint;
    private Point triPyramidRightPoint;
    private Point tetraPyramidLeftPoint;
    private Point tetraPyramidBackPoint;
    private Point tetraPyramidRightPoint;
    private Point triPrismLeftTopPoint;
    private Point triPrismLeftBottomPoint;
    private Point triPrismRightTopPoint;
    private Point triPrismRightBottomPoint;
    private Point ovalCenterPoint;


    private UndoRedoService undoService;

    public PaintModel() {

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

        ovalCenterPoint = new Point(0, 0);


    }

    public boolean wasIterated() {
        return undoService.isWasIterated();
    }

    public  void resetIteratedFlag() {
        undoService.setWasIterated(false);
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

    public int getxPad() {
        return xPad;
    }

    public void setxPad(int xPad) {
        this.xPad = xPad;
    }

    public int getXf() {
        return xf;
    }

    public void setXf(int xf) {
        this.xf = xf;
    }

    public int getYf() {
        return yf;
    }

    public void setYf(int yf) {
        this.yf = yf;
    }

    public int getyPad() {
        return yPad;
    }

    public void setyPad(int yPad) {
        this.yPad = yPad;
    }
//
//    public boolean isMouseIsPressed() {
//        return mouseIsPressed;
//    }
//
//    public void setMouseIsPressed(boolean mouseIsPressed) {
//        this.mouseIsPressed = mouseIsPressed;
//    }

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

    public Point getOvalCenterPoint() {
        return ovalCenterPoint;
    }

    public void setOvalCenterPoint(Point ovalCenterPoint) {
        this.ovalCenterPoint = ovalCenterPoint;
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
