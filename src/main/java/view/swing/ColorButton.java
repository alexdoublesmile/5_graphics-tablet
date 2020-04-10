package view.swing;

import config.Config;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JButton{

    private static int buttonCounter;
    private static int fixValue;

    private final int X_POINT;
    private final int Y_POINT;
    private final int BUTTON_SIZE;
    private final int BUTTON_OFFSET;

    {
        X_POINT = Integer.parseInt(Config.getProperty(Config.COLOR_BUTTON_XPOINT));
        Y_POINT = Integer.parseInt(Config.getProperty(Config.COLOR_BUTTON_YPOINT));
        BUTTON_SIZE = Integer.parseInt(Config.getProperty(Config.COLOR_BUTTON_SIZE));
        BUTTON_OFFSET = Integer.parseInt(Config.getProperty(Config.COLOR_BUTTON_OFFSET));
    }


    public ColorButton(Color color, int customXPoint, int customYPoint, int customWidth, int customHeight, ImageIcon icon) {
        super(icon);
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(Color color, int customXPoint, int customYPoint, int customWidth, int customHeight) {
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(int customXPoint, int customYPoint, int customWidth, int customHeight, ImageIcon icon) {
        super(icon);
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        buttonCounter++;
    }

    public ColorButton(int customXPoint, int customYPoint, int customWidth, int customHeight) {
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        buttonCounter++;
    }

    public ColorButton(String verticalMarker, Color color, int extraYOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                X_POINT,
                Y_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraYOffset) - fixValue,
                BUTTON_SIZE,
                BUTTON_SIZE);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(String verticalMarker, Color color, int extraYOffset) {
        this.setBounds(
                X_POINT,
                Y_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraYOffset) - fixValue,
                BUTTON_SIZE,
                BUTTON_SIZE);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(String verticalMarker, int extraYOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                X_POINT,
                Y_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraYOffset) - fixValue,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(String verticalMarker, int extraYOffset) {
        this.setBounds(
                X_POINT,
                Y_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraYOffset) - fixValue,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(Color color, int extraXOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                X_POINT +  buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraXOffset) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        this.setBackground(color);
        buttonCounter++;
    }


    public ColorButton(Color color, int customXPoint, int customYPoint, int size, ImageIcon icon) {
        super(icon);
        this.setBounds(
                customXPoint,
                customYPoint,
                size,
                size);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(Color color, int customXPoint, int customYPoint, ImageIcon icon) {
        super(icon);
        this.setBounds(
                customXPoint,
                customYPoint,
                BUTTON_SIZE,
                BUTTON_SIZE);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(Color color, int customXPoint, int customYPoint, int size) {
        this.setBounds(
                customXPoint,
                customYPoint,
                size,
                size);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(Color color, int customXPoint, int customYPoint) {
        this.setBounds(
                customXPoint,
                customYPoint,
                BUTTON_SIZE,
                BUTTON_SIZE);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(int customXPoint, int customYPoint, ImageIcon icon) {
        super(icon);
        this.setBounds(
                customXPoint,
                customYPoint,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(int customXPoint, int customYPoint) {
        this.setBounds(
                customXPoint,
                customYPoint,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(Color color, int size) {
        this.setBounds(
                X_POINT +  buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                size,
                size);
        this.setBackground(color);
        buttonCounter++;
    }

    public ColorButton(int extraXOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                X_POINT +  buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraXOffset) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(Color color, ImageIcon icon) {
        super(icon);
        this.setBackground(color);
        this.setBounds(
                X_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;

    }

    public ColorButton(Color color, double extraXOffset) {
        this.setBackground(color);
        this.setBounds(
                X_POINT +  buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + (int) extraXOffset) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(Color color, boolean counterFix, int fixValue) {
        buttonCounter++;
        ColorButton.fixValue = fixValue;
        this.setBackground(color);
        this.setBounds(
                X_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;

    }

    public ColorButton(int extraXOffset) {
        this.setBounds(
                X_POINT +  buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET + extraXOffset) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;
    }

    public ColorButton(ImageIcon icon) {
        super(icon);
        this.setBounds(
                X_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;

    }

    public ColorButton(Color color) {
        this.setBackground(color);
        this.setBounds(
                X_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;



    }

    public ColorButton() {
        this.setBounds(
                X_POINT + buttonCounter * (BUTTON_SIZE + BUTTON_OFFSET) - fixValue,
                Y_POINT,
                BUTTON_SIZE,
                BUTTON_SIZE);
        buttonCounter++;

    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        if (getModel().isArmed()) {
//            g.setColor(Color.GREEN);
//            setForeground(Color.yellow);
//        } else {
//            g.setColor(getBackground());
//            setForeground(Color.BLACK);
//        }
//        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
//
//        super.paintComponent(g);
//    }
//
//    @Override
//    protected void paintBorder(Graphics g) {
//        g.setColor(getForeground());
//        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
//    }

    public static int getButtonCounter() {
        return buttonCounter;
    }

    public static int getFixValue() {
        return fixValue;
    }

    public int getX_POINT() {
        return X_POINT;
    }

    public int getY_POINT() {
        return Y_POINT;
    }

    public int getBUTTON_SIZE() {
        return BUTTON_SIZE;
    }

    public int getBUTTON_OFFSET() {
        return BUTTON_OFFSET;
    }
}
