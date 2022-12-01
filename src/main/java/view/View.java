package view;

/**
 * Creating an interface named View.
 */

public interface View {
  /**
   * This function displays the option number and the string that is passed as a parameter.
   *
   * @param optionNumber  This is the number of the option that will be displayed.
   * @param needToDisplay This is the string that you want to display.
   */
  void displayWhatIsInParameter(int optionNumber, String needToDisplay);

  /**
   * Displays only integers are valid values.
   */
  void displayOnlyIntegers();

  /**
   * Tells the user to enter a valid option.
   */
  void displaySwitchCaseDefault();

  /**
   * Displays options menu for creating a new Portfolio.
   */
  void portfolioCreation();

  /**
   * Displays the name of companies with number of stock.
   *
   * @param stockCompanyName company name
   * @param number           number of stocks.
   */
  void displayCompanies(String stockCompanyName, int number);

  /**
   * Display an empty line.
   */
  void displayEmptyLine();

  /**
   * Display message that user cant change the name of portfolio.
   */
  void displayCannotEnterNameAgain();

  /**
   * Display message that name is mandatory for portfolio.
   */
  void displayCannotProceedWithoutName();

  /**
   * Asks the user to enter name for portfolio.
   */
  void displayEnterNameForPortfolio();

  /**
   * Displays that name cannot be empty.
   */
  void displayNameCannotBeEmpty();

  /**
   * Tells the user that he has created another portfolio with same name.
   */
  void displayAlreadyHaveAnotherPortfolioWithSameName();

  /**
   * Asks the user to enter company name.
   */
  void askForCompanyName();

  /**
   * Display message for user to enter number of stocks of company.
   */
  void askForNumberOfStocks();

  /**
   * Display message for the value of stock cannot be less than or equal 0.
   */
  void displayStockNumberCannotBeLessThanOrEqualToZero();

  /**
   * Displays message that no such company exists.
   */
  void displayNoSuchCompanyExist();

  /**
   * Displays current portfolio name.
   *
   * @param currPortfolio name of current portfolio
   */
  void displayPortfolioName(String currPortfolio);

  /**
   * Displays data in table layout.
   */
  void displayTableLayout();

  /**
   * Displays content of portfolio.
   *
   * @param contents content in string form.
   */
  void displayContentsOfPortfolio(String contents);

  /**
   * Display message if portfolio is empty.
   */
  void displayPortfolioIsEmpty();

  /**
   * Display options of select date.
   *
   * @param currentDate Date entered.
   */
  void displaySelectDateOption(String currentDate);

  /**
   * This function asks the user to enter the day of the month in number.
   */
  void askForDayOfTheMonth();

  /**
   * Display message to input month.
   */
  void askForMonth();

  /**
   * Display message to input year.
   */
  void askForYear();

  /**
   * Display message to input valid inputs.
   */
  void displayEnterValidDetailsForDate();

  /**
   * Display message to show that entered date is invalid.
   */
  void displayDateIsNotValid();

  /**
   * Display message options for stock values.
   */
  void displayStockValueMenu();

  /**
   * Displays if the user has already entered name for portfolio.
   */
  void displayPortfolioNameAlreadyEntered();

  /**
   * Displays data of a particular portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param currentDate   date of creation of portfolio
   * @param totalValue    total value of stocks
   */

  void displayTotalStockValue(String portfolioName, String currentDate, String totalValue);

  /**
   * Displays message if no portfolio exists for the name entered.
   */
  void displayNoSuchPortfolio();

  /**
   * Display message to create portfolio and then check value.
   */
  void displayNoPortfolio();

  /**
   * Display is no stock is present for the entered day.
   */
  void displayNoStockDataForGivenDate();

  /**
   * This function asks the user for the file path.
   */
  void askForFilePath();

  /**
   * This function displays a message to the user that the file path does not exist.
   */
  void displayTheFilePathDoesNotExist();

  /**
   * This function prints out a message to the user that the portfolio provided in the text file
   * is not
   * in proper format.
   */
  void displayDataNotInProperFormat();

  /**
   * Displays a method to the user saying there is a new version released.
   */
  void displayWarning();

  /**
   * It prints out the menu for the user to enter the name of the portfolio.
   */
  void displayPortfolioNameMenu();

  /**
   * It prints out the menu for creating a flexible portfolio.
   */
  void displayCreateFlexiblePortfolioMenu();

  /**
   * This function displays the menu for adding a company stock to the portfolio.
   */
  void displayAddCompanyStockMenu();

  /**
   * This function displays a message to the user that the company associated with the ticker
   * symbol they inputted doesn't exist.
   */
  void displayCompanyTickerSymbolIsNotValid();

  /**
   * This function asks the user for a ticker symbol.
   */
  void askForTickerSymbol();

  /**
   * This function asks the user for the number of stocks to sell.
   */
  void askForNumberOfStocksToSell();

  /**
   * This function asks the user to enter 1 for inflexible portfolios or 2 for flexible portfolios.
   */
  void askForFlexibleOrInFlexible();

  /**
   * This function displays the contents of the parameter in the console.
   *
   * @param contents The contents to be displayed.
   */
  void displayContentsInParameter(String contents);

  /**
   * Printing out a message to the user that the number of stocks they want to sell is
   * either negative or more than the stocks that exists.
   */

  void enterValidStockToSell();

  /**
   * This function displays a message to the user that the profile is updated.
   */
  void displayPortfolioUpdated();

  /**
   * This function displays a message to the user that there are no stocks to sell.
   */
  void displayNoStockToSell();

  /**
   * This function prints a message to the user that they cannot sell stock on this date as some
   * stocks are sold after this.
   */
  void displayCannotSellStock();

  /**
   * Tells the user to enter starting date first and ending date second.
   */
  void displayEnterStartingDateFirstAndEndingSecond();

  /**
   * Displays that ending date is either same as starting date or it is lesser than starting date.
   */
  void displayEndingDateCannotBeSameOrSmallerThanStartingDate();

  /**
   * Tells the user that range is very small, and we cannot calculate the performance.
   */
  void displayRangeIsSmall();

  /**
   * This function prints out the menu for the user to select the date.
   */
  public void dateSelectionMenu();

  /**
   * This function displays the total flex value for a company.
   *
   * @param companyName The name of the company
   * @param value       The value of the flex field.
   */
  public void displayTotalFlexValue(String companyName, String value);
}
