package util;

import config.Config;

import javax.swing.*;
import java.awt.*;

public class IconBuilder {

    private final ImageIcon PENCIL_ICON;
    private final ImageIcon MARKER_ICON;
    private final ImageIcon ERASER_ICON;
    private final ImageIcon LINE_ICON;
    private final ImageIcon ELLIPSE_ICON;
    private final ImageIcon RECT_ICON;
    private final ImageIcon TEXT_ICON;
    private final ImageIcon UNDO_ICON;
    private final ImageIcon REDO_ICON;

    public IconBuilder() {
        PENCIL_ICON = getIconFromPath(
                Config.getProperty(Config.PENCIL_ICON_PATH));
        MARKER_ICON = getIconFromPath(
                Config.getProperty(Config.MARKER_ICON_PATH));
        ERASER_ICON = getIconFromPath(
                Config.getProperty(Config.ERASER_ICON_PATH));
        LINE_ICON = getIconFromPath(
                Config.getProperty(Config.LINE_ICON_PATH));
        ELLIPSE_ICON = getIconFromPath(
                Config.getProperty(Config.ELLIPSE_ICON_PATH));
        RECT_ICON = getIconFromPath(
                Config.getProperty(Config.RECT_ICON_PATH));
        TEXT_ICON = getIconFromPath(
                Config.getProperty(Config.TEXT_ICON_PATH));
        UNDO_ICON = getIconFromPath(
                Config.getProperty(Config.UNDO_ICON_PATH));
        REDO_ICON = getIconFromPath(
                Config.getProperty(Config.REDO_ICON_PATH));
    }

    public ImageIcon getPENCIL_ICON() {
        return PENCIL_ICON;
    }

    public ImageIcon getMARKER_ICON() {
        return MARKER_ICON;
    }

    public ImageIcon getERASER_ICON() {
        return ERASER_ICON;
    }

    public ImageIcon getLINE_ICON() {
        return LINE_ICON;
    }

    public ImageIcon getELLIPSE_ICON() {
        return ELLIPSE_ICON;
    }

    public ImageIcon getRECT_ICON() {
        return RECT_ICON;
    }

    public ImageIcon getTEXT_ICON() {
        return TEXT_ICON;
    }

    public ImageIcon getUNDO_ICON() {
        return UNDO_ICON;
    }

    public ImageIcon getREDO_ICON() {
        return REDO_ICON;
    }

    private ImageIcon getIconFromPath(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        return icon;
    }
}
