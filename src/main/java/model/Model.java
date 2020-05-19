package model;

import config.Config;
import model.shapes.*;
import model.shapes.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    public static final double PARALLELOGRAM_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELOGRAM_FACTOR));

    public static final double PYRAMID_MAIN_FACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_MAIN_LINE_FACTOR));
    public static final double PYRAMID_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_LEFT_XFACTOR));
    public static final double PYRAMID_LEFT_YFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_LEFT_YFACTOR));
    public static final double PYRAMID_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_RIGHT_XFACTOR));
    public static final double PYRAMID_RIGHT_YFACTOR = Float.parseFloat(Config.getProperty(Config.PYRAMID_RIGHT_YFACTOR));

    public static final double PYRAMID_BACK_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_LEFT_XFACTOR));
    public static final double PYRAMID_BACK_LEFT_YFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_LEFT_YFACTOR));
    public static final double PYRAMID_BACK_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_RIGHT_XFACTOR));
    public static final double PYRAMID_BACK_RIGHT_YFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_BACK_RIGHT_YFACTOR));
    public static final double PYRAMID_FRONT_LEFT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_FRONT_LEFT_XFACTOR));
    public static final double PYRAMID_FRONT_RIGHT_XFACTOR = Float.parseFloat(Config.getProperty(Config.TETRA_PYRAMID_FRONT_RIGHT_XFACTOR));

    public static final double PYRAMID_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CUSTOM_PYRAMID_GROW_FACTOR));
    public static final int PYRAMID_TOP_PADDING = (int) Float.parseFloat(Config.getProperty(Config.CUSTOM_PYRAMID_TOP_PADDING));

    public static final double PRISM_LEFT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_XFACTOR));
    public static final double PRISM_LEFT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_TOP_YFACTOR));
    public static final double PRISM_RIGHT_TOP_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_XFACTOR));
    public static final double PRISM_RIGHT_TOP_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_TOP_YFACTOR));
    public static final double PRISM_LEFT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_XFACTOR));
    public static final double PRISM_LEFT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_LEFT_BOTTOM_YFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_XFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_XFACTOR));
    public static final double PRISM_RIGHT_BOTTOM_YFACTOR = Float.parseFloat(Config.getProperty(Config.TRI_PRISM_RIGHT_BOTTOM_YFACTOR));

    public static final double PARALLELEPIPED_SIDE_ANGLE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_SIDE_ANGLE_FACTOR));
    public static final double PARALLELEPIPED_FRONT_ANGLE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_FRONT_ANGLE_FACTOR));
    public static final double PARALLELEPIPED_CURVE_FACTOR = Float.parseFloat(Config.getProperty(Config.PARALLELEPIPED_CURVE_FACTOR));

    public static final double PRISM_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CUSTOM_PRISM_GROW_FACTOR));

    public static final double CONE_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_GROW_FACTOR));
    public static final int CONE_DOTTED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_DOTTED_ARC_START_ANGLE));
    public static final int CONE_DOTTED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_DOTTED_ARC_FINAL_ANGLE));
    public static final int CONE_LINED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_LINED_ARC_START_ANGLE));
    public static final int CONE_LINED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CONE_LINED_ARC_FINAL_ANGLE));
    public static final double CONE_LEFT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_LEFT_BUG_FACTOR));
    public static final double CONE_RIGHT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CONE_RIGHT_BUG_FACTOR));

    public static final double CYLINDER_GROW_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_GROW_FACTOR));
    public static final int CYLINDER_DOTTED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_DOTTED_ARC_START_ANGLE));
    public static final int CYLINDER_DOTTED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_DOTTED_ARC_FINAL_ANGLE));
    public static final int CYLINDER_LINED_ARC_START_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_LINED_ARC_START_ANGLE));
    public static final int CYLINDER_LINED_ARC_FINAL_ANGLE = (int) Float.parseFloat(Config.getProperty(Config.CYLINDER_LINED_ARC_FINAL_ANGLE));
    public static final double CYLINDER_TOP_LEFT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_TOP_LEFT_BUG_FACTOR));
    public static final double CYLINDER_TOP_RIGHT_BUG_FACTOR = Float.parseFloat(Config.getProperty(Config.CYLINDER_TOP_RIGHT_BUG_FACTOR));
    public static final double CYLINDER_RIGHT_LINE_BUG_CONSTANT = Float.parseFloat(Config.getProperty(Config.CYLINDER_RIGHT_LINE_BUG_CONSTANT));

    public static final int POLYGON_ACCURACY_FACTOR = (int) Float.parseFloat(Config.getProperty(Config.POLYGON_ACCURACY_FACTOR));

    public static final int AUTO_ACCURACY_FACTOR = (int) Float.parseFloat(Config.getProperty(Config.AUTO_ACCURACY_FACTOR));

    public static final int STICKY_LINE_FACTOR = Integer.parseInt(Config.getProperty(Config.STICKY_LINE_FACTOR));

    public static final double RESIZE_PLUS_FACTOR = Double.parseDouble(Config.getProperty(Config.RESIZE_PLUS_FACTOR));

    public static final double ARROW_PEAK_FACTOR = Float.parseFloat(Config.getProperty(Config.ARROW_PEAK_FACTOR));
    public static final double ARROW_WIDTH_FACTOR = Float.parseFloat(Config.getProperty(Config.ARROW_WIDTH_FACTOR));
    public static final double ARROW_MIN_LENGTH = Float.parseFloat(Config.getProperty(Config.ARROW_MIN_LENGTH));
    public static final double ARROW_MAX_LENGTH = Float.parseFloat(Config.getProperty(Config.ARROW_MAX_LENGTH));


    public static final int INDICATOR_WIDTH = Integer.parseInt(Config.getProperty(Config.INDICATOR_WIDTH));
    public static final int INDICATOR_TOP_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_TOP_OFFSET));
    public static final int INDICATOR_BOTTOM_OFFSET = Integer.parseInt(Config.getProperty(Config.INDICATOR_BOTTOM_OFFSET));


    private ArrayList<DrawMode> shapeModeList;
    private ArrayList<DrawMode> customShapeModeList;
    private ArrayList<DrawMode> copyModeList;
    private ArrayList<DrawMode> noButtonModeList;
    private boolean shapeMode;
    private boolean copyMode;
    private boolean customShapeMode;
    private boolean noButtonMode;

    private boolean firstMove;
    private boolean rectExtract;

    {
        shapeModeList = new ArrayList<>();
        shapeModeList.add(DrawMode.LINE);
//        shapeModeList.add(DrawMode.DOTTED_LINE);
        shapeModeList.add(DrawMode.ARROW);
//        shapeModeList.add(DrawMode.CIRCLE);
        shapeModeList.add(DrawMode.ELLIPSE);
        shapeModeList.add(DrawMode.RECT);
//        shapeModeList.add(DrawMode.PARALLELOGRAM);
        shapeModeList.add(DrawMode.POLYGON);
        shapeModeList.add(DrawMode.PYRAMID);
        shapeModeList.add(DrawMode.PYRAMID_TETRA);
//        shapeModeList.add(DrawMode.PYRAMID_CUSTOM);
        shapeModeList.add(DrawMode.PRISM);
        shapeModeList.add(DrawMode.PARALLELEPIPED);
//        shapeModeList.add(DrawMode.PRISM_CUSTOM);
        shapeModeList.add(DrawMode.CONE);
        shapeModeList.add(DrawMode.CYLINDER);
        shapeModeList.add(DrawMode.SPHERE);
//        shapeModeList.add(DrawMode.COPY);
        shapeModeList.add(DrawMode.CUT);
//        shapeModeList.add(DrawMode.COPY_SHAPE);
        shapeModeList.add(DrawMode.CUT_SHAPE);
//        shapeModeList.add(DrawMode.PASTE);

        copyModeList = new ArrayList<>();
//        copyModeList.add(DrawMode.COPY);
        copyModeList.add(DrawMode.CUT);
//        copyModeList.add(DrawMode.COPY_SHAPE);
        copyModeList.add(DrawMode.CUT_SHAPE);

        customShapeModeList = new ArrayList<>();
        customShapeModeList.add(DrawMode.POLYGON);
//        customShapeModeList.add(DrawMode.PYRAMID_CUSTOM);
//        customShapeModeList.add(DrawMode.PRISM_CUSTOM);

        noButtonModeList = new ArrayList<>();
        noButtonModeList.add(DrawMode.SCALE);
        noButtonModeList.add(DrawMode.PASTE);

        allPoints = new ArrayList<>();
    }

    private ArrayList<UndoRedoService> undoList;
    private DrawMode drawMode;
    private int startX;
    private int startY;
    private int finalX;
    private int finalY;
    private int drawPanelWidth;
    private int drawPanelHeight;

    private int eraserStroke;
    private int ragStroke;
    private String fileName;
    private boolean loading;

    private ArrayList<Shape> shapeList;
    private Line line;
    private Arrow arrow;
    private Ellipse ellipse;
    private Rect rect;
    private Parallelogram parallelogram;
    private Pyramid pyramid;
    private Prism prism;
    private TetraPyramid tetraPyramid;
    private TetraPrism tetraPrism;
    private Cone cone;
    private Cylinder cylinder;
    private Sphere sphere;

    {
        line = new Line();
        arrow = new Arrow();
        ellipse = new Ellipse();
        rect = new Rect();
        parallelogram = new Parallelogram();
        pyramid = new Pyramid();
        prism = new Prism();
        tetraPyramid = new TetraPyramid();
        tetraPrism = new TetraPrism();
        cone = new Cone();
        cylinder = new Cylinder();
        sphere = new Sphere();

        shapeList = new ArrayList<>();
        shapeList.add(line);
        shapeList.add(arrow);
        shapeList.add(ellipse);
        shapeList.add(rect);
        shapeList.add(parallelogram);
        shapeList.add(pyramid);
        shapeList.add(prism);
        shapeList.add(tetraPyramid);
        shapeList.add(tetraPrism);
        shapeList.add(cone);
        shapeList.add(cylinder);
        shapeList.add(sphere);
    }



