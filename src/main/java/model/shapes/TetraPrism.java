package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class TetraPrism extends Shape {

    private Point parallelepipedFrontLeftTop;
    private Point parallelepipedFrontRightTop;
    private Point parallelepipedFrontLeftBottom;
    private Point parallelepipedFrontRightBottom;
    private Point parallelepipedBackLeftTop;
    private Point parallelepipedBackRightTop;
    private Point parallelepipedBackLeftBottom;
    private Point parallelepipedBackRightBottom;
    private ArrayList<Point> allPoints;

    public TetraPrism() {
        allPoints = new ArrayList<>();
        parallelepipedFrontLeftTop = new Point(0, 0);
        parallelepipedFrontRightTop = new Point(0, 0);
        parallelepipedFrontLeftBottom = new Point(0, 0);
        parallelepipedFrontRightBottom = new Point(0, 0);
        parallelepipedBackLeftTop = new Point(0, 0);
        parallelepipedBackRightTop = new Point(0, 0);
        parallelepipedBackLeftBottom = new Point(0, 0);
        parallelepipedBackRightBottom = new Point(0, 0);
        allPoints.add(parallelepipedFrontLeftTop);
        allPoints.add(parallelepipedFrontRightTop);
        allPoints.add(parallelepipedFrontLeftBottom);
        allPoints.add(parallelepipedFrontRightBottom);
        allPoints.add(parallelepipedBackLeftTop);
        allPoints.add(parallelepipedBackRightTop);
        allPoints.add(parallelepipedBackLeftBottom);
        allPoints.add(parallelepipedBackRightBottom);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getParallelepipedFrontLeftTop() {
        return parallelepipedFrontLeftTop;
    }

    public void setParallelepipedFrontLeftTop(Point parallelepipedFrontLeftTop) {
        this.parallelepipedFrontLeftTop = parallelepipedFrontLeftTop;
    }

    public Point getParallelepipedFrontRightTop() {
        return parallelepipedFrontRightTop;
    }

    public void setParallelepipedFrontRightTop(Point parallelepipedFrontRightTop) {
        this.parallelepipedFrontRightTop = parallelepipedFrontRightTop;
    }

    public Point getParallelepipedFrontLeftBottom() {
        return parallelepipedFrontLeftBottom;
    }

    public void setParallelepipedFrontLeftBottom(Point parallelepipedFrontLeftBottom) {
        this.parallelepipedFrontLeftBottom = parallelepipedFrontLeftBottom;
    }

    public Point getParallelepipedFrontRightBottom() {
        return parallelepipedFrontRightBottom;
    }

    public void setParallelepipedFrontRightBottom(Point parallelepipedFrontRightBottom) {
        this.parallelepipedFrontRightBottom = parallelepipedFrontRightBottom;
    }

    public Point getParallelepipedBackLeftTop() {
        return parallelepipedBackLeftTop;
    }

    public void setParallelepipedBackLeftTop(Point parallelepipedBackLeftTop) {
        this.parallelepipedBackLeftTop = parallelepipedBackLeftTop;
    }

    public Point getParallelepipedBackRightTop() {
        return parallelepipedBackRightTop;
    }

    public void setParallelepipedBackRightTop(Point parallelepipedBackRightTop) {
        this.parallelepipedBackRightTop = parallelepipedBackRightTop;
    }

    public Point getParallelepipedBackLeftBottom() {
        return parallelepipedBackLeftBottom;
    }

    public void setParallelepipedBackLeftBottom(Point parallelepipedBackLeftBottom) {
        this.parallelepipedBackLeftBottom = parallelepipedBackLeftBottom;
    }

    public Point getParallelepipedBackRightBottom() {
        return parallelepipedBackRightBottom;
    }

    public void setParallelepipedBackRightBottom(Point parallelepipedBackRightBottom) {
        this.parallelepipedBackRightBottom = parallelepipedBackRightBottom;
    }
}
