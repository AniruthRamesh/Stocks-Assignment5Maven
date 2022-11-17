package command;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Model;
import view.View;

/**
 * It handles the creation of a mutable portfolio. Stocks can be added or to a new portfolio
 * or add stock to existing portfolio.
 */
public class HandleMutablePortfolioCreation implements Command {
  Model model;
  View view;
  Scanner sc;


  /**
   * Constructor for this class. Gets what model, view, scanner to use and processes
   * accordingly.
   *
   * @param model Model object.
   * @param view  View object.
   * @param sc    Scanner object.
   */
  public HandleMutablePortfolioCreation(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean initialOptions = false;
    String name = "";
    while (!initialOptions) {
      view.displayCreateFlexiblePortfolioMenu();
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
          name = handleFlexiblePortfolioMenu();
          break;
        case 2:
          name = handleFlexiblePortfolioMenu();
          boolean checker = false;
          checker = model.flexiblePortContainsCertainKey(name);
          if (!checker && name.length() != 0) {
            name = "";
            view.displayNoSuchPortfolio();
          }
          break;
        case 3:
          if (name.length() == 0) {
            view.displayNameCannotBeEmpty();
            break;
          }
          handleAddApiCompanyStock(name);
          break;
        case 4:
          initialOptions = true;
          model.saveFlexiblePortfolios();
          //save immutable portfolio (add this in model Interface also)
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }

    }

    return model;
  }

  /**
   * This function displays a menu to the user, and returns the name of the portfolio that the user
   * wants to create.
   *
   * @return The name of the portfolio.
   */
  String handleFlexiblePortfolioMenu() {
    String name = "";
    view.displayPortfolioNameMenu();
    int choice;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return "";
    }
    switch (choice) {
      case 1:
        sc.nextLine();
        name = sc.nextLine();
        break;
      case 2:
        break;
      default:
        view.displaySwitchCaseDefault();
        break;
    }
    return name;
  }

  /**
   * This function is used to add a stock to a portfolio using the API.
   *
   * @param portfolioName The name of the portfolio that the user wants to add the stock to.
   */
  void handleAddApiCompanyStock(String portfolioName) {
    boolean initialOptions = false;
    int choice;
    HashMap<String, String> stockData;
    while (!initialOptions) {
      view.displayAddCompanyStockMenu();
      try {
        choice = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      switch (choice) {
        case 1:
          sc.nextLine();
          String companyName = sc.nextLine();

          if (!model.checkIfTickerExists(companyName)) {
            String mission = model.addApiCompanyStockData(companyName);
            if (mission.equals("failure")) {
              view.displayCompanyTickerSymbolIsNotValid();
              break;
            }
            stockData = model.convertingStringToHashMap(mission);

            model.addStockDataToFlexibleList(stockData);
            int num = model.getApiStockDataSize();
            model.putNameInCompanyInPortfolio(companyName);
            model.putCompanyNameInTickerFinder(companyName, num - 1);
          } else {
            int ind = model.getTickerFinder().get(companyName);
            stockData = model.getApiStockData().get(ind);
          }
          DateHelper date = new DateHelper(view, model, sc);
          String dateVal = date.helper();
          if (dateVal.length() == 0) {
            continue;
          }
          view.askForNumberOfStocks();
          Double numberOfStocks = 0.0;
          try {
            numberOfStocks = sc.nextDouble();
          } catch (InputMismatchException e) {
            view.displayOnlyIntegers();
            sc.next();
            continue;
          }
          if (numberOfStocks <= 0) {
            view.displayStockNumberCannotBeLessThanOrEqualToZero();
            break;
          }
          numberOfStocks = model.helper(numberOfStocks);
          Double commission = 0.1;
          String alreadyExisting = companyName;

          if (stockData.containsKey(dateVal)) {
            Double priceOnThatDate = Double.parseDouble(stockData.get(dateVal));
            Double totalPrice = priceOnThatDate * numberOfStocks;
            commission *= totalPrice;
            Map<String, Map<String, List<List<String>>>> flexible = model.getFlexiblePort();
            if (flexible.containsKey(portfolioName)) {
              Map<String, List<List<String>>> portfolio = flexible.get(portfolioName);
              if (portfolio.containsKey(alreadyExisting)) {
                model.setFlexibleAddPortfolio(portfolioName, alreadyExisting,
                        List.of("Buy", companyName, String.valueOf(numberOfStocks), dateVal,
                                String.format("%.2f", commission), String.format("%.2f", totalPrice)
                        ));
              } else {
                model.setFlexiblePortfolioWith(portfolioName, alreadyExisting,
                        List.of("Buy", companyName, String.valueOf(numberOfStocks), dateVal,
                                String.format("%.2f", commission), String.format("%.2f", totalPrice)
                        ));
              }
            } else {
              Map<String, List<List<String>>> val = new HashMap<>();
              val.put(alreadyExisting, List.of(List.of("Buy",
                      companyName, String.valueOf(numberOfStocks), dateVal,
                      String.format("%.2f", commission), String.format("%.2f", totalPrice))));
              model.setFlexibleNewPortfolio(portfolioName, val);
            }


          } else {
            view.displayNoStockDataForGivenDate();
          }
          break;
        case 2:
          initialOptions = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
  }
}