package controller;

import javax.swing.*;

public interface Features {

  void createNewFlexiblePortfolio(JPanel frame, String text, String dayText, String monthText,
                                  String yearText,
                                  String tickerText, String numberText);

  void sellPortfolio();

  void costBasis(JPanel frame, String name, String dayText,
                 String monthText,
                 String yearText);

  void totalValue();

  void uploadPortfolio();

  void savePortfolio();

  void dollarCostAveraging();

  void dollarCostAveragingAndQueryCostBasisAndValue();

  void exit();

  void createGraph();
}
