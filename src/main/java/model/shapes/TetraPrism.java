package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class TetraPrism extends Shape {

    private Point frontLeftTop;
    private Point frontRightTop;
    private Point frontLeftBottom;
    private Point frontRightBottom;
    private Point backLeftTop;
    private Point backRightTop;
    private Point backLeftBottom;
    private Point backRightBottom;
    private ArrayList<Point> allPoints;

    public TetraPrism() {
        allPoints = new ArrayList<>();
        frontLeftTop = new Point(0, 0);
        frontRightTop = new Point(0, 0);
        frontLeftBottom = new Point(0, 0);
        frontRightBottom = new Point(0, 0);
        backLeftTop = new Point(0, 0);
        backRightTop = new Point(0, 0);
        backLeftBottom = new Point(0, 0);
        backRightBottom = new Point(0, 0);
        allPoints.add(frontLeftTop);
        allPoints.add(frontRightTop);
        allPoints.add(frontLeftBottom);
        allPoints.add(frontRightBottom);
        allPoints.add(backLeftTop);
        allPoints.add(backRightTop);
        allPoints.add(backLeftBottom);
        allPoints.add(backRightBottom);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getFrontLeftTop() {
        return frontLeftTop;
    }

    public void setFrontLeftTop(Point frontLeftTop) {
        this.frontLeftTop = frontLeftTop;
    }

    public Point getFrontRightTop() {
        return frontRightTop;
    }

    public void setFrontRightTop(Point frontRightTop) {
        this.frontRightTop = frontRightTop;
    }

    public Point getFrontLeftBottom() {
        return frontLeftBottom;
    }

    public void setFrontLeftBottom(Point frontLeftBottom) {
        this.frontLeftBottom = frontLeftBottom;
    }

    public Point getFrontRightBottom() {
        return frontRightBottom;
    }

    public void setFrontRightBottom(Point frontRightBottom) {
        this.frontRightBottom = frontRightBottom;
    }

    public Point getBackLeftTop() {
        return backLeftTop;
    }

    public void setBackLeftTop(Point backLeftTop) {
        this.backLeftTop = backLeftTop;
    }

    public Point getBackRightTop() {
        return backRightTop;
    }

    public void setBackRightTop(Point backRightTop) {
        this.backRightTop = backRightTop;
    }

    public Point getBackLeftBottom() {
        return backLeftBottom;
    }

    public void setBackLeftBottom(Point backLeftBottom) {
        this.backLeftBottom = backLeftBottom;
    }

    public Point getBackRightBottom() {
        return backRightBottom;
    }

    public void setBackRightBottom(Point backRightBottom) {
        this.backRightBottom = backRightBottom;
    }
}
