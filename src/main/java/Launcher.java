import controller.Controller;
import controller.swing.SwingControllerImpl;
import model.Model;
import view.swing.SwingViewImpl;
import view.View;

public class Launcher {
    private Controller theController;
    private Model theModel;
    private View theView;

    public static void main(String[] args) {
        new Launcher().execute();
    }

    public void execute() {
        theModel = new Model();
        theView = new SwingViewImpl(theModel);
        theController = new SwingControllerImpl(theView, theModel);
        theController.load();
    }
}
