package controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import command.Command;
import model.Model;
import view.ViewGui;

public class ControllerGUIImpl implements Features {
  ViewGui view;
  Model model;
  Command command;

  public ControllerGUIImpl(Model model, ViewGui viewGui) {
    this.model = model;
    this.view = viewGui;
    this.view.addFeatures(this);

  }

  @Override
  public void createNewFlexiblePortfolio(JPanel frame, String name, String dayText,
                                         String monthText,
                                         String yearText, String tickerText, String numberText) {

    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0
            || yearText.length() == 0 || tickerText.length() == 0 || numberText.length() == 0) {
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
            if (numberOfStocks == 0 || numberOfStocks <= 0) {
              view.createMessageBox(frame, "Number of stocks should be numeric. Value" +
                      " cannot be 0" + " " + "or negative");
            } else {
              double stockNumber = model.helper(Double.valueOf(numberOfStocks));
              Double commission = 0.1;

              if (stockData.containsKey(date)) {
                Double priceOnThatDate = Double.parseDouble(stockData.get(date));
                Double totalPrice = priceOnThatDate * numberOfStocks;
                commission *= totalPrice;
                Map<String, Map<String, List<List<String>>>> flexible = model.getFlexiblePort();
                Map<String, List<List<String>>> val = new HashMap<>();
                val.put(tickerText, List.of(List.of("Buy",
                        tickerText, String.valueOf(numberOfStocks), date,
                        String.format("%.2f", commission), String.format("%.2f", totalPrice))));
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
  public void sellPortfolio() {

  }

  @Override
  public void costBasis(JPanel frame, String name, String dayText,
                        String monthText,
                        String yearText) {
    String date;
    if (name.length() == 0 || dayText.length() == 0 || monthText.length() == 0
            || yearText.length() == 0) {
      view.createMessageBox(frame, "Fields cannot be empty");
    } else {
      int day = model.stringToNumber(dayText);
      int month = model.stringToNumber(monthText);
      int year = model.stringToNumber(yearText);

      if (!model.flexiblePortContainsCertainKey(name)) {
        view.createMessageBox(frame, "Portfolio does not exist");
      } else if (day == 0 || month == 0 || year == 0) {
        view.createMessageBox(frame, "Enter numeric values for date");
      } else {
        date = model.makeStringDate(day, month, year);
        if (!model.isValidDate(date)) {
          view.createMessageBox(frame, "Enter valid date.");
          return;
        }

      }
    }
  }

  @Override
  public void totalValue() {

  }

  @Override
  public void uploadPortfolio() {

  }

  @Override
  public void savePortfolio() {

  }

  @Override
  public void dollarCostAveraging() {

  }

  @Override
  public void dollarCostAveragingAndQueryCostBasisAndValue() {

  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public void createGraph() {

  }

}
