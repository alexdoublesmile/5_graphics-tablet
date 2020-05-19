package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Parallelogram extends Shape {

    private Point parallelogramLeftBottom;
    private Point parallelogramRightTop;
    private ArrayList<Point> allPoints;

    public Parallelogram() {
        allPoints = new ArrayList<>();
        parallelogramLeftBottom = new Point(0, 0);
        parallelogramRightTop = new Point(0, 0);
        allPoints.add(parallelogramLeftBottom);
        allPoints.add(parallelogramRightTop);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getParallelogramLeftBottom() {
        return parallelogramLeftBottom;
    }

    public void setParallelogramLeftBottom(Point parallelogramLeftBottom) {
        this.parallelogramLeftBottom = parallelogramLeftBottom;
    }

    public Point getParallelogramRightTop() {
        return parallelogramRightTop;
    }

    public void setParallelogramRightTop(Point parallelogramRightTop) {
        this.parallelogramRightTop = parallelogramRightTop;
    }
}
