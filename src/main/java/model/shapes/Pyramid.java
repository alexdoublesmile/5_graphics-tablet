package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Pyramid extends Shape {

    private Point triPyramidFront;
    private Point triPyramidLeft;
    private Point triPyramidRight;

    private ArrayList<Point> allPoints;

    public Pyramid() {
        allPoints = new ArrayList<>();
        triPyramidFront = new Point(0, 0);
        triPyramidLeft = new Point(0, 0);
        triPyramidRight = new Point(0, 0);
        allPoints.add(triPyramidFront);
        allPoints.add(triPyramidLeft);
        allPoints.add(triPyramidRight);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getTriPyramidFront() {
        return triPyramidFront;
    }

    public void setTriPyramidFront(Point triPyramidFront) {
        this.triPyramidFront = triPyramidFront;
    }

    public Point getTriPyramidLeft() {
        return triPyramidLeft;
    }

    public void setTriPyramidLeft(Point triPyramidLeft) {
        this.triPyramidLeft = triPyramidLeft;
    }

    public Point getTriPyramidRight() {
        return triPyramidRight;
    }

    public void setTriPyramidRight(Point triPyramidRight) {
        this.triPyramidRight = triPyramidRight;
    }
}
