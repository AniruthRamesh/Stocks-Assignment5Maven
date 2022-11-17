package controller;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import command.Command;
import command.HandleCostBasis;
import command.HandleFastForwardTime;
import command.HandleMutablePortfolioCreation;
import command.HandlePortfolioComposition;
import command.HandlePortfolioCreation;
import command.HandleSellPortfolio;
import command.HandleShowPortfolio;
import command.HandleTotalStockForFlexiblePortfolio;
import command.HandleTotalStockValueDisplay;
import command.HandleUploadFile;
import model.Model;
import view.View;

/**
 * This class implements the Controller.Controller interface. It has two main fields of objects
 * of classes Model.Model,View.View. This class tells the model to perform calculations,
 * store results and this class tells the view to display certain information.
 */
public class ControllerImpl implements Controller {
  Scanner sc;
  View view;
  Model model;
  Command command;

  /**
   * Constructor for the Controller.ControllerImpl class. Initializes the field viewer,models,sc
   * with
   * appropriate parameters.
   *
   * @param models Model.Model object.
   * @param viewer View.View object.
   * @param in     InputStream object,main method gives System.in,ByteArrayInputStream is used
   *               for test.
   */
  public ControllerImpl(Model models, View viewer, InputStream in) {
    this.model = models;
    this.view = viewer;
    this.sc = new Scanner(in);
  }

  /**
   * It takes in the user's choice and executes the corresponding command.
   */
  public void start() {
    model.getContentsFromFile();
    model.makeListOfDates();

    boolean initialOptions = false;
    while (!initialOptions) {
      List<String> currInitialOptions = model.getInitialOptions();
      for (int i = 0; i < currInitialOptions.size(); i++) {
        view.displayWhatIsInParameter(i + 1, currInitialOptions.get(i));
      }

      int choice;
      try {
        choice = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      switch (choice) {
        case 1:
          command = new HandlePortfolioCreation(model, view, sc);
          break;
        case 2:
          command = new HandlePortfolioComposition(model, view, sc);
          break;
        case 3:
          command = new HandleFastForwardTime(model, view, sc);
          break;
        case 4:
          command = new HandleTotalStockValueDisplay(model, view, sc);
          break;
        case 5:
          command = new HandleUploadFile(model, view, sc);
          break;
        case 6:
          command = new HandleShowPortfolio(model, view, sc);
          break;
        case 7:
          command = new HandleMutablePortfolioCreation(model, view, sc);
          break;
        case 8:
          command = new HandleSellPortfolio(model, view, sc);
          break;
        case 9:
          command = new HandleCostBasis(model, view, sc);
          break;
        case 10:
          command = new HandleTotalStockForFlexiblePortfolio(model, view, sc);
          break;
        case 11:
          initialOptions = true;
          break;
        default:
          command = null;
          view.displaySwitchCaseDefault();
          break;
      }
      if (!(command == null) && !initialOptions) {
        model = command.execute();
      }
    }
  }
}
