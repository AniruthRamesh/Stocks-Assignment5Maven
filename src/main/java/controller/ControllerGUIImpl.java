package controller;

import org.jfree.data.category.DefaultCategoryDataset;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import model.Model;
import view.ViewGui;

import static java.lang.Math.ceil;

public class ControllerGUIImpl implements Features {
  ViewGui view;
  Model model;
  public ControllerGUIImpl(Model model, ViewGui viewGui) {
    this.model = model;
    this.view = viewGui;
    this.view.addFeatures(this);

  }

  @Override
  public void createNewFlexiblePortfolio(JPanel frame, String name, String dayText,
                                         String monthText, String yearText, String tickerText,
                                         String numberText) {

    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0 || yearText.length() == 0 || tickerText.length() == 0 || numberText.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
    } else {
      int day = model.stringToNumber(dayText);
      int month = model.stringToNumber(monthText);
      int year = model.stringToNumber(yearText);
      HashMap<String, String> stockData;
      int numberOfStocks;

      if (model.flexiblePortContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio already exists");
      } else if (day == 0 || month == 0 || year == 0) {
        view.createMessageBox(frame, "Enter numeric values for date");
      } else {
        date = model.makeStringDate(day, month, year);
        if (model.isValidDate(date)) {

          if (!model.checkIfTickerExists(tickerText)) {
            String mission = model.addApiCompanyStockData(tickerText);
            if (mission.equals("failure")) {
              view.createMessageBox(frame, "Enter Valid ticker symbol.");

            }
            stockData = model.convertingStringToHashMap(mission);

            model.addStockDataToFlexibleList(stockData);
            int num = model.getApiStockDataSize();
            model.putNameInCompanyInPortfolio(tickerText);
            model.putCompanyNameInTickerFinder(tickerText, num - 1);
          } else {
            int ind = model.getTickerFinder().get(tickerText);
            stockData = model.getApiStockData().get(ind);
            numberOfStocks = model.stringToNumber(numberText);
            if (numberOfStocks <= 0) {
              view.createMessageBox(frame, "Number of stocks should be numeric. Value" + " cannot" +
                      " be 0" + " " + "or negative");
            } else {
              double commission = 0.1;

              if (stockData.containsKey(date)) {
                double priceOnThatDate = Double.parseDouble(stockData.get(date));
                double totalPrice = priceOnThatDate * numberOfStocks;
                commission *= totalPrice;
                Map<String, Map<String, List<List<String>>>> flexible = model.getFlexiblePort();
                Map<String, List<List<String>>> val = new HashMap<>();
                val.put(tickerText, List.of(List.of("Buy", tickerText,
                        String.valueOf(numberOfStocks), date, String.format("%.2f", commission),
                        String.format("%.2f", totalPrice))));
                model.setFlexibleNewPortfolio(name, val);
                model.saveFlexiblePortfolios();
                view.createMessageBox(frame, "Saved Portfolio");
              } else {
                view.createMessageBox(frame, "No data for given date.");

              }
            }
          }
        } else {
          view.createMessageBox(frame, "Enter valid date.");
        }


      }

    }
  }


  @Override
  public void sellPortfolio(JPanel frame, String name, String dayText, String monthText,
                            String yearText, String tickerText, String numberText) {
    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0 || yearText.length() == 0 || tickerText.length() == 0 || numberText.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
    } else {
      int day = model.stringToNumber(dayText);
      int month = model.stringToNumber(monthText);
      int year = model.stringToNumber(yearText);
      HashMap<String, String> stockData;
      int numberOfStocks;

      if (!model.flexiblePortfolioContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio with this name does not exists");
      } else if (day == 0 || month == 0 || year == 0) {
        view.createMessageBox(frame, "Enter numeric values for date");
      } else {
        date = model.makeStringDate(day, month, year);
        if (model.isValidDate(date)) {

          if (!model.checkIfTickerExists(tickerText)) {
            String mission = model.addApiCompanyStockData(tickerText);
            if (mission.equals("failure")) {
              view.createMessageBox(frame, "Enter Valid ticker symbol.");

            }

          } else {
            Map<String, List<List<String>>> portfolioData = new HashMap<>();
            try {
              portfolioData = model.getParticularFlexiblePortfolio(name);
            } catch (NullPointerException e) {
              view.createMessageBox(frame, "No such portfolio exists.");
              return;
            }
            if (portfolioData == null) {
              view.createMessageBox(frame, "No such portfolio exists.");
            }
            Double totalStock = 0.0;
            boolean check = false;
            if (!portfolioData.containsKey(tickerText)) {
              view.createMessageBox(frame, "Ticker symbol provided dies not exists in this " +
                      "portfolio.");

            }
            List<List<String>> tickerData = portfolioData.get(tickerText);
            for (List<String> tickerDatum : tickerData) {
              int compareDate =
                      LocalDate.parse(tickerDatum.get(3)).compareTo(LocalDate.parse(date));
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
                view.createMessageBox(frame, "No stocks to sell.");

              } else {
                int number = model.stringToNumber(numberText);
                if (number == 0) {
                  view.createMessageBox(frame, "Enter valid numeric value for stocks to sell");
                } else {
                  double stockToSell;
                  stockToSell = ceil(Double.parseDouble(numberText));

                  if (stockToSell == totalStock || (stockToSell < totalStock && stockToSell >= 0)) {
                    totalStock -= stockToSell;
                    int index = model.getTickerFinder().get(tickerText);
                    HashMap<String, String> companyStock = model.getApiStockData().get(index);
                    double valueOfStocks = 0;
                    try {
                      valueOfStocks = Double.parseDouble(companyStock.get(date));
                    } catch (Exception e) {
                      view.createMessageBox(frame, "No data for given date.");

                    }

                    double commission = totalStock * 0.1 * valueOfStocks;
                    model.setFlexibleAddPortfolio(name, tickerText, List.of("Sell", tickerText,
                            String.valueOf(stockToSell), date, String.valueOf(commission),
                            String.valueOf(totalStock)));
                    view.createMessageBox(frame, "Portfolio Updated.");

                  } else {
                    view.createMessageBox(frame, "Please enter a valid number. The number " + "is" +
                            " either" + " negative or more than the" + " stocks that exists.");
                  }
                }
              }
            } else {
              view.createMessageBox(frame, "Cannot sell stock on this date as some stocks " +
                      "are " + "sold after this.");
            }
          }
        } else {
          view.createMessageBox(frame, "Enter valid date.");
        }


      }

    }

  }

  @Override
  public void costBasis(JPanel frame, String name, String dayText,
                        String monthText,
                        String yearText) {
    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0
            || yearText.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
      return;
    } else {
      int day = model.stringToNumber(dayText);
      int month = model.stringToNumber(monthText);
      int year = model.stringToNumber(yearText);

      if (!model.flexiblePortContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio does not exist");
        return;
      } else if (day == 0 || month == 0 || year == 0) {
        view.createMessageBox(frame, "Enter numeric values for date");
        return;
      } else {
        date = model.makeStringDate(day, month, year);
        if (!model.isValidDate(date)) {
          view.createMessageBox(frame, "Enter valid date.");
          return;
        }
      }
    }
    List<String> companies = model.getCompaniesInCertainPortfolio(name);
    List<String> actualCompanyData = new ArrayList<>();
    for (int i = 0; i < companies.size(); i++) {
      List<List<String>> data = model.getStockDataInCertainPortfolio(name,
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
      view.createMessageBox(frame, "No stocks purchased before this date.");
      return;
    }
    String dash;
    Double overallTotalMoneySpent = 0.0;
    Double totalMoneySpent = 0.0;
    for (int i = 0; i < actualCompanyData.size(); i++) {
      StringBuilder totalStrings = new StringBuilder("");
      totalMoneySpent = 0.0;
      List<List<String>> data = model.getStockDataInCertainPortfolio(name,
              actualCompanyData.get(i));
      dash = helper(actualCompanyData.get(i).length());
      for (int j = 0; j < data.size(); j++) {
        List<String> insideContents = data.get(j);
        if (date.compareTo(insideContents.get(3)) < 0) {
          continue;
        }

        if (insideContents.get(0).equals("Sell")) {
          String seller = actualCompanyData.get(i) + "\n" + dash + "\n" + "Sell         -> " +
                  insideContents.get(1) + " : "
                  + insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n"
                  + "Commission  -> $" + insideContents.get(4);
          totalStrings.append(seller);
          view.displayCostBasis(seller);
          totalMoneySpent += Double.parseDouble(insideContents.get(4));

        }
        if (insideContents.get(0).equals("Buy")) {
          String buyer = actualCompanyData.get(i) + "\n" + dash + "\n" + "Buy         -> " +
                  insideContents.get(1) + " : "
                  + insideContents.get(2) + "\n" + "Date        -> " + insideContents.get(3) + "\n"
                  + "Total value -> $" + insideContents.get(5) + "\n" + "Commission  -> $"
                  + insideContents.get(4);
          totalStrings.append(buyer);
          view.displayCostBasis(buyer);
          totalMoneySpent += Double.parseDouble(insideContents.get(4))
                  + Double.parseDouble(insideContents.get(5));
        }
      }
      overallTotalMoneySpent += totalMoneySpent;
      totalStrings.append("Total money spent on " + actualCompanyData.get(i) +
              "-> $" + totalMoneySpent);
      view.displayCostBasis("Total money spent on " + actualCompanyData.get(i) +
              "-> $" + totalMoneySpent);
    }
    view.displayCostBasis("Overall Total -> $" + overallTotalMoneySpent);

  }

  @Override
  public void totalValue(JPanel frame, String name, String dayText, String monthText,
                         String yearText) {
    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0 || yearText.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
    } else {
      int day = model.stringToNumber(dayText);
      int month = model.stringToNumber(monthText);
      int year = model.stringToNumber(yearText);

      if (!model.flexiblePortfolioContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio with this name does not exists");
      } else if (day == 0 || month == 0 || year == 0) {
        view.createMessageBox(frame, "Enter numeric values for date");
      } else {
        date = model.makeStringDate(day, month, year);
        if (model.isValidDate(date)) {
          HashMap<String, Double> totalValueData = model.getTotalFlexibleStockValue(name,
                  date);
          double totalValue = 0.0;
          for (Map.Entry<String, Double> set : totalValueData.entrySet()) {
            totalValue += set.getValue();
            if (set.getValue() != 0) {
              view.displayDynamicDataTotalValue(frame, set.getKey(),
                      String.valueOf(set.getValue()));
            } else {
              view.displayDynamicDataTotalValue(frame, set.getKey(), "no value for selected date");
            }
          }
          view.displayTotalValue(frame, name, date,
                  new BigDecimal(totalValue).toPlainString());
        } else {
          view.createMessageBox(frame, "Enter valid date.");
        }
      }
    }
  }

  @Override
  public void uploadPortfolio(JPanel frame, String path, int selected) {
    String isFileReadSuccessFull = model.readFromFile(path);
    if (isFileReadSuccessFull.equals("Failure")) {
      view.createMessageBox(frame, "Enter a valid Path");
    } else if (selected == 1) {
      Map<String, List<List<String>>> parsedPortfolio = model.parseJson(isFileReadSuccessFull);
      if (parsedPortfolio == null) {
        view.createMessageBox(frame, "The format of data is incorrect.");
      }
      boolean checker = false;
      try {
        checker = model.checkParsedPortfolio(parsedPortfolio);
      } catch (Exception e) {
        view.createMessageBox(frame, "The format of data is incorrect.");
      }
      if (!checker) {
        view.createMessageBox(frame, "The format of data is incorrect.");
      }
      model.setInflexiblePortfolio(parsedPortfolio);
      model.savePortfolio();
      view.createMessageBox(frame, "Inflexible Portfolio uploaded successfully");
    } else if (selected == 2) {
      Map<String, Map<String, List<List<String>>>> parseFlexiblePortfolio =
              model.parseFlexiblePortfolio(isFileReadSuccessFull);
      List<String> keys = new ArrayList<>();
      try {
        keys = new ArrayList<>(parseFlexiblePortfolio.keySet());
      } catch (Exception e) {
        view.createMessageBox(frame, "The format of data is incorrect.");
      }
      for (int i = 0; i < keys.size(); i++) {
        Map<String, List<List<String>>> insideContents = parseFlexiblePortfolio.get(keys.get(i));
        List<String> insideKeySet = new ArrayList<>();
        try {
          insideKeySet = new ArrayList<>(insideContents.keySet());
          if (insideKeySet.size() == 0) {
            view.createMessageBox(frame, "The format of data is incorrect.");
          }
        } catch (Exception e) {
          view.createMessageBox(frame, "The format of data is incorrect.");
        }
        for (int k = 0; k < insideKeySet.size(); k++) {
          List<List<String>> contents =
                  parseFlexiblePortfolio.get(keys.get(i)).get(insideKeySet.get(k));
          for (int j = 0; j < contents.size(); j++) {
            List<String> insideValues = contents.get(j);
            if (insideValues.size() != 6) {
              view.createMessageBox(frame, "The format of data is incorrect.");
            }
            if ((!insideValues.get(0).equals("Buy") && !insideValues.get(0).equals("Sell"))) {
              view.createMessageBox(frame, "The format of data is incorrect.");
            }
            String ticker = insideValues.get(1);
            String date = insideValues.get(3);
            String numberOfStocks = insideValues.get(2);
            String commission = insideValues.get(4);
            if (!model.isValidDate(date)) {
              view.createMessageBox(frame, "The format of data is incorrect.");
            }
            try {
              Double.parseDouble(numberOfStocks);
              Double.parseDouble(commission);
            } catch (Exception e) {
              view.createMessageBox(frame, "The format of data is incorrect.");
            }
            String data = model.addApiCompanyStockData(ticker);
            if (data.equals("Failure")) {
              view.createMessageBox(frame, "The format of data is incorrect.");
            } else {
              HashMap<String, String> stockData = model.convertingStringToHashMap(data);
              model.addStockDataToFlexibleList(stockData);
              int num = model.getApiStockDataSize();
              model.putNameInCompanyInPortfolio(ticker);
              model.putCompanyNameInTickerFinder(ticker, num - 1);
            }
          }
        }
      }
      model.setFlexible(parseFlexiblePortfolio);
      model.saveFlexiblePortfolios();
      view.createMessageBox(frame, "Flexible Portfolio successfully");
    }
  }

  @Override
  public void dollarCostAveraging() {

  }

  @Override
  public void noDate(String ticker, String percentage) {

  }

  @Override
  public void dollarCostAveragingAndQueryCostBasisAndValue() {

  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public void createGraph(JPanel frame, String name, String startDay, String startMonth,
                          String startYear,
                          String endDay, String endMonth, String endYear) {
    String startDate;
    String endDate;
    if (name.length() == 0 || startDay.length() == 0 || startMonth.length() == 0
            || startYear.length() == 0 || endDay.length() == 0 || endMonth.length() == 0
            || endYear.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
    } else {
      int day1 = model.stringToNumber(startDay);
      int month1 = model.stringToNumber(startMonth);
      int year1 = model.stringToNumber(startYear);

      if (!model.flexiblePortfolioContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio with this name does not exists");
      } else if (day1 == 0 || month1 == 0 || year1 == 0) {
        view.createMessageBox(frame, "Enter numeric values for start date");
      } else {
        startDate = model.makeStringDate(day1, month1, year1);
        if (model.isValidDate(startDate)) {
          int day2 = model.stringToNumber(endDay);
          int month2 = model.stringToNumber(endMonth);
          int year2 = model.stringToNumber(endYear);
          if (day2 == 0 || month2 == 0 || year2 == 0) {
            view.createMessageBox(frame, "Enter numeric values for end date");
          } else {
            endDate = model.makeStringDate(day2, month2, year2);
            if (model.isValidDate(endDate)) {
              DefaultCategoryDataset dataset = lineGraphValues(frame, name, startDate, endDate);
              view.createLineGraph(dataset);
            } else {
              view.createMessageBox(frame, "Enter valid end date.");
            }
          }
        } else {
          view.createMessageBox(frame, "Enter valid start date.");
        }
      }
    }

  }

  public DefaultCategoryDataset lineGraphValues(JPanel graph, String portfolioName,
                                                String startingDate, String endingDate) {
    if (startingDate.compareTo(endingDate) >= 0) {
      view.createMessageBox(graph, "Ending date cannot be smaller then Start date");
    }
    long differenceBetweenDates = model.numberOfDays(LocalDate.parse(startingDate),
            LocalDate.parse(endingDate));

    if (differenceBetweenDates < 10) {
      view.createMessageBox(graph, "Time range is very small");
    }
    LocalDate dateStartingDate = LocalDate.parse(startingDate);
    LocalDate dateEndingDate = LocalDate.parse(endingDate);

    Map<String, String> dateValues = new HashMap<>();
    if (differenceBetweenDates >= 1825) {
      dateValues = model.calculatingYears(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 1825 && differenceBetweenDates >= 900) {
      dateValues = model.moreMonths(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 900 && differenceBetweenDates >= 150) {
      dateValues = model.calculatingMonths(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 150 && differenceBetweenDates >= 30) {
      dateValues = model.moreDays(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 30 && differenceBetweenDates >= 5) {
      dateValues = model.calculatingDays(dateStartingDate, dateEndingDate);
    }

    List<String> keys = new ArrayList<>(dateValues.keySet());
    List<Double> values = new ArrayList<>();
    for (int i = 0; i < keys.size(); i++) {
      values.add(model.calculateValueForGraph(model.getTotalFlexibleStockValue(portfolioName
              , dateValues.get(keys.get(i)))));
    }
//    view.displayContentsInParameter("Performance of portfolio " + portfolioName + " from " +
//            startingDate + " to " + endingDate);
    String series1 = "Stocks";
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < values.size(); i++) {
      int val = values.get(i).intValue();
      dataset.addValue(val / 1000, series1, keys.get(i));
    }
    return dataset;
  }

  private String helper(int number) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < number; i++) {
      str.append("_");
    }
    return str.toString();
  }

}
