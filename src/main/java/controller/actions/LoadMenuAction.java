package controller.actions;

import model.PaintModel;
import util.TextFileFilter;
import view.swing.SwingViewImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadMenuAction extends AbstractAction {

    private SwingViewImpl view;
    private PaintModel model;

    private JFrame frame;
    private JPanel panel;
    private BufferedImage image;
    private JFileChooser fileChooser;
    private String fileName;

    public LoadMenuAction(SwingViewImpl view, PaintModel model, String actionName) {
        super(actionName);
        this.view = view;
        this.model = model;

        frame = view.getMainFrame();
        panel = view.getMainPanel();
        fileChooser = view.getFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        fileChooser.addChoosableFileFilter(new TextFileFilter(".png"));
        fileChooser.addChoosableFileFilter(new TextFileFilter(".jpg"));
        int choose = fileChooser.showOpenDialog(null);
        if (choose == JFileChooser.APPROVE_OPTION) {
            try {
                fileName = fileChooser.getSelectedFile().getAbsolutePath();
                File file = new File(fileName);

                image = ImageIO.read(file);
                view.setMainImage(image);
                model.setFileName(fileName);

                model.setLoading(true);
                frame.setSize(image.getWidth(), image.getHeight());
                panel.setSize(image.getWidth(), image.getHeight());
                view.setTitle(fileName);

                model.saveAction(view.getMainImage());
                panel.repaint();

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(view.getMainFrame(),
                        "Такого файла не существует");
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(view.getMainFrame(),
                        "Исключение ввода-вывода");
            }
            catch (Exception ex) {
                String errorString = String.format("Что-то пошло не так.\n\nДетали:\n" + ex.getMessage() + "\n" + ex + "\n");
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view.getMainFrame(), errorString);
            }
        }
    }
}
