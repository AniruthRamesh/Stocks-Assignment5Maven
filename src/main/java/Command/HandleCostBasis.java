package Command;

import java.util.ArrayList;
import java.util.List;
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
          view.displayContentsInParameter("Sell         -> " + insideContents.get(1) + " : " +
                  insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n" +
                  "Commission  -> " + insideContents.get(4));
          totalMoneySpent += Double.parseDouble(insideContents.get(4));

        }
        if (insideContents.get(0).equals("Buy")) {
          view.displayContentsInParameter("Buy         -> " + insideContents.get(1) + " : " +
                  insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n"
                  + "Total value -> " + insideContents.get(5) + "\n" + "Commission  -> "
                  + insideContents.get(4));
          totalMoneySpent += Double.parseDouble(insideContents.get(4)) +
                  Double.parseDouble(insideContents.get(5));
        }
        view.displayEmptyLine();
      }
      overallTotalMoneySpent += totalMoneySpent;
      view.displayContentsInParameter("Total       -> " + totalMoneySpent);
    }
    view.displayContentsInParameter("Overall Total -> " + overallTotalMoneySpent);


    return model;
  }

  private String helper(int number) {
    StringBuilder str = new StringBuilder("");
    for (int i = 0; i < number; i++) {
      str.append("_");
    }
    return str.toString();
  }
}
