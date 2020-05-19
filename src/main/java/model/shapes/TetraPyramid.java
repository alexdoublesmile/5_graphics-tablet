package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class TetraPyramid extends Shape {

    private Point frontLeft;
    private Point frontRight;
    private Point backLeft;
    private Point backRight;
    private Point bottomCenter;

    private ArrayList<Point> allPoints;

    public TetraPyramid() {
        allPoints = new ArrayList<>();
        frontLeft = new Point(0, 0);
        frontRight = new Point(0, 0);
        backLeft = new Point(0, 0);
        backRight = new Point(0, 0);
        bottomCenter = new Point(0, 0);
        allPoints.add(frontLeft);
        allPoints.add(frontRight);
        allPoints.add(backLeft);
        allPoints.add(backRight);
        allPoints.add(bottomCenter);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(Point frontLeft) {
        this.frontLeft = frontLeft;
    }

    public Point getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(Point frontRight) {
        this.frontRight = frontRight;
    }

    public Point getBackLeft() {
        return backLeft;
    }

    public void setBackLeft(Point backLeft) {
        this.backLeft = backLeft;
    }

    public Point getBackRight() {
        return backRight;
    }

    public void setBackRight(Point backRight) {
        this.backRight = backRight;
    }

    public Point getBottomCenter() {
        return bottomCenter;
    }

    public void setBottomCenter(Point bottomCenter) {
        this.bottomCenter = bottomCenter;
    }
}
