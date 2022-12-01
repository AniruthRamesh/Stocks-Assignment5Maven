package view;

import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JPanel;

import controller.ControllerGUIImpl;

/**
 * Creating a new interface called ViewGui.
 */
public interface ViewGui {

  /**
   * * This function adds the features to the controller.
   *
   * @param features The features to add to the controller.
   */
  void addFeatures(ControllerGUIImpl features);

  /**
   * It displays the total value of the company's stock.
   *
   * @param frame       The JPanel that you want to display the data on.
   * @param companyName The name of the company you want to display the data for.
   * @param value       The value to be displayed
   */
  void displayDynamicDataTotalValue(JPanel frame, String companyName, String value);

  /**
   * This function displays the total value of a portfolio on the screen.
   *
   * @param frame         The JPanel that the total value will be displayed on.
   * @param portfolioName The name of the portfolio
   * @param value         The total value of the portfolio
   * @param date          The date of the total value.
   */
  void displayTotalValue(JPanel frame, String portfolioName, String value, String date);

  /**
   * Create a message box with the given message and add it to the given frame.
   *
   * @param frame   The frame that the message box will be created on.
   * @param message The message you want to display in the message box.
   */
  void createMessageBox(JPanel frame, String message);

  /**
   * This function takes in a string of data and displays the cost basis of the stock.
   *
   * @param data The data to be displayed.
   */
  void displayCostBasis(String data);

  void createLineGraph(DefaultCategoryDataset dataset);

  void addRadioButtons(JPanel frame, String portfolio);
}
