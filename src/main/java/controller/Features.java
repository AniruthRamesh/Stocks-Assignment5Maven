package controller;

import javax.swing.*;

public interface Features {

  void createNewFlexiblePortfolio(JPanel frame, String text, String dayText, String monthText,
                                  String yearText,
                                  String tickerText, String numberText);

  void sellPortfolio(JPanel frame, String text, String dayText, String monthText,
                     String yearText,
                     String tickerText, String numberText);

  void costBasis(JPanel frame, String name, String dayText,
                 String monthText,
                 String yearText);

  void totalValue(JPanel frame, String name, String dayText, String monthText,
                  String yearText);

  void uploadPortfolio(JPanel frame, String path, int selected);

  void dollarCostAveraging();

  void dollarCostAveragingAndQueryCostBasisAndValue();

  void exit();

  void createGraph();
}
