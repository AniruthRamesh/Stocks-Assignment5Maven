package Command;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

import static java.lang.Math.ceil;

public class HandleSellPortfolio implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleSellPortfolio(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean portfolioOptionExit = false;
    boolean nameEntered = false;
    String name = "";

    while (!portfolioOptionExit) {
      int ans;
      view.displayPortfolioNameMenu();
      ans = sc.nextInt();

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

  public void handleSellPortfolioOptions(String portfolioName) {
    String currentDate = model.getCurrentDate();
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

  public boolean handleEnterTickerSymbol(String name) {

    return model.checkIfTickerExists(name);
  }

  public void handleDateSelection(String portfolioName, String ticker) {
    int choice;
    view.displaySelectDateOption(model.getCurrentDate());
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
    }

    if (choice == 1) {
      int day;
      int month;
      int year;
      view.askForDayOfTheMonth();
      try {
        day = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (day > 31 || day == 0) {
        view.displayEnterValidDetailsForDate();
        return;
      }
      view.askForMonth();
      try {
        month = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (month > 12 || month == 0) {
        view.displayEnterValidDetailsForDate();
        return;
      }
      view.askForYear();
      try {
        year = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (year > 2022 || year < 2001) {
        view.displayEnterValidDetailsForDate();
        return;
      }

      String dateWishToChange = model.makeStringDate(day, month, year);

      boolean checker1 = model.isValidDate(dateWishToChange);
      if (checker1) {
        handleStockForSelling(portfolioName, ticker, dateWishToChange);
      } else {
        view.displayDateIsNotValid();
      }
    } else if (choice == 2) {
      //
    } else {
      view.displaySwitchCaseDefault();
    }
  }

  public void handleStockForSelling(String portfolioName, String ticker,
                                    String dateWishToChange) {
    Map<String, List<List<String>>> portfolioData =
            model.getParticularFlexiblePortfolio(portfolioName);
    Double totalStock = 0.0;
    List<List<String>> tickerData = portfolioData.get(ticker);
    for (int i = 0; i < tickerData.size(); i++) {
      int compareDate =
              LocalDate.parse(tickerData.get(i).get(3)).compareTo(LocalDate.parse(dateWishToChange));
      if (compareDate <= 0) {
        if (tickerData.get(i).get(0).equals("Buy")) {
          totalStock += Double.parseDouble(tickerData.get(i).get(2));
        } else if (tickerData.get(i).get(0).equals("Sell")) {
          totalStock -= Double.parseDouble(tickerData.get(i).get(2));
        }
      }
    }
    if (totalStock == 0) {
      view.displayNoStockToSell();
    } else {
      view.askForNumberOfStocksToSell();
      double stockToSell;
      sc.nextLine();
      stockToSell = ceil(Double.parseDouble(sc.nextLine()));
      if (stockToSell == totalStock || (stockToSell < totalStock && stockToSell >= 0)) {
        totalStock -= stockToSell;
        int index = model.getTickerFinder().get(ticker);
        HashMap<String, String> companyStock = model.getApiStockData().get(index);
        double valueOfStocks = 0.0;
        try {
          valueOfStocks = Double.parseDouble(companyStock.get(dateWishToChange));
        } catch (Exception e) {
          view.displayNoStockDataForGivenDate();
          return;
        }

        double commission = totalStock * 0.1 * valueOfStocks;
//         List<List<String>> val = new HashMap<>();
//        val.put(ticker, List.of(List.of("Sell", ticker, String.valueOf(stockToSell),
//                dateWishToChange, String.valueOf(commission), String.valueOf(totalStock))));
        model.setFlexibleAddPortfolio(portfolioName, ticker, List.of("Sell", ticker,
                String.valueOf(stockToSell),
                dateWishToChange, String.valueOf(commission), String.valueOf(totalStock)));
        view.displayPortfolioUpdated();
      } else {
        view.enterValidStockToSell();
      }
    }
  }
}