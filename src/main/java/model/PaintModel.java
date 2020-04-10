package model;

import java.awt.image.BufferedImage;

public class PaintModel {

    private String fileName;
    private int drawMode;
    private int  xPad;
    private int  xf;
    private int  yf;
    private int  yPad;
    private boolean mouseIsPressed;
    private boolean loading;

    private UndoRedoService undoService;

    public PaintModel() {
        undoService = new UndoRedoService();
    }

    public  void saveImage(BufferedImage action) {
        undoService.saveImage(action);
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

    public int getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(int drawMode) {
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

    public boolean isMouseIsPressed() {
        return mouseIsPressed;
    }

    public void setMouseIsPressed(boolean mouseIsPressed) {
        this.mouseIsPressed = mouseIsPressed;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
