package util;

import config.Config;

import java.awt.*;

public class CursorBuilder {

    private final Cursor PENCIL_CURSOR;
    private final Cursor MARKER_CURSOR;
    private final Cursor BRUSH_CURSOR;
    private final Cursor ERASER_CURSOR;
    private final Cursor RAG_CURSOR;
    private final Cursor FILL_CURSOR;

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
        BRUSH_CURSOR = getCursorFromPath(
                Config.getProperty(Config.BRUSH_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.BRUSH_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.BRUSH_CURSOR_YPOINT, "0")),
                "brush");
        ERASER_CURSOR = getCursorFromPath(
                Config.getProperty(Config.ERASER_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.ERASER_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.ERASER_CURSOR_YPOINT, "0")),
                "eraser");
        RAG_CURSOR = getCursorFromPath(
                Config.getProperty(Config.RAG_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.RAG_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.RAG_CURSOR_YPOINT, "0")),
                "rag");
        FILL_CURSOR = getCursorFromPath(
                Config.getProperty(Config.FILL_CURSOR_PATH),
                Integer.parseInt(Config.getProperty(Config.FILL_CURSOR_XPOINT, "0")),
                Integer.parseInt(Config.getProperty(Config.FILL_CURSOR_YPOINT, "0")),
                "fill");
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

    public Cursor getRAG_CURSOR() {
        return RAG_CURSOR;
    }

    public Cursor getBRUSH_CURSOR() {
        return BRUSH_CURSOR;
    }

    public Cursor getFILL_CURSOR() {
        return FILL_CURSOR;
    }


    private Cursor getCursorFromPath(String path, int pointX, int pointY, String name) {
        Image cursorImage = toolkit.getImage(getClass().getResource(path));
        Cursor cursor = toolkit.createCustomCursor(
                cursorImage, new Point(pointX, pointY), name);
        return cursor;
    }
}
