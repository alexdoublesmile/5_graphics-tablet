package config;


import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String PROPERTIES_FILE_NAME = "settings.properties";

    public static final String PENCIL_CURSOR_PATH = "pencil.cursor.path";
    public static final String PENCIL_CURSOR_XPOINT = "pencil.cursor.x.point";
    public static final String PENCIL_CURSOR_YPOINT = "pencil.cursor.y.point";
    public static final String PENCIL_BASIC_STROKE = "pencil.basic.stroke";

    public static final String MARKER_CURSOR_PATH = "marker.cursor.path";
    public static final String MARKER_CURSOR_XPOINT = "marker.cursor.x.point";
    public static final String MARKER_CURSOR_YPOINT = "marker.cursor.y.point";
    public static final String MARKER_BASIC_STROKE = "marker.basic.stroke";

    public static final String BRUSH_CURSOR_PATH = "brush.cursor.path";
    public static final String BRUSH_CURSOR_XPOINT = "brush.cursor.x.point";
    public static final String BRUSH_CURSOR_YPOINT = "brush.cursor.y.point";
    public static final String BRUSH_BASIC_STROKE = "brush.basic.stroke";

    public static final String ERASER_CURSOR_PATH = "eraser.cursor.path";
    public static final String ERASER_CURSOR_XPOINT = "eraser.cursor.x.point";
    public static final String ERASER_CURSOR_YPOINT = "eraser.cursor.y.point";
    public static final String ERASER_BASIC_STROKE = "eraser.basic.stroke";
    public static final String ERASER_XPOINT_OFFSET = "eraser.x.point.offset";
    public static final String ERASER_YPOINT_OFFSET = "eraser.y.point.offset";

    public static final String RAG_CURSOR_PATH = "rag.cursor.path";
    public static final String RAG_CURSOR_XPOINT = "rag.cursor.x.point";
    public static final String RAG_CURSOR_YPOINT = "rag.cursor.y.point";
    public static final String RAG_BASIC_STROKE = "rag.basic.stroke";

    public static final String FILL_CURSOR_PATH = "fill.cursor.path";
    public static final String FILL_CURSOR_XPOINT = "fill.cursor.x.point";
    public static final String FILL_CURSOR_YPOINT = "fill.cursor.y.point";
    public static final String FILL_BASIC_STROKE = "fill.basic.stroke";

    public static final String HAND_CURSOR_PATH = "hand.cursor.path";
    public static final String GRAB_CURSOR_PATH = "grab.cursor.path";
    public static final String POINTER_CURSOR_PATH = "pointer.cursor.path";

    public static final String FIGURE_BASIC_STROKE = "figure.basic.stroke";

    public static final String TEXT_BASIC_STROKE = "text.basic.stroke";
    public static final String TEXT_BASIC_FONT = "text.basic.font";
    public static final String TEXT_BASIC_FONT_SIZE = "text.basic.font.size";
    public static final String TEXT_BASIC_FONT_STYLE = "text.basic.font.style";
    public static final String TEXT_BASIC_OFFSET = "text.basic.offset";

    public static final String PENCIL_ICON_PATH = "pencil.icon.path";
    public static final String MARKER_ICON_PATH = "marker.icon.path";
    public static final String BRUSH_ICON_PATH = "brush.icon.path";
    public static final String ERASER_ICON_PATH = "eraser.icon.path";
    public static final String RAG_ICON_PATH = "rag.icon.path";
    public static final String LINE_ICON_PATH = "line.icon.path";
    public static final String DOTTEDLINE_ICON_PATH = "dotted.line.icon.path";
    public static final String ARROW_ICON_PATH = "arrow.icon.path";
    public static final String CIRCLE_ICON_PATH = "circle.icon.path";
    public static final String ELLIPSE_ICON_PATH = "ellipse.icon.path";
    public static final String SQUARE_ICON_PATH = "square.icon.path";
    public static final String RECT_ICON_PATH = "rect.icon.path";
    public static final String PARALLELOGRAM_ICON_PATH = "parallelogram.icon.path";
    public static final String POLYGON_ICON_PATH = "polygon.icon.path";
    public static final String PYRAMID_ICON_PATH = "tri.pyramid.icon.path";
    public static final String PYRAMID_TETRA_ICON_PATH = "tetra.pyramid.icon.path";
    public static final String PYRAMID_CUSTOM_ICON_PATH = "pyramid.custom.icon.path";
    public static final String PRISM_ICON_PATH = "prism.icon.path";
    public static final String PARALLELEPIPED_ICON_PATH = "parallelepiped.icon.path";
    public static final String PRISM_CUSTOM_ICON_PATH = "prism.custom.icon.path";
    public static final String CONE_ICON_PATH = "cone.icon.path";
    public static final String CYLINDER_ICON_PATH = "cylinder.icon.path";
    public static final String SPHERE_ICON_PATH = "sphere.icon.path";
    public static final String FRUSTUM_ICON_PATH = "frustum.icon.path";
    public static final String FILL_ICON_PATH = "fill.icon.path";
    public static final String TEXT_ICON_PATH = "text.icon.path";
    public static final String HAND_ICON_PATH = "hand.icon.path";
    public static final String MOVE_ICON_PATH = "move.icon.path";
    public static final String COPY_ICON_PATH = "copy.icon.path";
    public static final String CUT_ICON_PATH = "cut.icon.path";
    public static final String PASTE_ICON_PATH = "paste.icon.path";
    public static final String PLUS_ICON_PATH = "plus.icon.path";
    public static final String MINUS_ICON_PATH = "minus.icon.path";
    public static final String UNDO_ICON_PATH = "undo.icon.path";
    public static final String REDO_ICON_PATH = "redo.icon.path";
    public static final String CALCULATOR_ICON_PATH = "calculator.icon.path";
    public static final String PALETTE_ICON_PATH = "palette.icon.path";

    public static final String TOOL_BUTTON_XPOINT = "tool.buttons.main.x.point";
    public static final String TOOL_BUTTON_YPOINT = "tool.buttons.main.y.point";
    public static final String TOOL_BUTTON_SIZE = "tool.buttons.main.size";
    public static final String TOOL_BUTTON_OFFSET = "tool.buttons.main.offset";

    public static final String COLOR_BUTTON_XPOINT = "color.buttons.main.x.point";
    public static final String COLOR_BUTTON_YPOINT = "color.buttons.main.y.point";
    public static final String COLOR_BUTTON_SIZE = "color.buttons.main.size";
    public static final String COLOR_BUTTON_OFFSET = "color.buttons.main.offset";

    public static final String FUNCTION_BUTTON_XPOINT = "function.buttons.main.x.point";
    public static final String FUNCTION_BUTTON_YPOINT = "function.buttons.main.y.point";
    public static final String FUNCTION_BUTTON_SIZE = "function.buttons.main.size";
    public static final String FUNCTION_BUTTON_OFFSET = "function.buttons.main.offset";

    public static final String UNDO_ACTIONS_QUANTITY = "undo.actions.quantity";

    public static final String RESIZE_PLUS_FACTOR = "resize.plus.factor";
    public static final String RESIZE_MINUS_FACTOR = "resize.minus.factor";

    public static final String MAIN_FRAME_WIDTH = "main.frame.width";
    public static final String MAIN_FRAME_HEIGHT = "main.frame.height";

    public static final String COLOR_DIALOG_WIDTH = "color.dialog.width";
    public static final String COLOR_DIALOG_HEIGHT = "color.dialog.height";

    public static final String BASIC_STROKE_WIDTH = "basic.stroke.width";
    public static final String BASIC_STROKE_LENGHT = "basic.stroke.lenght";
    public static final String BASIC_STROKE_OFFSET_LENGHT = "basic.stroke.offset.lenght";

    public static final String INDICATOR_WIDTH = "indicator.width";
    public static final String INDICATOR_TOP_OFFSET = "indicator.top.offset";
    public static final String INDICATOR_BOTTOM_OFFSET = "indicator.bottom.offset";

    public static final String PARALLELOGRAM_FACTOR = "parallelogram.factor";

    public static final String POLYGON_ACCURACY_FACTOR = "polygon.accuracy.factor";

    public static final String PYRAMID_MAIN_LINE_FACTOR = "tri.pyramid.main.line.factor";
    public static final String PYRAMID_LEFT_XFACTOR = "tri.pyramid.left.x.factor";
    public static final String PYRAMID_LEFT_YFACTOR = "tri.pyramid.left.y.factor";
    public static final String PYRAMID_RIGHT_XFACTOR = "tri.pyramid.right.x.factor";
    public static final String PYRAMID_RIGHT_YFACTOR = "tri.pyramid.right.y.factor";

    public static final String TETRA_PYRAMID_BACK_LEFT_XFACTOR = "tetra.pyramid.back.left.x.factor";
    public static final String TETRA_PYRAMID_BACK_LEFT_YFACTOR = "tetra.pyramid.back.left.y.factor";
    public static final String TETRA_PYRAMID_BACK_RIGHT_XFACTOR = "tetra.pyramid.back.right.x.factor";
    public static final String TETRA_PYRAMID_BACK_RIGHT_YFACTOR = "tetra.pyramid.back.right.y.factor";
    public static final String TETRA_PYRAMID_FRONT_LEFT_XFACTOR = "tetra.pyramid.front.left.x.factor";
    public static final String TETRA_PYRAMID_FRONT_RIGHT_XFACTOR = "tetra.pyramid.front.right.x.factor";

    public static final String CUSTOM_PYRAMID_GROW_FACTOR = "custom.pyramid.grow.factor";
    public static final String CUSTOM_PYRAMID_TOP_PADDING = "custom.pyramid.top.padding";

    public static final String TRI_PRISM_LEFT_TOP_XFACTOR = "tri.prism.left.top.x.factor";
    public static final String TRI_PRISM_LEFT_TOP_YFACTOR = "tri.prism.left.top.y.factor";
    public static final String TRI_PRISM_RIGHT_TOP_XFACTOR = "tri.prism.right.top.x.factor";
    public static final String TRI_PRISM_RIGHT_TOP_YFACTOR = "tri.prism.right.top.y.factor";
    public static final String TRI_PRISM_LEFT_BOTTOM_XFACTOR = "tri.prism.left.bottom.x.factor";
    public static final String TRI_PRISM_LEFT_BOTTOM_YFACTOR = "tri.prism.left.bottom.y.factor";
    public static final String TRI_PRISM_RIGHT_BOTTOM_XFACTOR = "tri.prism.right.bottom.x.factor";
    public static final String TRI_PRISM_RIGHT_BOTTOM_YFACTOR = "tri.prism.right.bottom.y.factor";

    public static final String PARALLELEPIPED_SIDE_ANGLE_FACTOR = "parallelepiped.side.angle.factor";
    public static final String PARALLELEPIPED_FRONT_ANGLE_FACTOR = "parallelepiped.front.angle.factor";
    public static final String PARALLELEPIPED_CURVE_FACTOR = "parallelepiped.curve.factor";

    public static final String CUSTOM_PRISM_GROW_FACTOR = "custom.prism.grow.factor";

    public static final String CONE_GROW_FACTOR = "cone.grow.factor";
    public static final String CONE_DOTTED_ARC_START_ANGLE = "cone.dotted.arc.start.angle";
    public static final String CONE_DOTTED_ARC_FINAL_ANGLE = "cone.dotted.arc.final.angle";
    public static final String CONE_LINED_ARC_START_ANGLE = "cone.lined.arc.start.angle";
    public static final String CONE_LINED_ARC_FINAL_ANGLE = "cone.lined.arc.final.angle";
    public static final String CONE_LEFT_BUG_FACTOR = "cone.left.bug.factor";
    public static final String CONE_RIGHT_BUG_FACTOR = "cone.right.bug.factor";

    public static final String CYLINDER_GROW_FACTOR = "cylinder.grow.factor";
    public static final String CYLINDER_DOTTED_ARC_START_ANGLE = "cylinder.dotted.arc.start.angle";
    public static final String CYLINDER_DOTTED_ARC_FINAL_ANGLE = "cylinder.dotted.arc.final.angle";
    public static final String CYLINDER_LINED_ARC_START_ANGLE = "cylinder.lined.arc.start.angle";
    public static final String CYLINDER_LINED_ARC_FINAL_ANGLE = "cylinder.lined.arc.final.angle";
    public static final String CYLINDER_TOP_LEFT_BUG_FACTOR = "cylinder.top.left.bug.factor";
    public static final String CYLINDER_TOP_RIGHT_BUG_FACTOR = "cylinder.top.right.bug.factor";
    public static final String CYLINDER_RIGHT_LINE_BUG_CONSTANT = "cylinder.right.line.bug.constant";






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