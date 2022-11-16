package Command;

import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It's a command that handles the cost basis of a stock.
 */
public class HandleCostBasis implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleCostBasis(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.displayEnterNameForPortfolio();
    String portfolioName = sc.nextLine();
    if (!model.flexiblePortContainsCertainKey(portfolioName)) {
      view.displayNoSuchPortfolio();
      return model;
    }
    DateHelper dateValue = new DateHelper(view, model, sc);
    String date = dateValue.helper();
    if ((date.length() == 0)) {
      System.out.println("check is correct, proceed");
    }

    return model;
  }
}
