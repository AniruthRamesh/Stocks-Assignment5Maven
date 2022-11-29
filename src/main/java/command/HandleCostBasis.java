package command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Model;
import view.View;

/**
 * It's a class which handles calculating the cost basis, and it tells the model to calculate
 * necessary logic and tells view what to display of the cost basis.
 */
public class HandleCostBasis implements Command {
  Model model;
  View view;
  Scanner sc;

  /**
   * Constructor for the HandleCostBasis class. Gets what model, view, scanner to use and processes
   * accordingly.
   *
   * @param model Model object.
   * @param view  View object.
   * @param sc    Scanner object.
   */
  public HandleCostBasis(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.displayEnterNameForPortfolio();
    sc.nextLine();
    String portfolioName = sc.nextLine();
    if (!model.flexiblePortContainsCertainKey(portfolioName)) {
      view.displayNoSuchPortfolio();
      return model;
    }
    DateHelper dateValue = new DateHelper(view, model, sc);
    String date = dateValue.helper();
    if (date.length() == 0) {
      view.displayDateIsNotValid();
      return model;
    }
    List<String> companies = model.getCompaniesInCertainPortfolio(portfolioName);
    view.displayPortfolioName(portfolioName);
    String dash;
    dash = helper(12 + portfolioName.length());
    Double totalAmount = 0.0;
    view.displayContentsInParameter(dash);
    List<String> actualCompanyData = new ArrayList<>();

    for (int i = 0; i < companies.size(); i++) {

      List<List<String>> data = model.getStockDataInCertainPortfolio(portfolioName,
              companies.get(i));
      for (int j = 0; j < data.size(); j++) {
        List<String> insideContents = data.get(j);
        if (date.compareTo(insideContents.get(3)) < 0) {
          continue;
        }
        actualCompanyData.add(companies.get(i));
        break;
      }
    }
    if (actualCompanyData.size() == 0) {
      view.displayContentsInParameter("No stock purchased before this date." + "\n");
      return model;
    }

    Double overallTotalMoneySpent = 0.0;
    Double totalMoneySpent = 0.0;
    for (int i = 0; i < actualCompanyData.size(); i++) {
      totalMoneySpent = 0.0;
      List<List<String>> data = model.getStockDataInCertainPortfolio(portfolioName,
              actualCompanyData.get(i));
      view.displayEmptyLine();
      view.displayContentsInParameter(actualCompanyData.get(i));
      dash = helper(actualCompanyData.get(i).length());
      view.displayContentsInParameter(dash);
      view.displayEmptyLine();
      for (int j = 0; j < data.size(); j++) {
        List<String> insideContents = data.get(j);
        if (date.compareTo(insideContents.get(3)) < 0) {
          continue;
        }
        if (insideContents.get(0).equals("Sell")) {
          view.displayContentsInParameter("Sell         -> " + insideContents.get(1) + " : "
                  + insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n"
                  + "Commission  -> $" + insideContents.get(4));
          totalMoneySpent += Double.parseDouble(insideContents.get(4));

        }
        if (insideContents.get(0).equals("Buy")) {
          view.displayContentsInParameter("Buy         -> " + insideContents.get(1) + " : "
                  + insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n"
                  + "Total value -> $" + insideContents.get(5) + "\n" + "Commission  -> $"
                  + insideContents.get(4));
          totalMoneySpent += Double.parseDouble(insideContents.get(4))
                  + Double.parseDouble(insideContents.get(5));
        }
        view.displayEmptyLine();
      }
      overallTotalMoneySpent += totalMoneySpent;
      view.displayContentsInParameter("Total       -> $" + totalMoneySpent);
    }
    view.displayContentsInParameter("Overall Total -> $" + overallTotalMoneySpent);


    return model;
  }

  /**
   * It takes in a number and returns a string of underscores of that length.
   *
   * @param number the number of underscores to return
   * @return The number of underscores that are needed to fill the space between the left and
   * right side of the tree.
   */
  private String helper(int number) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < number; i++) {
      str.append("_");
    }
    return str.toString();
  }
}
