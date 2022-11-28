package view;

import java.io.PrintStream;


/**
 * This class is responsible for displaying the output to the user.
 */
public class ViewImpl implements View {
  private final PrintStream out;

  /**
   * Constructor for the View.View Class.
   *
   * @param out PrintStream object, required way ex. main method sends System.out.
   */
  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void displayWhatIsInParameter(int optionNumber, String needToDisplay) {
    this.out.println(optionNumber + "." + " " + needToDisplay);
  }


  @Override
  public void displayOnlyIntegers() {
    this.out.println("Enter only valid Integers");
    this.out.println();
  }

  @Override
  public void displaySwitchCaseDefault() {
    this.out.println("Enter an valid option.");
    this.out.println();
  }


  @Override
  public void portfolioCreation() {
    this.out.println("1.Give a name for the current Portfolio");
    this.out.println("2.Show list of Companies");
    this.out.println("3.Add a company Stock");
    this.out.println("4.Exit");
    this.out.println();
  }

  @Override
  public void displayCompanies(String stockCompanyName, int number) {
    this.out.println(number + "." + stockCompanyName);
  }

  @Override
  public void displayEmptyLine() {
    this.out.println();
  }

  @Override
  public void displayCannotEnterNameAgain() {
    this.out.println("You already entered Name for this current Portfolio,cannot change Name.");
    this.out.println();
  }

  @Override
  public void displayCannotProceedWithoutName() {
    this.out.println("Please Enter a name for the portfolio first.");
    this.out.println();
  }

  @Override
  public void displayEnterNameForPortfolio() {
    this.out.println("Enter Name of the Portfolio:");
    this.out.println();
  }

  @Override
  public void displayNameCannotBeEmpty() {
    this.out.println("Name cannot be empty. Enter at least one character");
    this.out.println();
  }

  @Override
  public void displayAlreadyHaveAnotherPortfolioWithSameName() {
    this.out.println("You already have another Portfolio with the same name.");
    this.out.println();
  }

  @Override
  public void askForCompanyName() {
    this.out.println("Enter the company name:");
    this.out.println();
  }

  @Override
  public void askForNumberOfStocks() {
    this.out.println("Enter number of stocks:");
    this.out.println();
  }


  @Override
  public void displayStockNumberCannotBeLessThanOrEqualToZero() {
    this.out.println("Stock value cannot be less than or equal to zero. Please enter a valid"
            + "number");
    this.out.println();
  }

  @Override
  public void displayNoSuchCompanyExist() {
    this.out.println("There is no such company, enter using the list of companies available");
    this.out.println();
  }

  @Override
  public void displayPortfolioName(String currPortfolio) {
    this.out.println("Portfolio: " + currPortfolio);
  }

  @Override
  public void displayTableLayout() {
    this.out.println("Company\t\tStock-Numbers\t\tDate-obtained");
  }


  @Override
  public void displayContentsOfPortfolio(String contents) {
    this.out.print(contents + "\t\t\t");
  }


  @Override
  public void displayPortfolioIsEmpty() {
    this.out.println("Portfolio is empty, cannot view composition");
    this.out.println();
  }


  @Override
  public void displaySelectDateOption(String currentDate) {
    this.out.println("Current Date: " + currentDate);
    this.out.println("1.Select Date");
    this.out.println("2.Exit");

    this.out.println();
  }

  @Override
  public void askForDayOfTheMonth() {
    this.out.println("Enter the day of the month in number(1-31):");
  }

  @Override
  public void askForMonth() {
    this.out.println("Enter the month in number(1-12):");
  }

  @Override
  public void askForYear() {
    this.out.println("Enter the year you wish to jump to(2001 - 2022):");
  }

  @Override
  public void displayEnterValidDetailsForDate() {
    this.out.println("Enter valid details.");
  }

  @Override
  public void displayDateIsNotValid() {
    this.out.println("The entered date is not a valid date.");
    this.out.println();
  }

  @Override
  public void displayStockValueMenu() {
    this.out.println("1.Enter the portfolio name to get value for");
    this.out.println("2.Get total Stock Value on current Date");
    this.out.println("3.Change the date and get Stock Value");
    this.out.println("4.Exit");
    this.out.println();
  }

  @Override
  public void displayPortfolioNameAlreadyEntered() {
    this.out.println("You already entered name for portfolio. Choose option 2 or 3 or exit.");
    this.out.println();
  }


