package view.swing.buttons;

import config.Config;
import util.IconBuilder;
import view.swing.SwingViewImpl;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ButtonComponent extends JComponent {
    private JButton button;
    private JButton menuButton;

    public ButtonComponent(ImageIcon icon) {
        setLayout(new BorderLayout());

        button = new ToolButton(icon);
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight() + 5));
        button.setMaximumSize(getPreferredSize());
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        menuButton = new JButton(IconBuilder.buildIconByPath(
                Config.getProperty(Config.BUTTON_MENU_ICON_PATH)
        ));
        menuButton.setBorderPainted(false);
        menuButton.setFocusPainted(false);
        menuButton.setOpaque(false);
        menuButton.setPreferredSize(new Dimension(icon.getIconWidth(), 5));
        menuButton.setMaximumSize(getPreferredSize());
//        menuButton.setPreferredSize(new Dimension(icon.getIconWidth(), 5));

        add(button, BorderLayout.NORTH);
        add(menuButton, BorderLayout.CENTER);
    }

    public JButton getButton() {
        return button;
    }

    public JButton getMenuButton() {
        return menuButton;
    }
}
