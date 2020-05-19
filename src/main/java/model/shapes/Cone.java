package model.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Cone extends Shape {
    private ArrayList<Point> allPoints;

    public Cone() {
        allPoints = new ArrayList<>();
    }

    @Override
    public ArrayList<Point> getAllPoints() {
        return allPoints;
    }
}