//    private Point triPyramidFront;
//    private Point triPyramidLeft;
//    private Point triPyramidRight;
//
//    private Point tetraPyramidFrontLeft;
//    private Point tetraPyramidFrontRight;
//    private Point tetraPyramidBackLeft;
//    private Point tetraPyramidBackRight;
//    private Point tetraPyramidBottomCenter;
//
//    private Point triPrismLeftTop;
//    private Point triPrismLeftBottom;
//    private Point triPrismRightTop;
//    private Point triPrismRightBottom;
//
//    private Point parallelepipedFrontLeftTop;
//    private Point parallelepipedFrontRightTop;
//    private Point parallelepipedFrontLeftBottom;
//    private Point parallelepipedFrontRightBottom;
//    private Point parallelepipedBackLeftTop;
//    private Point parallelepipedBackRightTop;
//    private Point parallelepipedBackLeftBottom;
//    private Point parallelepipedBackRightBottom;
//
//    private Point parallelogramLeftBottom;
//    private Point parallelogramRightTop;

//    private Point circleCenter;

//    private Point ellipseCenter;
//
//    private Point arrowLeftPoint;
//    private Point arrowRightPoint;

    private ArrayList<Point> allPoints;
    private ArrayList<Point> pointList;
    private ArrayList<Point> prismTopPointList;

    private Polygon polygon;

    private boolean scaleMode;
    private boolean polygonInWork;
    private double currentScale;
    private boolean cutting;

    private HashMap<DrawMode, Float> strokeList;
    private HashMap<DrawMode, Float> defaultStrokeList;
    private float pencilStroke;

    public Model() {
        undoList = new ArrayList<>();

        drawMode = DrawMode.PENCIL;
        eraserStroke = Integer.valueOf(Config.getProperty(Config.ERASER_BASIC_STROKE));
        ragStroke = Integer.valueOf(Config.getProperty(Config.RAG_BASIC_STROKE));
//        triPyramidFront = new Point(0, 0);
//        triPyramidLeft = new Point(0, 0);
//        triPyramidRight = new Point(0, 0);
//        tetraPyramidFrontLeft = new Point(0, 0);
//        tetraPyramidBackLeft = new Point(0, 0);
//        tetraPyramidBackRight = new Point(0, 0);
//        tetraPyramidFrontRight = new Point(0, 0);
//        tetraPyramidBottomCenter = new Point(0, 0);
//        triPrismLeftTop = new Point(0, 0);
//        triPrismLeftBottom = new Point(0, 0);
//        triPrismRightTop = new Point(0, 0);
//        triPrismRightBottom = new Point(0, 0);
//        parallelepipedFrontLeftTop = new Point(0, 0);
//        parallelepipedFrontRightTop = new Point(0, 0);
//        parallelepipedFrontLeftBottom = new Point(0, 0);
//        parallelepipedFrontRightBottom = new Point(0, 0);
//        parallelepipedBackLeftTop = new Point(0, 0);
//        parallelepipedBackRightTop = new Point(0, 0);
//        parallelepipedBackLeftBottom = new Point(0, 0);
//        parallelepipedBackRightBottom = new Point(0, 0);
//        parallelogramLeftBottom = new Point(0, 0);
//        parallelogramRightTop = new Point(0, 0);

//        circleCenter = new Point(0, 0);
//        circleCenter = new Point(0, 0);
//        arrowLeftPoint = new Point(0, 0);
//        arrowRightPoint = new Point(0, 0);

        pointList = new ArrayList<>();
        prismTopPointList = new ArrayList<>();
        polygon = new Polygon();
        currentScale = 1;
        drawPanelWidth = 0;
        drawPanelHeight = 0;

        strokeList = new HashMap<>();
        strokeList.put(DrawMode.PENCIL, Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE)));
        strokeList.put(DrawMode.RAG, Float.valueOf(Config.getProperty(Config.RAG_BASIC_STROKE)));
        strokeList.put(DrawMode.LINE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.ELLIPSE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.RECT, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.ARROW, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.PYRAMID, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.PYRAMID_TETRA, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.PRISM, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.PARALLELEPIPED, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.CONE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.CYLINDER, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.SPHERE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        strokeList.put(DrawMode.POLYGON, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));

        defaultStrokeList = new HashMap<>();
        defaultStrokeList.put(DrawMode.PENCIL, Float.valueOf(Config.getProperty(Config.PENCIL_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.RAG, Float.valueOf(Config.getProperty(Config.RAG_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.LINE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.ELLIPSE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.RECT, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.ARROW, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.PYRAMID, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.PYRAMID_TETRA, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.PRISM, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.PARALLELEPIPED, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.CONE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.CYLINDER, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.SPHERE, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
        defaultStrokeList.put(DrawMode.POLYGON, Float.valueOf(Config.getProperty(Config.FIGURE_BASIC_STROKE)));
    }

    public int getEllipseBigRadius() {
        return Math.abs(startX - finalX) / 2;
    }


    public int getEllipseSmallRadius() {
        return Math.abs(startY - finalY) / 2;
    }

    public Point getRectCenter(boolean downDrawing, boolean rightDrawing) {
        Point rectCenter = new Point(0, 0);
        if (downDrawing && rightDrawing) {
            rectCenter.setLocation(
                    startX + Math.abs(startX - finalX) / 2,
                    startY + Math.abs(startY - finalY) / 2);
        } else if (downDrawing && !rightDrawing) {
            rectCenter.setLocation(
                    startX - Math.abs(startX - finalX) / 2,
                    startY + Math.abs(startY - finalY) / 2);
        } else if (!downDrawing && rightDrawing) {
            rectCenter.setLocation(
                    startX + Math.abs(startX - finalX) / 2,
                    startY - Math.abs(startY - finalY) / 2);
        } else {
            rectCenter.setLocation(
                    startX - Math.abs(startX - finalX) / 2,
                    startY - Math.abs(startY - finalY) / 2);
        }

        return rectCenter;
    }

    public double getDrawLine() {
        return Math.sqrt(
                Math.pow(startX - finalX, 2.0) +
                        Math.pow(startY - finalY, 2.0)
        );
    }

    public void resetAllPoints() {
//        triPyramidFront = new Point(finalX, finalY);
//        triPyramidLeft = new Point(finalX, finalY);
//        triPyramidRight = new Point(finalX, finalY);
//        tetraPyramidFrontLeft = new Point(finalX, finalY);
//        tetraPyramidBackLeft = new Point(finalX, finalY);
//        tetraPyramidBackRight = new Point(finalX, finalY);
//        tetraPyramidFrontRight = new Point(finalX, finalY);
//        tetraPyramidBottomCenter = new Point(finalX, finalY);
//        triPrismLeftTop = new Point(finalX, finalY);
//        triPrismLeftBottom = new Point(finalX, finalY);
//        triPrismRightTop = new Point(finalX, finalY);
//        triPrismRightBottom = new Point(finalX, finalY);
//        parallelepipedFrontLeftTop = new Point(finalX, finalY);
//        parallelepipedFrontRightTop = new Point(finalX, finalY);
//        parallelepipedFrontLeftBottom = new Point(finalX, finalY);
//        parallelepipedFrontRightBottom = new Point(finalX, finalY);
//        parallelepipedBackLeftBottom = new Point(finalX, finalY);
//        parallelepipedBackLeftTop = new Point(finalX, finalY);
//        parallelepipedBackRightTop = new Point(finalX, finalY);
//        parallelepipedBackRightBottom = new Point(finalX, finalY);
//        parallelogramLeftBottom = new Point(finalX, finalY);
//        parallelogramRightTop = new Point(finalX, finalY);
//
//        circleCenter = new Point(finalX, finalY);
//        circleCenter = new Point(finalX, finalY);

        for (Shape shape : shapeList) {
            for (Point point : shape.getAllPoints()) {
                point.move(finalX, finalY);
            }
        }
    }

    public boolean wasIterated(int undoIndex) {
        return undoList.get(undoIndex).isWasIterated();
    }

    public  void saveAction(BufferedImage action, int undoIndex) {
        undoList.get(undoIndex).saveAction(action);
    }
    public  void reSaveAction(BufferedImage action, int undoIndex) {
        undoList.get(undoIndex).reSaveAction(action);
    }
    public  void removeLastAction(int undoIndex) {
        undoList.get(undoIndex).removeLastAction();
    }

    public BufferedImage getPreviousAction(int undoIndex) {
        resetAllCustomPoints();
        return undoList.get(undoIndex).getPreviousAction();
    }

    public BufferedImage getNextAction(int undoIndex) {
        return undoList.get(undoIndex).getNextAction();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DrawMode getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
    }

    public int getFinalX() {
        return finalX;
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getFinalY() {
        return finalY;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public int getEraserStroke() {
        return eraserStroke;
    }

    public void setEraserStroke(int eraserStroke) {
        this.eraserStroke = eraserStroke;
    }

    public int getRagStroke() {
        return ragStroke;
    }

    public void setRagStroke(int ragStroke) {
        this.ragStroke = ragStroke;
    }

    public Point getTriPyramidLeft() {
        return pyramid.getTriPyramidLeft();
    }

    public Point getTriPyramidRight() {
        return pyramid.getTriPyramidRight();
    }

    public Point getTetraPyramidFrontLeft() {
        return tetraPyramid.getTetraPyramidFrontLeft();
    }

    public Point getTetraPyramidBackLeft() {
        return tetraPyramid.getTetraPyramidBackLeft();
    }

    public Point getTetraPyramidBackRight() {
        return tetraPyramid.getTetraPyramidBackRight();
    }

//    public Point getCircleCenter() {
//        return circleCenter;
//    }
//
//    public void setCircleCenter(Point circleCenter) {
//        this.circleCenter = circleCenter;
//    }

    public Point getTriPrismLeftTop() {
        return prism.getTriPrismLeftTop();
    }

    public Point getTriPrismLeftBottom() {
        return prism.getTriPrismLeftBottom();
    }

    public Point getTriPrismRightTop() {
        return prism.getTriPrismRightTop();
    }

    public Point getTriPrismRightBottom() {
        return prism.getTriPrismRightBottom();
    }

    public Point getTetraPyramidFrontRight() {
        return tetraPyramid.getTetraPyramidFrontRight();
    }

    public Point getParallelepipedFrontLeftTop() {
        return tetraPrism.getParallelepipedFrontLeftTop();
    }

    public Point getParallelepipedFrontRightTop() {
        return tetraPrism.getParallelepipedFrontRightTop();
    }

    public Point getParallelepipedFrontLeftBottom() {
        return tetraPrism.getParallelepipedFrontLeftBottom();
    }

    public Point getParallelepipedBackLeftBottom() {
        return tetraPrism.getParallelepipedBackLeftBottom();
    }

    public Point getParallelepipedBackRightTop() {
        return tetraPrism.getParallelepipedBackRightTop();
    }

    public Point getParallelepipedBackRightBottom() {
        return tetraPrism.getParallelepipedBackRightBottom();
    }

//    public Point getParallelogramLeftBottom() {
//        return parallelogramLeftBottom;
//    }
//
//    public Point getParallelogramRightTop() {
//        return parallelogramRightTop;
//    }

    public Point getEllipseCenter() {
        return ellipse.getEllipseCenter();
    }

//    public void setTriPyramidLeft(Point triPyramidLeft) {
//        this.triPyramidLeft = triPyramidLeft;
//    }
//
//    public void setTriPyramidRight(Point triPyramidRight) {
//        this.triPyramidRight = triPyramidRight;
//    }
//
//    public void setTetraPyramidFrontLeft(Point tetraPyramidFrontLeft) {
//        this.tetraPyramidFrontLeft = tetraPyramidFrontLeft;
//    }
//
//    public void setTetraPyramidFrontRight(Point tetraPyramidFrontRight) {
//        this.tetraPyramidFrontRight = tetraPyramidFrontRight;
//    }
//
//    public void setTetraPyramidBackLeft(Point tetraPyramidBackLeft) {
//        this.tetraPyramidBackLeft = tetraPyramidBackLeft;
//    }
//
//    public void setTetraPyramidBackRight(Point tetraPyramidBackRight) {
//        this.tetraPyramidBackRight = tetraPyramidBackRight;
//    }
//
//    public void setTriPrismLeftTop(Point triPrismLeftTop) {
//        this.triPrismLeftTop = triPrismLeftTop;
//    }
//
//    public void setTriPrismLeftBottom(Point triPrismLeftBottom) {
//        this.triPrismLeftBottom = triPrismLeftBottom;
//    }
//
//    public void setTriPrismRightTop(Point triPrismRightTop) {
//        this.triPrismRightTop = triPrismRightTop;
//    }
//
//    public void setTriPrismRightBottom(Point triPrismRightBottom) {
//        this.triPrismRightBottom = triPrismRightBottom;
//    }
//
//    public void setParallelepipedFrontLeftTop(Point parallelepipedFrontLeftTop) {
//        this.parallelepipedFrontLeftTop = parallelepipedFrontLeftTop;
//    }
//
//    public void setParallelepipedFrontRightTop(Point parallelepipedFrontRightTop) {
//        this.parallelepipedFrontRightTop = parallelepipedFrontRightTop;
//    }
//
//    public void setParallelepipedFrontLeftBottom(Point parallelepipedFrontLeftBottom) {
//        this.parallelepipedFrontLeftBottom = parallelepipedFrontLeftBottom;
//    }
//
//    public void setParallelepipedBackLeftBottom(Point parallelepipedBackLeftBottom) {
//        this.parallelepipedBackLeftBottom = parallelepipedBackLeftBottom;
//    }
//
//    public void setParallelepipedBackRightTop(Point parallelepipedBackRightTop) {
//        this.parallelepipedBackRightTop = parallelepipedBackRightTop;
//    }
//
//    public void setParallelepipedBackRightBottom(Point parallelepipedBackRightBottom) {
//        this.parallelepipedBackRightBottom = parallelepipedBackRightBottom;
//    }
//
//    public void setParallelogramLeftBottom(Point parallelogramLeftBottom) {
//        this.parallelogramLeftBottom = parallelogramLeftBottom;
//    }
//
//    public void setParallelogramRightTop(Point parallelogramRightTop) {
//        this.parallelogramRightTop = parallelogramRightTop;
//    }

    public void setEllipseCenter(Point ellipseCenter) {
        ellipse.setEllipseCenter(ellipseCenter);
    }

    public Point getTriPyramidFront() {
        return pyramid.getTriPyramidFront();
    }

//    public void setTriPyramidFront(Point triPyramidFront) {
//        pyramid.setTriPyramidFront(triPyramidFront);
//    }

    public Point getTetraPyramidBottomCenter() {
        return tetraPyramid.getTetraPyramidBottomCenter();
    }

//    public void setTetraPyramidBottomCenter(Point tetraPyramidBottomCenter) {
//        this.tetraPyramidBottomCenter = tetraPyramidBottomCenter;
//    }

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public ArrayList<Point> getPrismTopPointList() {
        return prismTopPointList;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void resetAllCustomPoints() {
        this.polygon = new Polygon();
        this.pointList = new ArrayList<>();
        this.prismTopPointList = new ArrayList<>();

    }

    public void fillPolygon() {
        int[] xPoints = new int[pointList.size()];
        int[] yPoints = new int[pointList.size()];

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] = pointList.get(i).x;
        }
        for (int i = 0; i < yPoints.length; i++) {
            yPoints[i] = pointList.get(i).y;
        }

        polygon = new Polygon(xPoints, yPoints, xPoints.length);
    }

//    public Point getArrowLeftPoint() {
//        return arrowLeftPoint;
//    }
//
//    public void setArrowLeftPoint(Point arrowLeftPoint) {
//        this.arrowLeftPoint = arrowLeftPoint;
//    }
//
//    public Point getArrowRightPoint() {
//        return arrowRightPoint;
//    }
//
//    public void setArrowRightPoint(Point arrowRightPoint) {
//        this.arrowRightPoint = arrowRightPoint;
//    }

    public boolean isShapeMode() {
        return shapeMode;
    }

    public void setShapeMode(boolean shapeMode) {
        this.shapeMode = shapeMode;
    }

    public ArrayList<DrawMode> getShapeModeList() {
        return shapeModeList;
    }

    public ArrayList<DrawMode> getCustomShapeModeList() {
        return customShapeModeList;
    }

    public boolean isCustomShapeMode() {
        return customShapeMode;
    }

    public boolean isNoButtonMode() {
        return noButtonMode;
    }

    public void setNoButtonMode(boolean noButtonMode) {
        this.noButtonMode = noButtonMode;
    }

    public void setCustomShapeMode(boolean customShapeMode) {
        this.customShapeMode = customShapeMode;
    }

    public boolean isPolygonInWork() {
        return polygonInWork;
    }

    public void setPolygonInWork(boolean polygonInWork) {
        this.polygonInWork = polygonInWork;
    }

    public Point getParallelepipedFrontRightBottom() {
        return tetraPrism.getParallelepipedFrontRightBottom();
    }

    public Point getParallelepipedBackLeftTop() {
        return tetraPrism.getParallelepipedBackLeftTop();
    }

    public ArrayList<DrawMode> getNoButtonModeList() {
        return noButtonModeList;
    }


    public int getScaleRectX() {
        int scaleRectX =
                finalX - getScaleRectWidth() / 2 < 0 ? 0 :
                        finalX + getScaleRectWidth() / 2 > getDrawPanelWidth() ?
                                getDrawPanelWidth() - getScaleRectWidth() : finalX - getScaleRectWidth() / 2;

        return scaleRectX;
    }

    public int getScaleRectY() {
        int scaleRectY =
                finalY - getScaleRectHeight() / 2 < 0 ? 0 :
                        finalY + getScaleRectHeight() / 2 > getDrawPanelHeight() ?
                                getDrawPanelHeight() - getScaleRectHeight() : finalY - getScaleRectHeight() / 2;

        return scaleRectY;
    }

    public int getScaleRectWidth() {
        return (int) (getDrawPanelWidth() / RESIZE_PLUS_FACTOR);
    }

    public int getScaleRectHeight() {
        return (int) (getDrawPanelHeight() / RESIZE_PLUS_FACTOR);
    }

    public boolean isScaleMode() {
        return scaleMode;
    }

    public void setScaleMode(boolean scaleMode) {
        this.scaleMode = scaleMode;
    }

    public void setCurrentScale(double currentScale) {
        this.currentScale = currentScale;
    }

    public double getCurrentScale() {
        return currentScale;
    }

    public int getDrawPanelWidth() {
        return (int) (drawPanelWidth / currentScale);
    }

    public void setDrawPanelWidth(int drawPanelWidth) {
        this.drawPanelWidth = drawPanelWidth;
    }

    public int getDrawPanelHeight() {
        return (int) (drawPanelHeight / currentScale);
    }

    public void setDrawPanelHeight(int drawPanelHeight) {
        this.drawPanelHeight = drawPanelHeight;
    }

    public ArrayList<UndoRedoService> getUndoList() {
        return undoList;
    }

    public int getUndoQuantity() {
        return UndoRedoService.getUndoQuantity();
    }

    public void setUndoQuantity(int newUndoQuantity) {

            UndoRedoService.setUndoQuantity(newUndoQuantity);
    }

    public boolean isCopyMode() {
        return copyMode;
    }

    public void setCopyMode(boolean copyMode) {
        this.copyMode = copyMode;
    }

    public ArrayList<DrawMode> getCopyModeList() {
        return copyModeList;
    }

    public boolean isCutting() {
        return cutting;
    }

    public void setCutting(boolean cutting) {
        this.cutting = cutting;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isRectExtract() {
        return rectExtract;
    }

    public void setRectExtract(boolean rectExtract) {
        this.rectExtract = rectExtract;
    }

    public float getPencilStroke() {
        return pencilStroke;
    }

    public void setPencilStroke(float pencilStroke) {
        this.pencilStroke = pencilStroke;
    }

    public HashMap<DrawMode, Float> getStrokeList() {
        return strokeList;
    }

    public HashMap<DrawMode, Float> getDefaultStrokeList() {
        return defaultStrokeList;
    }
}
