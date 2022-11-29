package command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Model;
import view.View;

/**
 * This class handles creating a new Strategy plan and adding stocks in the portfolio.
 */
public class HandleStrategy implements Command {
  Scanner sc;
  View view;
  Model model;
  List<String> tickers;
  List<Double> percentage;
  Double percentageSoFar;
  String startDate;
  String endDate;
  Double amount;

  String myd;
  int duration;
  String portfolioName;

  /**
   * Constructor for this class. Gets what model, view, scanner to use and processes
   * accordingly.
   *
   * @param model Model object.
   * @param view  View object.
   * @param sc    Scanner object.
   */
  public HandleStrategy(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
    this.tickers = new ArrayList<>();
    this.percentage = new ArrayList<>();
    this.percentageSoFar = 0.0;
    startDate = "";
    endDate = "";
    myd = "";
    amount = 0.0;
    duration = 0;
    portfolioName = "";
  }

  @Override
  public Model execute() {
    boolean exit = false;
    while (!exit) {
      view.displayContentsInParameter("1. Enter name of the Portfolio.\n2. Exit");
      int input;
      try {
        input = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      if (input == 1) {
        handlingStrategy();
        if (tickers.size() == 0) {
          continue;
        }
        view.displayContentsInParameter("Enter start date first and then end date");
        boolean askEndDate = true;
        while (startDate.length() == 0 && askEndDate) {
          DateHelper date = new DateHelper(view, model, sc);
          startDate = date.helper();
          if (startDate.equals("")) {
            view.displayDateIsNotValid();
            continue;
          }
          view.displayContentsInParameter("Do you want to enter end date?");
          view.displayContentsInParameter("Enter Y for yes, N for no.");
          sc.nextLine();
          String askingEndDate = sc.nextLine();
          if (askingEndDate.equals("Y") || askingEndDate.equals("N")) {
            if (askingEndDate.equals("N")) {
              askEndDate = false;
              continue;
            }
          } else {
            view.displayContentsInParameter("Enter valid Details and please enter Start date " +
                    "again");
            startDate = "";
            continue;
          }
          endDate = date.helper();
          if (endDate.equals("")) {
            view.displayDateIsNotValid();
          }
        }
        //add the logic for getting months, years,days etc.
        while (myd.length() == 0) {
          view.displayContentsInParameter("Enter D for days, Enter M for months, " +
                  "Enter Y for years");
          //sc.nextLine();
          myd = sc.nextLine();
          if (myd.equals("D") || myd.equals("M") || myd.equals("Y")) {
            try {
              view.displayContentsInParameter("Enter the duration:");
              duration = sc.nextInt();
            } catch (InputMismatchException e) {
              view.displayOnlyIntegers();
              sc.next();
              continue;
            }
            if (myd.equals("D")) {
              if (duration > 31) {
                view.displayContentsInParameter("Please use Months");
                myd = "";
              }
            } else if (myd.equals("M")) {
              if (duration > 12) {
                view.displayContentsInParameter("Please use Years");
                myd = "";
              }
            }
          } else if (myd.length() != 0) {
            myd = "";
            view.displayContentsInParameter("Enter valid option");
          }
        }
        handleAddingStocks();
        initializer();
        model.saveFlexiblePortfolios();
      } else if (input == 2) {
        exit = true;
      } else {
        view.displaySwitchCaseDefault();
      }
    }

    return model;
  }

  void handlingStrategy() {
    try {
      sc.nextLine();
      portfolioName = sc.nextLine();
    } catch (Exception e) {
      return;
    }
    boolean portfolioContainsTheName = model.flexiblePortContainsCertainKey(portfolioName);
    if (!portfolioContainsTheName) {
      view.displayNoSuchPortfolio();
      return;
    }
    view.displayContentsInParameter("Enter the Amount");
    try {
      amount = sc.nextDouble();
    } catch (InputMismatchException e) {
      view.displayContentsInParameter("Enter valid number");
      sc.next();
      return;
    }
    if (amount <= 0) {
      view.displayContentsInParameter("Amount cannot be negative or zero.");
      return;
    }

    boolean tickerExit = false;
    // A while loop that keeps on running until the condition is false.
    while (!tickerExit) {
      int option1 = 0;

      view.displayContentsInParameter("1. Enter investment details\n2. Exit");
      try {
        option1 = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      if (option1 == 1) {
        handleGettingTickerDetails();
      } else if (option1 == 2) {
        return;
      } else {
        view.displaySwitchCaseDefault();
      }
    }
  }

  void handleGettingTickerDetails() {
    String companyName;
    view.displayContentsInParameter("Enter the ticker symbol to invest");
    sc.nextLine();
    companyName = sc.nextLine();
    if (!model.checkIfTickerExists(companyName)) {
      String mission = model.addApiCompanyStockData(companyName);
      if (mission.equals("failure")) {
        view.displayCompanyTickerSymbolIsNotValid();
        return;
      }
      HashMap<String, String> stockData;
      stockData = model.convertingStringToHashMap(mission);

      model.addStockDataToFlexibleList(stockData);
      int num = model.getApiStockDataSize();
      model.putNameInCompanyInPortfolio(companyName);
      model.putCompanyNameInTickerFinder(companyName, num - 1);
    }
    view.displayContentsInParameter("Enter the percentage");
    Double percent;
    try {
      percent = sc.nextDouble();
    } catch (InputMismatchException e) {
      view.displayContentsInParameter("Please Enter a valid number");
      sc.next();
      return;
    }
    if (percent > 100 || percent + percentageSoFar > 100) {
      view.displayContentsInParameter("Percentage cannot exceed 100%. " +
              "Please Enter a valid percent.");
      return;
    }

    this.percentageSoFar += percent;
    this.tickers.add(companyName);
    this.percentage.add(percent);
  }

  void handleAddingStocks() {
    List<String> ticker = tickers;
    List<Double> percent = percentage;
    if (ticker.size() == 0) {
      return;
    }
    if (startDate.length() == 0) {
      return;
    }
    LocalDate endingDate;
    if (endDate.length() != 0) {
      if (endDate.compareTo(startDate) >= 0) {
        view.displayContentsInParameter("End date cannot be same or equal to start date.");
        initializer();
        return;
      }
      if (LocalDate.parse(endDate).isAfter(LocalDate.now())) {
        view.displayContentsInParameter("No data after this " + LocalDate.now() + " date");
        return;
      }
      endingDate = LocalDate.parse(endDate);
    } else {
      endingDate = LocalDate.now();
    }
    LocalDate currDate = LocalDate.parse(startDate);
    while (currDate.isBefore(endingDate)) {
      for (int i = 0; i < ticker.size(); i++) {
        String companyName = ticker.get(i);
        HashMap<String, String> stockData;
        //getting the stock data and storing in stockData
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

        //adding 1 day if stockData doesnt contain the following date.
        while (currDate.isBefore(endingDate) && !stockData.containsKey(currDate.toString())) {
          currDate = currDate.plusDays(1);
        }
        if (currDate.isAfter(endingDate) || currDate.isEqual(endingDate)) {
          view.displayContentsInParameter("Cannot proceed");
          return;
        }
        //System.out.println(stockData.get(currDate.toString()));

        Double stockPrice = Double.valueOf(stockData.get(currDate.toString()));
        Double share = amount * (percent.get(i) / 100.0);
        Double numberOfStocks = share / stockPrice;
        Double commission = 0.1;
        String alreadyExisting = ticker.get(i);

        Double totalPrice = stockPrice * numberOfStocks;
        commission *= totalPrice;
        Map<String, Map<String, List<List<String>>>> flexible = model.getFlexiblePort();
        if (flexible.containsKey(portfolioName)) {
          Map<String, List<List<String>>> portfolio = flexible.get(portfolioName);
          if (portfolio.containsKey(alreadyExisting)) {
            model.setFlexibleAddPortfolio(portfolioName, alreadyExisting,
                    List.of("Buy", companyName, String.valueOf(numberOfStocks), currDate.toString(),
                            String.format("%.2f", commission), String.format("%.2f", totalPrice)
                    ));
          } else {
            model.setFlexiblePortfolioWith(portfolioName, alreadyExisting,
                    List.of("Buy", companyName, String.valueOf(numberOfStocks), currDate.toString(),
                            String.format("%.2f", commission), String.format("%.2f", totalPrice)
                    ));
          }
        } else {
          Map<String, List<List<String>>> val = new HashMap<>();
          val.put(alreadyExisting, List.of(List.of("Buy",
                  companyName, String.valueOf(numberOfStocks), currDate.toString(),
                  String.format("%.2f", commission), String.format("%.2f", totalPrice))));
          model.setFlexibleNewPortfolio(portfolioName, val);
        }

      }
      if (myd.equals("Y")) {
        currDate = currDate.plusYears(duration);
      } else if (myd.equals("D")) {
        currDate = currDate.plusDays(duration);
      } else if (myd.equals("M")) {
        currDate = currDate.plusMonths(duration);
      }
    }

  }

  void initializer() {
    this.tickers = new ArrayList<>();
    this.percentage = new ArrayList<>();
    this.percentageSoFar = 0.0;
    startDate = "";
    endDate = "";
    amount = 0.0;
  }
}
