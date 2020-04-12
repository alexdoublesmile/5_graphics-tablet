import controller.Controller;
import controller.SwingControllerImpl;
import model.PaintModel;
import view.swing.SwingViewImpl;
import view.View;

import javax.swing.*;

public class Launcher {
    private static Controller theController;
    private static PaintModel theModel;
    private static View theView;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Launcher().execute());
    }

    public void execute() {
        theModel = new PaintModel();
        theView = new SwingViewImpl(theModel);
        theController = new SwingControllerImpl(theView, theModel);
    }
}
