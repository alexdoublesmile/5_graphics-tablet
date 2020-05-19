package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Ellipse extends Shape {
    private Point center;
    private ArrayList<Point> allPoints;

    public Ellipse() {
        allPoints = new ArrayList<>();
        center = new Point(0, 0);
        allPoints.add(center);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }
}
