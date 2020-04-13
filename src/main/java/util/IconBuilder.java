package util;

import config.Config;
import model.DrawMode;

import javax.swing.*;
import java.lang.reflect.Field;

public class IconBuilder {
    private static final String ICON_PATH_NAME_PATTERN = "%s_ICON_PATH";
    private static ImageIcon icon;

    public static ImageIcon buildIconByDrawMode(DrawMode drawMode) {
        String drawModeName = drawMode.name();

        String pathName = String.format(ICON_PATH_NAME_PATTERN, drawModeName);

        Field[] configFields = Config.class.getDeclaredFields();
        for (Field field : configFields) {
            if (field.getName().equals(pathName)) {
                try {
                    String fieldValue = (String) field.get(null);
                    icon  = new ImageIcon(IconBuilder.class.getResource(Config.getProperty(fieldValue)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return icon;
    }

    public static ImageIcon buildIconByPath(String path) {
        ImageIcon icon = new ImageIcon(IconBuilder.class.getResource(path));
        return icon;
    }
}
