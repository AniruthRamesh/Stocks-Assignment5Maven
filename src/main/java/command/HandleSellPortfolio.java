package command;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Model;
import view.View;

import static java.lang.Math.ceil;

/**
 * This class is used to handle the sell portfolio options.
 */
public class HandleSellPortfolio implements Command {
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
  public HandleSellPortfolio(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean portfolioOptionExit = false;
    boolean nameEntered = false;
    String name;

    while (!portfolioOptionExit) {
      int ans;
      view.displayPortfolioNameMenu();
      try {
        ans = sc.nextInt();
      }
      catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }


      switch (ans) {
        case 1:
          name = handleGetPortfolioName();
          if (name.length() != 0) {
            nameEntered = true;
          }
          if (nameEntered) {
            handleSellPortfolioOptions(name);
            portfolioOptionExit = true;
            break;
          }
          break;
        case 2:
          portfolioOptionExit = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
    return model;
  }

  /**
   * This function is used to get the name of the portfolio from the user.
   *
   * @return The name of the portfolio.
   */
  public String handleGetPortfolioName() {
    String name;
    view.displayEnterNameForPortfolio();
    //check if we need to add try catch
    sc.nextLine();
    name = sc.nextLine();

    if (name.length() == 0) {
      view.displayNameCannotBeEmpty();
      name = "";
    }
    if (!model.flexiblePortfolioContainsCertainKey(name)) {
      view.displayNoPortfolio();
      name = "";
    }
    return name;
  }

  /**
   * This function is used to handle the sell portfolio options.
   *
   * @param portfolioName The name of the portfolio that the user wants to sell stocks from.
   */
  public void handleSellPortfolioOptions(String portfolioName) {
    boolean optionExit = false;

    while (!optionExit) {
      int ans;
      view.displayAddCompanyStockMenu();
      ans = sc.nextInt();

      switch (ans) {
        case 1:
          String ticker;
          view.askForTickerSymbol();
          sc.nextLine();
          ticker = sc.nextLine();
          if (handleEnterTickerSymbol(ticker)) {
            handleDateSelection(portfolioName, ticker);
            model.saveFlexiblePortfolios();
          } else {
            view.displayCompanyTickerSymbolIsNotValid();
          }
          break;
        case 2:
          model.saveFlexiblePortfolios();
          optionExit = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }


  }

  /**
   * If the model returns true, then the user has entered a valid ticker symbol.
   *
   * @param name The name of the ticker symbol
   * @return A boolean value.
   */
  public boolean handleEnterTickerSymbol(String name) {
    return model.checkIfTickerExists(name);
  }

  /**
   * This function is used to handle the date selection for the user.
   *
   * @param portfolioName the name of the portfolio that the user wants to sell from
   * @param ticker        the ticker of the stock you want to sell
   */
  public void handleDateSelection(String portfolioName, String ticker) {
    DateHelper helper = new DateHelper(view, model, sc);
    String dateWishToChange = helper.helper();
    if (!dateWishToChange.isEmpty()) {
      handleStockForSelling(portfolioName, ticker, dateWishToChange);
    } else {
      view.displayDateIsNotValid();
    }
  }

  /**
   * This function handles the selling of stocks in a portfolio.
   *
   * @param portfolioName    The name of the portfolio that the user wants to sell stocks from.
   * @param ticker           The ticker symbol of the stock.
   * @param dateWishToChange The date on which the user wants to sell the stocks.
   */
  public void handleStockForSelling(String portfolioName, String ticker,
                                    String dateWishToChange) {
    Map<String, List<List<String>>> portfolioData = new HashMap<>();
    try {
      portfolioData =
              model.getParticularFlexiblePortfolio(portfolioName);
    } catch (NullPointerException e) {
      view.displayNoSuchPortfolio();
      return;
    }
    if (portfolioData == null) {
      view.displayNoSuchPortfolio();
      return;
    }

    Double totalStock = 0.0;
    boolean check = false;
    if (!portfolioData.containsKey(ticker)) {
      view.displayCompanyTickerSymbolIsNotValid();
      return;
    }
    List<List<String>> tickerData = portfolioData.get(ticker);
    for (List<String> tickerDatum : tickerData) {
      int compareDate =
              LocalDate.parse(tickerDatum.get(3)).compareTo(LocalDate.parse(dateWishToChange));
      if (compareDate <= 0) {
        if (tickerDatum.get(0).equals("Buy")) {
          totalStock += Double.parseDouble(tickerDatum.get(2));
        } else if (tickerDatum.get(0).equals("Sell")) {
          totalStock -= Double.parseDouble(tickerDatum.get(2));
        }
      } else {
        if (tickerDatum.get(0).equals("Sell")) {
          check = true;
        }
      }
    }
    if (!check) {
      if (totalStock == 0) {
        view.displayNoStockToSell();
      } else {
        view.askForNumberOfStocksToSell();
        double stockToSell;
        sc.nextLine();

        try {
          stockToSell = ceil(Double.parseDouble(sc.nextLine()));
        } catch (InputMismatchException e) {
          view.displayOnlyIntegers();
          stockToSell = 0.0;
          sc.next();
        }

        if (stockToSell == totalStock || (stockToSell < totalStock && stockToSell >= 0)) {
          totalStock -= stockToSell;
          int index = model.getTickerFinder().get(ticker);
          HashMap<String, String> companyStock = model.getApiStockData().get(index);
          double valueOfStocks;
          try {
            valueOfStocks = Double.parseDouble(companyStock.get(dateWishToChange));
          } catch (Exception e) {
            view.displayNoStockDataForGivenDate();
            return;
          }

          double commission = totalStock * 0.1 * valueOfStocks;
          model.setFlexibleAddPortfolio(portfolioName, ticker, List.of("Sell", ticker,
                  String.valueOf(stockToSell),
                  dateWishToChange, String.valueOf(commission), String.valueOf(totalStock)));
          view.displayPortfolioUpdated();
        } else {
          view.enterValidStockToSell();
        }
      }
    } else {
      view.displayCannotSellStock();
    }

  }
}
