package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Sphere extends Shape {
    private ArrayList<Point> allPoints;

    public Sphere() {
        allPoints = new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }
}
