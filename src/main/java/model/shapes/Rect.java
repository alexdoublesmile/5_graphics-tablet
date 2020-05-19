package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Rect extends Shape {
    private ArrayList<Point> allPoints;

    public Rect() {
        allPoints = new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }
}
