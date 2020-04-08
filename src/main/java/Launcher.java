import controller.Controller;
import controller.SwingControllerImpl;
import domain.PaintModel;
import view.SwingViewImpl;
import view.View;

public class Launcher {
    private static Controller theController;
    private static PaintModel theModel;
    private static View theView;

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        theModel = new PaintModel();
        theView = new SwingViewImpl(theModel);
        theController = new SwingControllerImpl(theView, theModel);
    }
}
