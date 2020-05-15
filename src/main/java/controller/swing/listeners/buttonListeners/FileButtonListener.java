package controller.swing.listeners.buttonListeners;

import model.Model;
import org.apache.commons.io.FilenameUtils;
import util.ResolutionFileFilter;
import view.swing.SwingViewImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileButtonListener {

    private static final String FILE_NOT_FOUND_MESSAGE = "Такого файла не существует";
    private static final String IOEXCEPTION_MESSAGE = "Исключение ввода-вывода";
    private static final String DEFAULT_EXCEPTION_MESSAGE = "Что-то пошло не так.\n\nДетали:\n";

    private final ResolutionFileFilter PNG_FILTER;
    private final ResolutionFileFilter JPG_FILTER;
    private final OpenButtonListener OPEN_BUTTON_LISTENER;

    private SwingViewImpl view;
    private Model model;
    private JFrame frame;

    private BufferedImage image;
    private JFileChooser fileChooser;
    private int chooseValue;
    private File file;
    private String filePath;
    private String fileName;

    public FileButtonListener(SwingViewImpl view, Model model) {
        this.view = view;
        this.model = model;

        PNG_FILTER = new ResolutionFileFilter(ResolutionFileFilter.getPngFormat());
        JPG_FILTER = new ResolutionFileFilter(ResolutionFileFilter.getJpgFormat());

        frame = view.getMainFrame();
        fileChooser = view.getFileChooser();
        fileChooser.addChoosableFileFilter(PNG_FILTER);
        fileChooser.addChoosableFileFilter(JPG_FILTER);

        OPEN_BUTTON_LISTENER = new OpenButtonListener();
    }

    private class OpenButtonListener implements ActionListener {
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

    public OpenButtonListener getOPEN_BUTTON_LISTENER() {
        return OPEN_BUTTON_LISTENER;
    }
}
