package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Arrow extends Shape {

    private Point leftPoint;
    private Point rightPoint;

    private ArrayList<Point> allPoints;

    public Arrow() {
        allPoints = new ArrayList<>();
        leftPoint = new Point(0, 0);
        rightPoint = new Point(0, 0);
        allPoints.add(leftPoint);
        allPoints.add(rightPoint);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getLeftPoint() {
        return leftPoint;
    }

    public void setLeftPoint(Point leftPoint) {
        this.leftPoint = leftPoint;
    }

    public Point getRightPoint() {
        return rightPoint;
    }

    public void setRightPoint(Point rightPoint) {
        this.rightPoint = rightPoint;
    }

}
