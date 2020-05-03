package util;

import config.Config;
import model.DrawMode;

import java.awt.*;
import java.lang.reflect.Field;

public class CursorBuilder {

    private static final String CURSOR_PATH_NAME_PATTERN = "%s_CURSOR_PATH";
    private static final String CURSOR_XPOINT_NAME_PATTERN = "%s_CURSOR_XPOINT";
    private static final String CURSOR_YPOINT_NAME_PATTERN = "%s_CURSOR_YPOINT";

    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();

    private static boolean cursorIsDefault = true;
    private static Cursor cursor;
    private static Cursor defaultCursor;


    public static Cursor buildCursorByDrawMode(DrawMode drawMode) {
        String drawModeName = drawMode.name();
        resetCursor();

        String pathFieldName = String.format(CURSOR_PATH_NAME_PATTERN, drawModeName);
        String xPointFieldName = String.format(CURSOR_XPOINT_NAME_PATTERN, drawModeName);
        String yPointFieldName = String.format(CURSOR_YPOINT_NAME_PATTERN, drawModeName);
        Image cursorImage = null;
        int firstPoint = 0;
        int secondPoint = 0;

        Field[] configFields = Config.class.getDeclaredFields();
        for (Field field : configFields) {
            if (field.getName().equals(pathFieldName)) {
                String fieldValue = null;
                try {
                    fieldValue = (String) field.get(null);
                    cursorImage = toolkit.getImage(
                             CursorBuilder.class.getResource(Config.getProperty(fieldValue)));

                    firstPoint = getPointValue(xPointFieldName, firstPoint, configFields);
                    secondPoint = getPointValue(yPointFieldName, secondPoint, configFields);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                cursor = toolkit.createCustomCursor(
                        cursorImage, new Point(firstPoint, secondPoint), drawModeName.toLowerCase());

                cursorIsDefault = false;
            }
        }

        if (cursorIsDefault) {
            cursor = buildDefaultCursor(drawModeName);
        }

        return cursor;
    }

    public static Cursor buildICursorByPath(String path, int pointX, int pointY, String name) {
        Image cursorImage = toolkit.getImage(Cursor.class.getResource(path));
        cursor = toolkit.createCustomCursor(
                cursorImage, new Point(pointX, pointY), name);
        return cursor;
    }

    private static int getPointValue(String fieldName, int pointValue, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                String fieldValue = (String) field.get(null);
                pointValue = Integer.parseInt(Config.getProperty(fieldValue, "0"));
            }
        }
        return pointValue;
    }

    private static Cursor buildDefaultCursor(String drawModeName) {
        switch(drawModeName) {
            default:
                defaultCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
        }
        return defaultCursor;
    }

    private static void resetCursor() {
        cursorIsDefault = true;
    }
}
