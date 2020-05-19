package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Prism extends Shape {

    private Point triPrismLeftTop;
    private Point triPrismLeftBottom;
    private Point triPrismRightTop;
    private Point triPrismRightBottom;
    private ArrayList<Point> allPoints;

    public Prism() {
        allPoints = new ArrayList<>();
        triPrismLeftBottom = new Point(0, 0);
        triPrismLeftTop = new Point(0, 0);
        triPrismRightBottom = new Point(0, 0);
        triPrismRightTop = new Point(0, 0);
        allPoints.add(triPrismLeftBottom);
        allPoints.add(triPrismLeftTop);
        allPoints.add(triPrismRightBottom);
        allPoints.add(triPrismRightTop);
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }

    public Point getTriPrismLeftTop() {
        return triPrismLeftTop;
    }

    public void setTriPrismLeftTop(Point triPrismLeftTop) {
        this.triPrismLeftTop = triPrismLeftTop;
    }

    public Point getTriPrismLeftBottom() {
        return triPrismLeftBottom;
    }

    public void setTriPrismLeftBottom(Point triPrismLeftBottom) {
        this.triPrismLeftBottom = triPrismLeftBottom;
    }

    public Point getTriPrismRightTop() {
        return triPrismRightTop;
    }

    public void setTriPrismRightTop(Point triPrismRightTop) {
        this.triPrismRightTop = triPrismRightTop;
    }

    public Point getTriPrismRightBottom() {
        return triPrismRightBottom;
    }

    public void setTriPrismRightBottom(Point triPrismRightBottom) {
        this.triPrismRightBottom = triPrismRightBottom;
    }
}
