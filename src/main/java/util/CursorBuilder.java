package util;

import config.Config;

import java.awt.*;

public class CursorBuilder {

    private final Cursor PENCIL_CURSOR;
    private final Cursor MARKER_CURSOR;
    private final Cursor ERASER_CURSOR;

    public CursorBuilder() {
        PENCIL_CURSOR = getCursorFromPath(
                Config.getProperty(Config.PENCIL_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.PENCIL_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.PENCIL_CURSOR_YPOINT, "0")),
                "pencil");
        MARKER_CURSOR = getCursorFromPath(
                Config.getProperty(Config.MARKER_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.MARKER_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.MARKER_CURSOR_YPOINT, "0")),
                "marker");
        ERASER_CURSOR = getCursorFromPath(
                Config.getProperty(Config.ERASER_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.ERASER_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.ERASER_CURSOR_YPOINT, "0")),
                "eraser");
    }

    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();

    public Cursor getPENCIL_CURSOR() {
        return PENCIL_CURSOR;
    }

    public Cursor getMARKER_CURSOR() {
        return MARKER_CURSOR;
    }

    public Cursor getERASER_CURSOR() {
        return ERASER_CURSOR;
    }

    private Cursor getCursorFromPath(String path, int pointX, int pointY, String name) {
        Image cursorImage = toolkit.getImage(getClass().getResource(path));
        Cursor cursor = toolkit.createCustomCursor(
                cursorImage, new Point(pointX, pointY), name);
        return cursor;
    }
}
