package config;


import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE_NAME = "settings.properties";

    public static final String PENCIL_CURSOR_PATH = "pencil.cursor.path";
    public static final String PENCIL_CURSOR_XPOINT = "pencil.cursor.xpoint";
    public static final String PENCIL_CURSOR_YPOINT = "pencil.cursor.ypoint";
    public static final String PENCIL_BASIC_STROKE = "pencil.basic.stroke";

    public static final String MARKER_CURSOR_PATH = "marker.cursor.path";
    public static final String MARKER_CURSOR_XPOINT = "marker.cursor.xpoint";
    public static final String MARKER_CURSOR_YPOINT = "marker.cursor.ypoint";
    public static final String MARKER_BASIC_STROKE = "marker.basic.stroke";

    public static final String BRUSH_CURSOR_PATH = "brush.cursor.path";
    public static final String BRUSH_CURSOR_XPOINT = "brush.cursor.xpoint";
    public static final String BRUSH_CURSOR_YPOINT = "brush.cursor.ypoint";
    public static final String BRUSH_BASIC_STROKE = "brush.basic.stroke";

    public static final String ERASER_CURSOR_PATH = "eraser.cursor.path";
    public static final String ERASER_CURSOR_XPOINT = "eraser.cursor.xpoint";
    public static final String ERASER_CURSOR_YPOINT = "eraser.cursor.ypoint";
    public static final String ERASER_BASIC_STROKE = "eraser.basic.stroke";
    public static final String ERASER_XPOINT_OFFSET = "eraser.xpoint.offset";
    public static final String ERASER_YPOINT_OFFSET = "eraser.ypoint.offset";

    public static final String RAG_CURSOR_PATH = "rag.cursor.path";
    public static final String RAG_CURSOR_XPOINT = "rag.cursor.xpoint";
    public static final String RAG_CURSOR_YPOINT = "rag.cursor.ypoint";
    public static final String RAG_BASIC_STROKE = "rag.basic.stroke";

    public static final String FILL_CURSOR_PATH = "fill.cursor.path";
    public static final String FILL_CURSOR_XPOINT = "fill.cursor.xpoint";
    public static final String FILL_CURSOR_YPOINT = "fill.cursor.ypoint";
    public static final String FILL_BASIC_STROKE = "fill.basic.stroke";

    public static final String FIGURE_BASIC_STROKE = "figure.basic.stroke";

    public static final String TEXT_BASIC_STROKE = "text.basic.stroke";
    public static final String TEXT_BASIC_FONT = "text.basic.font";
    public static final String TEXT_BASIC_FONT_SIZE = "text.basic.fontsize";
    public static final String TEXT_BASIC_FONT_STYLE = "text.basic.fontstyle";
    public static final String TEXT_BASIC_OFFSET = "text.basic.offset";

    public static final String PENCIL_ICON_PATH = "pencil.icon.path";
    public static final String MARKER_ICON_PATH = "marker.icon.path";
    public static final String BRUSH_ICON_PATH = "brush.icon.path";
    public static final String ERASER_ICON_PATH = "eraser.icon.path";
    public static final String RAG_ICON_PATH = "rag.icon.path";
    public static final String LINE_ICON_PATH = "line.icon.path";
    public static final String DOTTEDLINE_ICON_PATH = "dottedLine.icon.path";
    public static final String CIRCLE_ICON_PATH = "circle.icon.path";
    public static final String RECT_ICON_PATH = "rect.icon.path";
    public static final String PYRAMID_ICON_PATH = "pyramid.icon.path";
    public static final String PRISM_ICON_PATH = "prism.icon.path";
    public static final String FILL_ICON_PATH = "fill.icon.path";
    public static final String TEXT_ICON_PATH = "text.icon.path";
    public static final String UNDO_ICON_PATH = "undo.icon.path";
    public static final String REDO_ICON_PATH = "redo.icon.path";
    public static final String CALCULATOR_ICON_PATH = "calculator.icon.path";

    public static final String TOOL_BUTTON_XPOINT = "toolButtons.main.xPoint";
    public static final String TOOL_BUTTON_YPOINT = "toolButtons.main.yPoint";
    public static final String TOOL_BUTTON_SIZE = "toolButtons.main.size";
    public static final String TOOL_BUTTON_OFFSET = "toolButtons.main.offset";

    public static final String COLOR_BUTTON_XPOINT = "colorButtons.main.xPoint";
    public static final String COLOR_BUTTON_YPOINT = "colorButtons.main.yPoint";
    public static final String COLOR_BUTTON_SIZE = "colorButtons.main.size";
    public static final String COLOR_BUTTON_OFFSET = "colorButtons.main.offset";

    public static final String FUNCTION_BUTTON_XPOINT = "functionButtons.main.xPoint";
    public static final String FUNCTION_BUTTON_YPOINT = "functionButtons.main.yPoint";
    public static final String FUNCTION_BUTTON_SIZE = "functionButtons.main.size";
    public static final String FUNCTION_BUTTON_OFFSET = "functionButtons.main.offset";



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