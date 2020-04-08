package config;


import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE_NAME = "settings.properties";

    public static final String PENCIL_CURSOR_PATH = "pencil.cursor.path";
    public static final String PENCIL_CURSOR_XPOINT = "pencil.cursor.xpoint";
    public static final String PENCIL_CURSOR_YPOINT = "pencil.cursor.ypoint";
    public static final String MARKER_CURSOR_PATH = "marker.cursor.path";
    public static final String MARKER_CURSOR_XPOINT = "marker.cursor.xpoint";
    public static final String MARKER_CURSOR_YPOINT = "marker.cursor.ypoint";
    public static final String ERASER_CURSOR_PATH = "eraser.cursor.path";
    public static final String ERASER_CURSOR_XPOINT = "eraser.cursor.xpoint";
    public static final String ERASER_CURSOR_YPOINT = "eraser.cursor.ypoint";

    public static final String PENCIL_ICON_PATH = "pencil.icon.path";
    public static final String MARKER_ICON_PATH = "marker.icon.path";
    public static final String ERASER_ICON_PATH = "eraser.icon.path";
    public static final String LINE_ICON_PATH = "line.icon.path";
    public static final String ELLIPSE_ICON_PATH = "ellipse.icon.path";
    public static final String RECT_ICON_PATH = "rect.icon.path";
    public static final String TEXT_ICON_PATH = "text.icon.path";

    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name, String defaultValue) {
        if (properties.isEmpty()) {
            loadProperties();
        }
        return properties.getProperty(name, defaultValue);
    }

    public synchronized static String getProperty(String name) {
        if (properties.isEmpty()) {
            loadProperties();
        }
        return properties.getProperty(name);
    }

    private static void loadProperties() {
        try (InputStream is = Config.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {

            properties.load(is);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}