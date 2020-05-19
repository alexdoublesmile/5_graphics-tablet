package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Arrow extends Shape {

    private Point arrowLeftPoint;
    private Point arrowRightPoint;

    private ArrayList<Point> allPoints;

    public Arrow() {
        allPoints = new ArrayList<>();
        arrowLeftPoint = new Point(0, 0);
        arrowRightPoint = new Point(0, 0);
        allPoints.add(arrowLeftPoint);
        allPoints.add(arrowRightPoint);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getArrowLeftPoint() {
        return arrowLeftPoint;
    }

    public void setArrowLeftPoint(Point arrowLeftPoint) {
        this.arrowLeftPoint = arrowLeftPoint;
    }

    public Point getArrowRightPoint() {
        return arrowRightPoint;
    }

    public void setArrowRightPoint(Point arrowRightPoint) {
        this.arrowRightPoint = arrowRightPoint;
    }

}
