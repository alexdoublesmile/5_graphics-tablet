package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Line extends Shape {
    private ArrayList<Point> allPoints;

    public Line() {
        allPoints = new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }
}
