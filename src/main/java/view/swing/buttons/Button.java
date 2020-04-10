package view.swing.buttons;

import javax.swing.*;

public abstract class Button extends JButton {

    public Button(ImageIcon icon) {
        super(icon);

        this.setBorderPainted(false);
        this.setFocusPainted(false);
//        this.setContentAreaFilled(false);
    }

    public Button() {

    }



}
