package controller.swing.actions;

import model.Model;
import org.apache.commons.io.FilenameUtils;
import util.ResolutionFileFilter;
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

    private final ResolutionFileFilter PNG_FILTER;
    private final ResolutionFileFilter JPG_FILTER;

    private final OpenMenuAction LOAD_MENU_ACTION;
    private final SaveMenuAction SAVE_MENU_ACTION;
    private final SaveAsMenuAction SAVE_AS_MENU_ACTION;

    private SwingViewImpl view;
    private Model model;

    private JFrame frame;
    private BufferedImage image;
    private JFileChooser fileChooser;
    private int chooseValue;
    private File file;
    private String filePath;
    private String fileName;

    public FileAction(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;

        PNG_FILTER = new ResolutionFileFilter(ResolutionFileFilter.getPngFormat());
        JPG_FILTER = new ResolutionFileFilter(ResolutionFileFilter.getJpgFormat());

        frame = view.getMainFrame();
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
                    file = new File(filePath);
                    image = ImageIO.read(file);

                    view.setMainImage(image);
                    model.setFileName(filePath);

                    model.setLoading(true);
//                    frame.setSize(image.getWidth(), image.getHeight());
//                    panel.setSize(image.getWidth(), image.getHeight());
                    fileName = FilenameUtils.removeExtension(file.getName());
                    view.getTabbedPane().setTitleAt(view.getTabbedPane().getSelectedIndex(), fileName);
                    view.getTabbedPane().getTabComponentAt(view.getTabbedPane().getSelectedIndex()).revalidate();

                    model.saveAction(view.getMainImage(), view.getTabbedPane().getSelectedIndex());
                    view.saveCurrentImage();
                    view.getMainPanel().repaint();

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
                    } else return;
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
                    model.setFileName(fileChooser.getSelectedFile().getAbsolutePath());
                    writeToFile();
                }

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
                ImageIO.write(
                        view.getMainImage(),
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
