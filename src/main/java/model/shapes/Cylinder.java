package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Cylinder extends Shape {
    private ArrayList<Point> allPoints;

    public Cylinder() {
        allPoints = new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }
}
