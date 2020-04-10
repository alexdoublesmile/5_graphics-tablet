package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaintModel {

    private String fileName;
    private int drawMode;
    private int  xPad;
    private int  xf;
    private int  yf;
    private int  yPad;
    private boolean mouseIsPressed;
    private boolean loading;

    private List<BufferedImage> savedActions;
    private int actionsCounter;
    private boolean returned;
    private BufferedImage previousImage;

    public PaintModel() {
        savedActions = new ArrayList<>(1000);
    }

    public void saveImage(BufferedImage action) {
        if (returned) {
            BufferedImage changedImage = savedActions.get(actionsCounter - 1);
            changedImage = previousImage;
            returned = false;
        }
        savedActions.add(getNewImage(action));
        actionsCounter++;
    }

    public BufferedImage getPreviousAction() {
        if (actionsCounter > 1) {
            actionsCounter--;
        }
        returned = true;
        List<BufferedImage> tempList = savedActions.stream()
                .limit(actionsCounter)
                .collect(Collectors.toList());
        savedActions = tempList;

        previousImage = getNewImage(savedActions.get(actionsCounter - 1));
        return previousImage;
    }

    public BufferedImage getNextAction() {
        if (actionsCounter < savedActions.size()) {
            actionsCounter++;
        }
        return savedActions.get(actionsCounter - 1);
    }

    private BufferedImage getNewImage(BufferedImage oldImage) {
        BufferedImage newImage = new BufferedImage(
                oldImage.getColorModel(),
                oldImage.copyData(null),
                oldImage.isAlphaPremultiplied(),
                null
        );
        return newImage;
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
