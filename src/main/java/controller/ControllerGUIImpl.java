package controller;

import java.util.ArrayList;
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
  public void costBasis() {

  }

  @Override
  public void totalValue() {

  }

  @Override
  public void uploadPortfolio(JPanel frame, String path, int selected) {
    String isFileReadSuccessFull = model.readFromFile(path);
    if (isFileReadSuccessFull.equals("Failure")) {
      view.createMessageBox(frame, "Enter a valid Path");
      if (selected == 1) {
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
        view.createMessageBox(frame, "File uploaded successfully");
      }
    }

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
