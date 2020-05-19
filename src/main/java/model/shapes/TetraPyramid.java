package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class TetraPyramid extends Shape {

    private Point tetraPyramidFrontLeft;
    private Point tetraPyramidFrontRight;
    private Point tetraPyramidBackLeft;
    private Point tetraPyramidBackRight;
    private Point tetraPyramidBottomCenter;

    private ArrayList<Point> allPoints;

    public TetraPyramid() {
        allPoints = new ArrayList<>();
        tetraPyramidFrontLeft = new Point(0, 0);
        tetraPyramidFrontRight = new Point(0, 0);
        tetraPyramidBackLeft = new Point(0, 0);
        tetraPyramidBackRight = new Point(0, 0);
        tetraPyramidBottomCenter = new Point(0, 0);
        allPoints.add(tetraPyramidFrontLeft);
        allPoints.add(tetraPyramidFrontRight);
        allPoints.add(tetraPyramidBackLeft);
        allPoints.add(tetraPyramidBackRight);
        allPoints.add(tetraPyramidBottomCenter);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getTetraPyramidFrontLeft() {
        return tetraPyramidFrontLeft;
    }

    public void setTetraPyramidFrontLeft(Point tetraPyramidFrontLeft) {
        this.tetraPyramidFrontLeft = tetraPyramidFrontLeft;
    }

    public Point getTetraPyramidFrontRight() {
        return tetraPyramidFrontRight;
    }

    public void setTetraPyramidFrontRight(Point tetraPyramidFrontRight) {
        this.tetraPyramidFrontRight = tetraPyramidFrontRight;
    }

    public Point getTetraPyramidBackLeft() {
        return tetraPyramidBackLeft;
    }

    public void setTetraPyramidBackLeft(Point tetraPyramidBackLeft) {
        this.tetraPyramidBackLeft = tetraPyramidBackLeft;
    }

    public Point getTetraPyramidBackRight() {
        return tetraPyramidBackRight;
    }

    public void setTetraPyramidBackRight(Point tetraPyramidBackRight) {
        this.tetraPyramidBackRight = tetraPyramidBackRight;
    }

    public Point getTetraPyramidBottomCenter() {
        return tetraPyramidBottomCenter;
    }

    public void setTetraPyramidBottomCenter(Point tetraPyramidBottomCenter) {
        this.tetraPyramidBottomCenter = tetraPyramidBottomCenter;
    }
}
