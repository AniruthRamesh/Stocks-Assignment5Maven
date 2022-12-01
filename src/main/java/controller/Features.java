package controller;

import javax.swing.*;

public interface Features {

  /**
   * This function creates a new flexible portfolio.
   *
   * @param frame      the JPanel that the user is currently on
   * @param text       The name of the portfolio
   * @param dayText    The day of the month that the user wants to start the portfolio on.
   * @param monthText  The month of the date you want to start the portfolio on.
   * @param yearText   The year of the portfolio
   * @param tickerText The ticker symbol of the stock you want to add to the portfolio.
   * @param numberText the number of shares you want to buy
   */
  void createNewFlexiblePortfolio(JPanel frame, String text, String dayText, String monthText,
                                  String yearText,
                                  String tickerText, String numberText);

  /**
   * This function sells portfolio.
   *
   * @param frame      the JPanel that the user is currently on
   * @param text       the name of the portfolio
   * @param dayText    The day of the month you want to sell the stock
   * @param monthText  The month of the date you want to sell the stock.
   * @param yearText   the year of the date you want to sell the stock
   * @param tickerText the ticker symbol of the stock you want to sell
   * @param numberText The number of shares you want to sell
   */
  void sellPortfolio(JPanel frame, String text, String dayText, String monthText,
                     String yearText,
                     String tickerText, String numberText);

  /**
   * This function calculates cost Basis.
   *
   * @param frame     The JPanel that the cost basis will be displayed on.
   * @param name      The name of the stock
   * @param dayText   The day of the month the stock was purchased.
   * @param monthText The month of the purchase.
   * @param yearText  The year of the purchase.
   */
  void costBasis(JPanel frame, String name, String dayText,
                 String monthText,
                 String yearText);

  /**
   * This function total value of stocks.
   *
   * @param frame     The JPanel that the user is currently on.
   * @param name      name of portfolio.
   * @param dayText   The day of the month.
   * @param monthText The month of the date you want to calculate the total value of.
   * @param yearText  The year of the date you want to calculate the total value of.
   */
  void totalValue(JPanel frame, String name, String dayText, String monthText,
                  String yearText);

  /**
   * It uploads a portfolio to the current session.
   *
   * @param frame    The JPanel that the user is currently on.
   * @param path     The path to the file you want to upload.
   * @param selected 1 for inflexible, 2 for flexible
   */
  void uploadPortfolio(JPanel frame, String path, int selected);

  /**
   * It calculates the dollar cost averaging of a stock.
   */
  void dollarCostAveraging(JPanel frame,String portfolio,String amount,String day,String month,
                           String year);

  /**
   * This function takes in a ticker and a percentage.
   *
   * @param ticker     The ticker symbol of the stock you want to get the data for.
   * @param percentage The percentage of the stock's price that you want to buy.
   */
  boolean noDate(JPanel frame,String ticker, String percentage);

  boolean enteredDate(JPanel frame,String day,String month,String year);

  void dollarCostAveragingAndQueryCostBasisAndValue(JPanel frame,String portfolio,String amount,
                                                    String day,String month,
                                                    String year);

  void exit();

  void createGraph();

  boolean getPercentage();

  boolean durationCheck(JPanel frame,String myd, String duration);

  boolean mydChecker();

}
