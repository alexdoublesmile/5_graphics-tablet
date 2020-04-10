package view.swing.buttons;

import config.Config;

import javax.swing.*;

public class FunctionButton extends JButton{


    private static final int FUNCTION_BUTTON_XPOINT;
    private static final int FUNCTION_BUTTON_YPOINT;
    private static final int FUNCTION_BUTTON_SIZE;
    private static final int FUNCTION_BUTTON_OFFSET;


    static {
        FUNCTION_BUTTON_XPOINT = Integer.parseInt(Config.getProperty(Config.FUNCTION_BUTTON_XPOINT));
        FUNCTION_BUTTON_YPOINT = Integer.parseInt(Config.getProperty(Config.FUNCTION_BUTTON_YPOINT));
        FUNCTION_BUTTON_SIZE = Integer.parseInt(Config.getProperty(Config.FUNCTION_BUTTON_SIZE));
        FUNCTION_BUTTON_OFFSET = Integer.parseInt(Config.getProperty(Config.FUNCTION_BUTTON_OFFSET));
    }


    private static int buttonCounter;

    public FunctionButton(int customXPoint, int customYPoint, int customWidth, int customHeight, ImageIcon icon) {
        super(icon);
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        buttonCounter++;

    }

    public FunctionButton(String verticalMarker, int extraYOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                FUNCTION_BUTTON_XPOINT,
                FUNCTION_BUTTON_YPOINT + buttonCounter * (FUNCTION_BUTTON_SIZE + FUNCTION_BUTTON_OFFSET + extraYOffset),
                FUNCTION_BUTTON_SIZE,
                FUNCTION_BUTTON_SIZE);
        buttonCounter++;

    }

    public FunctionButton(int extraXOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                FUNCTION_BUTTON_XPOINT +  buttonCounter * (FUNCTION_BUTTON_SIZE + FUNCTION_BUTTON_OFFSET + extraXOffset),
                FUNCTION_BUTTON_YPOINT,
                FUNCTION_BUTTON_SIZE,
                FUNCTION_BUTTON_SIZE);
        buttonCounter++;

    }

    public FunctionButton(String buttonName, ImageIcon icon) {
        super(buttonName, icon);
        buttonCounter++;

    }

    public FunctionButton(ImageIcon icon) {
        super(icon);
        buttonCounter++;

    }

    public FunctionButton(String buttonName) {
        super(buttonName);
        buttonCounter++;

    }

    public FunctionButton() {
        buttonCounter++;

    }
}
