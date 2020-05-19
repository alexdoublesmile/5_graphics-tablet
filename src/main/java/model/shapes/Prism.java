package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Prism extends Shape {

    private Point leftTop;
    private Point leftBottom;
    private Point rightTop;
    private Point rightBottom;
    private ArrayList<Point> allPoints;

    public Prism() {
        allPoints = new ArrayList<>();
        leftBottom = new Point(0, 0);
        leftTop = new Point(0, 0);
        rightBottom = new Point(0, 0);
        rightTop = new Point(0, 0);
        allPoints.add(leftBottom);
        allPoints.add(leftTop);
        allPoints.add(rightBottom);
        allPoints.add(rightTop);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    public Point getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(Point rightBottom) {
        this.rightBottom = rightBottom;
    }
}