  @Override
  public void displayTotalStockValue(String portfolioName, String currentDate, String totalValue) {
    this.out.println("Portfolio: " + portfolioName);
    this.out.println("Date: " + currentDate);
    this.out.println("Total Value: $ " + totalValue);
    this.out.println();
  }

  @Override
  public void displayTotalFlexibleStockValue(String portfolioName, String dateSelected,
                                             String totalValue) {
    this.out.println("Portfolio: " + portfolioName);
    this.out.println("Date: " + dateSelected);
    this.out.println("Total Value: $" + totalValue);
    this.out.println();
  }

  @Override
  public void displayNoSuchPortfolio() {
    this.out.println("No such portfolio exists, enter name again");
    this.out.println();
  }

  @Override
  public void displayNoPortfolio() {
    this.out.println("Create portfolio first.");
    this.out.println();
  }

  @Override
  public void displayNoStockDataForGivenDate() {
    this.out.println("There is no stock data for given date, please enter another date");
    this.out.println();
  }

  @Override
  public void displayAllPortfolioSaved(String path) {
    this.out.println("All the portfolio created have been saved and It is in the location: "
            + path);
    this.out.println();
  }


  @Override
  public void askForFilePath() {
    this.out.println("Enter the file path:");
    this.out.println();
  }

  @Override
  public void displayTheFilePathDoesNotExist() {
    this.out.println("The file path does not exist, Please Enter a valid path");
    this.out.println();
  }

  @Override
  public void displayDataNotInProperFormat() {
    this.out.println("The portfolio provided in the text file is not in proper format," + "please"
            + " look at the documentation");
    this.out.println();
  }

  @Override
  public void displayWarning() {
    this.out.println("Warning: You are using older feature of Stocks");
    this.out.println("If you wish to use Mutable portfolios, have options to use multiple "
            + "companies, choose the newer version");
  }

  @Override
  public void displayPortfolioNameMenu() {
    this.out.println("1. Enter the name of the portfolio");
    this.out.println("2. Exit");
    this.out.println();
  }

  @Override
  public void displayCreateFlexiblePortfolioMenu() {
    this.out.println("1. Create new flexible portfolio");
    this.out.println("2. Edit the existing flexible portfolio");
    this.out.println("3. Add the company's stock");
    this.out.println("4. Exit");
    this.out.println();
  }

  @Override
  public void displayAddCompanyStockMenu() {
    this.out.println("1. Enter the ticker symbol of the desired company.");
    this.out.println("2. Exit");
    this.out.println();
  }

  @Override
  public void displayCompanyTickerSymbolIsNotValid() {
    this.out.println("Please input correct ticker Symbol");
    this.out.println();
  }

  @Override
  public void askForTickerSymbol() {
    this.out.println("Enter ticker symbol for the company:");
    this.out.println();
  }

  @Override
  public void askForNumberOfStocksToSell() {
    this.out.println("Enter number of stock to sell:");
    this.out.println();
  }

  @Override
  public void dateSelectionMenu() {
    this.out.println("1. Enter the Date");
    this.out.println("2. Exit");
  }

  @Override
  public void askForFlexibleOrInFlexible() {
    this.out.println("Enter 1, for Inflexible Portfolios:");
    this.out.println("Enter 2, for Flexible Portfolios:");
  }

  @Override
  public void displayContentsInParameter(String contents) {
    this.out.println(contents);
  }

  @Override
  public void enterValidStockToSell() {
    this.out.println("Please enter a valid number. The number is either negative or more than the"
            + " stocks that exists.");
  }

  @Override
  public void displayPortfolioUpdated() {
    this.out.println("Portfolio updated.");
  }

  @Override
  public void displayNoStockToSell() {
    this.out.println("No Stocks to sell.");
  }


  @Override
  public void displayTotalFlexValue(String companyName, String value) {
    this.out.println(companyName + "--> $" + value);
  }

  @Override
  public void displayCannotSellStock() {
    this.out.println("Cannot sell stock on this date as some stocks are sold after this.");
  }

  @Override
  public void displayEnterStartingDateFirstAndEndingSecond() {
    this.out.println("Enter the starting date first and ending date second");
    this.out.println();
  }

  @Override
  public void displayEndingDateCannotBeSameOrSmallerThanStartingDate() {
    this.out.println("Ending date cannot be same or lesser than the starting date");
  }

  @Override
  public void displayRangeIsSmall() {
    this.out.println("The provided range is very small, cannot calculate.");
    this.out.println();
  }

}

