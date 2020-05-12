package view.swing.buttons;

import config.Config;
import view.swing.buttons.Button;

import javax.swing.*;

public class ToolButton extends Button {

    private static final int TOOL_BUTTON_XPOINT;
    private static final int TOOL_BUTTON_YPOINT;
    private static final int TOOL_BUTTON_SIZE;
    private static final int TOOL_BUTTON_OFFSET;

    static {
        TOOL_BUTTON_XPOINT = Integer.parseInt(Config.getProperty(Config.TOOL_BUTTON_XPOINT));
        TOOL_BUTTON_YPOINT = Integer.parseInt(Config.getProperty(Config.TOOL_BUTTON_YPOINT));
        TOOL_BUTTON_SIZE = Integer.parseInt(Config.getProperty(Config.TOOL_BUTTON_SIZE));
        TOOL_BUTTON_OFFSET = Integer.parseInt(Config.getProperty(Config.TOOL_BUTTON_OFFSET));
    }

    private static int buttonCounter;

    public ToolButton(int customXPoint, int customYPoint, int customWidth, int customHeight, ImageIcon icon) {
        super(icon);
        this.setBounds(customXPoint, customYPoint, customWidth, customHeight);
        buttonCounter++;

    }

    public ToolButton(String verticalMarker, int extraYOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                TOOL_BUTTON_XPOINT,
                TOOL_BUTTON_YPOINT + buttonCounter * (TOOL_BUTTON_SIZE + TOOL_BUTTON_OFFSET + extraYOffset),
                TOOL_BUTTON_SIZE,
                TOOL_BUTTON_SIZE);
        buttonCounter++;

    }

    public ToolButton(int extraXOffset, ImageIcon icon) {
        super(icon);
        this.setBounds(
                TOOL_BUTTON_XPOINT +  buttonCounter * (TOOL_BUTTON_SIZE + TOOL_BUTTON_OFFSET + extraXOffset),
                TOOL_BUTTON_YPOINT,
                TOOL_BUTTON_SIZE,
                TOOL_BUTTON_SIZE);
        buttonCounter++;

    }

    public ToolButton(ImageIcon icon, boolean buttonMenu) {
        super(icon);
        buttonCounter++;
    }

    public ToolButton(ImageIcon icon) {
        super(icon);
        buttonCounter++;
    }

    public ToolButton() {
        buttonCounter++;

    }

}
