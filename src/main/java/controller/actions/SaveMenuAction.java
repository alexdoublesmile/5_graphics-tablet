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

public class SaveMenuAction extends AbstractAction {
    private SwingViewImpl view;
    private PaintModel model;

    private JFrame frame;
    private BufferedImage mainImage;
    private JFileChooser fileChooser;
    private String fileName;

    public SaveMenuAction(SwingViewImpl view, PaintModel model, String actionName) {
        super(actionName);
        this.view = view;
        this.model = model;

        frame = view.getMainFrame();
        fileChooser = view.getFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            TextFileFilter pngFilter = new TextFileFilter(".png");
            TextFileFilter jpgFilter = new TextFileFilter(".jpg");
            fileChooser.addChoosableFileFilter(pngFilter);
            fileChooser.addChoosableFileFilter(jpgFilter);

            if (model.getFileName() == null) {
                int  result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    model.setFileName(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }

            if (fileChooser.getFileFilter() == pngFilter) {
                ImageIO.write(view.getMainImage(), "png", new  File(model.getFileName() + ".png"));
            }
            else {
                ImageIO.write(view.getMainImage(), "jpeg", new  File(model.getFileName() + ".jpg"));
            }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(frame, "Ошибка ввода-вывода");
        }
    }
}
