package controller.swing.menuActions;

import model.Model;
import util.TextFileFilter;
import view.swing.SwingViewImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileAction {
    private static final String OPEN_MENU_TITLE = "Открыть";
    private static final String SAVE_MENU_TITLE = "Сохранить";
    private static final String SAVE_AS_MENU_TITLE = "Сохранить как...";

    private static final String FILE_NOT_FOUND_MESSAGE = "Такого файла не существует";
    private static final String IOEXCEPTION_MESSAGE = "Исключение ввода-вывода";
    private static final String DEFAULT_EXCEPTION_MESSAGE = "Что-то пошло не так.\n\nДетали:\n";

    private final TextFileFilter PNG_FILTER;
    private final TextFileFilter JPG_FILTER;

    private final OpenMenuAction LOAD_MENU_ACTION;
    private final SaveMenuAction SAVE_MENU_ACTION;
    private final SaveAsMenuAction SAVE_AS_MENU_ACTION;

    private SwingViewImpl view;
    private Model model;

    private JFrame frame;
    private JPanel panel;
    private BufferedImage image;
    private JFileChooser fileChooser;
    private int chooseValue;
    private String filePath;

    public FileAction(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;

        PNG_FILTER = new TextFileFilter(TextFileFilter.getPngFormat());
        JPG_FILTER = new TextFileFilter(TextFileFilter.getJpgFormat());

        frame = view.getMainFrame();
        panel = view.getMainPanel();
        fileChooser = view.getFileChooser();
        fileChooser.addChoosableFileFilter(PNG_FILTER);
        fileChooser.addChoosableFileFilter(JPG_FILTER);

        LOAD_MENU_ACTION = new OpenMenuAction();
        SAVE_MENU_ACTION = new SaveMenuAction();
        SAVE_AS_MENU_ACTION = new SaveAsMenuAction();
    }

    private class OpenMenuAction extends AbstractAction {
        public OpenMenuAction() { super(OPEN_MENU_TITLE); }

        @Override
        public void actionPerformed(ActionEvent e) {

            chooseValue = fileChooser.showOpenDialog(null);
            if (chooseValue == JFileChooser.APPROVE_OPTION) {
                try {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    image = ImageIO.read(new File(filePath));
                    view.setMainImage(image);
                    model.setFileName(filePath);

                    model.setLoading(true);
//                    frame.setSize(image.getWidth(), image.getHeight());
//                    panel.setSize(image.getWidth(), image.getHeight());
                    view.setTitle(filePath);


                    model.saveAction(view.getMainImage());
                    view.saveCurrentImage();
                    panel.repaint();

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(view.getMainFrame(),
                            FILE_NOT_FOUND_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view.getMainFrame(),
                            IOEXCEPTION_MESSAGE);
                } catch (Exception ex) {
                    String errorString = String.format(DEFAULT_EXCEPTION_MESSAGE + ex);
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view.getMainFrame(), errorString);
                }
            }
        }
    }

    private class SaveMenuAction extends AbstractAction {
        public SaveMenuAction() { super(SAVE_MENU_TITLE); }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (model.getFileName() == null) {
                    chooseValue = fileChooser.showSaveDialog(null);
                    if (chooseValue == JFileChooser.APPROVE_OPTION) {
                        model.setFileName(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                }

                writeToFile();
        }
    }

    private class SaveAsMenuAction extends AbstractAction {
        public SaveAsMenuAction() { super(SAVE_AS_MENU_TITLE); }

        @Override
        public void actionPerformed(ActionEvent e) {
                chooseValue = fileChooser.showSaveDialog(null);
                if (chooseValue == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                }

                writeToFile();
        }
    }

    private void writeToFile() {
        try {
            if (fileChooser.getFileFilter() == PNG_FILTER) {
                ImageIO.write(
                        view.getMainImage(),
                        PNG_FILTER.getFileFormat(),
                        new  File(model.getFileName() + PNG_FILTER.getFileResolution()));
            }
            else {
                ImageIO.write(view.getMainImage(),
                        JPG_FILTER.getFileFormat(),
                        new  File(model.getFileName() + JPG_FILTER.getFileResolution()));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, IOEXCEPTION_MESSAGE);
        }
    }

    public OpenMenuAction getLOAD_MENU_ACTION() {
        return LOAD_MENU_ACTION;
    }

    public SaveMenuAction getSAVE_MENU_ACTION() {
        return SAVE_MENU_ACTION;
    }

    public SaveAsMenuAction getSAVE_AS_MENU_ACTION() {
        return SAVE_AS_MENU_ACTION;
    }
}
