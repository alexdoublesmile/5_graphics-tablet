package controller.actions;

import domain.PaintModel;
import util.TextFileFilter;
import view.SwingViewImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveAsMenuAction extends AbstractAction {
    private SwingViewImpl view;
    private PaintModel model;

    private JFrame frame;
    private BufferedImage mainImage;
    private JFileChooser fileChooser;
    private String fileName;

    public SaveAsMenuAction(SwingViewImpl view, PaintModel model, String actionName) {
        super(actionName);
        this.view = view;
        this.model = model;

        frame = view.getMainFrame();
        mainImage = this.view.getMainImage();
        fileChooser = view.getFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            TextFileFilter pngFilter = new TextFileFilter(".png");
            TextFileFilter jpgFilter = new TextFileFilter(".jpg");
            fileChooser.addChoosableFileFilter(pngFilter);
            fileChooser.addChoosableFileFilter(jpgFilter);

            int  choose = fileChooser.showSaveDialog(null);
            if (choose == JFileChooser.APPROVE_OPTION) {
                fileName = fileChooser.getSelectedFile().getAbsolutePath();
            }

            if (fileChooser.getFileFilter() == pngFilter) {
                ImageIO.write(view.getMainImage(), "png", new  File(fileName + ".png"));
            }
            else {
                ImageIO.write(view.getMainImage(), "jpeg", new  File(fileName + ".jpg"));
            }
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(frame, "Ошибка ввода-вывода");
        }
    }
}
