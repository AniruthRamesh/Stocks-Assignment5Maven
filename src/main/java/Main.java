import Controller.Controller;
import Controller.ControllerImpl;
import Model.Model;
import Model.ModelImpl;
import View.View;

/**
 * This is the class which contains main method. Creates an object for view with PrintStream
 * as System.out. Creates an object for Model.Model Interface and provides all these objects
 * as parameter to controller. Calls the controller's start method, which is the starting point
 * in the application.
 */
public class Main {
  /**
   * It creates a new Model.Model, View.View, and Controller.Controller, and then starts the
   * controller.
   *
   * @param args accepts a single argument of type String array
   */
  public static void main(String[] args) {
    Model model = new ModelImpl();
    View view = new View(System.out);
    model.createDirectory();
    Controller controller = new ControllerImpl(model, view, System.in);
    controller.start();

    //change model.getContentsFromFile when building jar

    //we are using object of jsonPackage class and not using SavingDataSource(change this)
    //change the upload file method
    //in the current session files in the folder should be loaded

    //handle upload portfolio checking for flexiblePortfolio
    //handle code duplication for saving and upload

    //change handleUploadFile if the hashmap is changed(if included commission)

    //commission for buying is $25, per stock
  }

}
