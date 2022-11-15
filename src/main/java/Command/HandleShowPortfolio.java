package Command;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It displays the list of portfolios in the model.
 */
public class HandleShowPortfolio implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleShowPortfolio(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.askForFlexibleOrInFlexible();
    int choice = 0;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      sc.next();
    }
    if (choice == 1 || choice == 2) {
      List<String> files = model.getListOfPortfolio(choice);
      if (files == null || files.size() == 0) {
        view.displayNoPortfolio();
      } else {
        for (String file : files) {
          view.displayPortfolioName(file);
        }
        view.displayEmptyLine();
      }
    } else {
      view.displaySwitchCaseDefault();
    }

    return model;
  }
}
