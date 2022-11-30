package view;

import javax.swing.*;

import controller.ControllerGUIImpl;

public interface ViewGui {

  void addFeatures(ControllerGUIImpl features);

  void displayDynamicDataTotalValue(JPanel frame, String companyName, String value);

  void displayTotalValue(JPanel frame, String portfolioName, String value, String date);

  void createMessageBox(JPanel frame, String message);

  void displayCostBasis(String data);
}
