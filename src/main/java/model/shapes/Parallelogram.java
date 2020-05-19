package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Parallelogram extends Shape {

    private Point leftBottom;
    private Point rightTop;
    private ArrayList<Point> allPoints;

    public Parallelogram() {
        allPoints = new ArrayList<>();
        leftBottom = new Point(0, 0);
        rightTop = new Point(0, 0);
        allPoints.add(leftBottom);
        allPoints.add(rightTop);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
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
}
