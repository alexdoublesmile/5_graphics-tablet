package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Ellipse extends Shape {
    private Point ellipseCenter;
    private ArrayList<Point> allPoints;

    public Ellipse() {
        allPoints = new ArrayList<>();
        ellipseCenter = new Point(0, 0);
        allPoints.add(ellipseCenter);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getEllipseCenter() {
        return ellipseCenter;
    }

    public void setEllipseCenter(Point ellipseCenter) {
        this.ellipseCenter = ellipseCenter;
    }
}
