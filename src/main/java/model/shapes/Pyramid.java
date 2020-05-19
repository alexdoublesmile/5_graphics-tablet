package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Pyramid extends Shape {

    private Point front;
    private Point left;
    private Point right;

    private ArrayList<Point> allPoints;

    public Pyramid() {
        allPoints = new ArrayList<>();
        front = new Point(0, 0);
        left = new Point(0, 0);
        right = new Point(0, 0);
        allPoints.add(front);
        allPoints.add(left);
        allPoints.add(right);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getFront() {
        return front;
    }

    public void setFront(Point front) {
        this.front = front;
    }

    public Point getLeft() {
        return left;
    }

    public void setLeft(Point left) {
        this.left = left;
    }

    public Point getRight() {
        return right;
    }

    public void setRight(Point right) {
        this.right = right;
    }
}
