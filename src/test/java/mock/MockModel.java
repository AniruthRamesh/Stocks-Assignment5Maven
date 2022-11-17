package mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import inputdata.AlphaVantageApi;
import inputdata.InputDataSource;
import model.Model;
import outputdatasource.JsonPackage;

/**
 * This class is mock of the model, which is mainly used of logging what values controller sends
 * and what value is calculated after model performs an execution, it is mainly used for
 * testing.
 */
public class MockModel implements Model {
  private final StringBuilder logForAddPortfolioData = new StringBuilder();
  private final StringBuilder logForCheckingIfCompanyNameExist = new StringBuilder();
  private final StringBuilder logForIsValidDate = new StringBuilder();
  private final StringBuilder logForMakeStringDate = new StringBuilder();
  private final StringBuilder logForReadFromFile = new StringBuilder();
  private final StringBuilder logForParseJson = new StringBuilder();
  private final StringBuilder logForCheckParsedPortfolio = new StringBuilder();
  private final StringBuilder logForCostBasis = new StringBuilder();
  private final StringBuilder logForGetPortfolioKeys = new StringBuilder();
  private final StringBuilder LogForGetPortfolioSize = new StringBuilder();
  private final StringBuilder logForHelper = new StringBuilder();
  private final StringBuilder logForCheckingHelperWorksCorrectly = new StringBuilder();
  private final StringBuilder logForSetCurrentDate = new StringBuilder();
  private final StringBuilder logForPortfolioContainsCertainKey = new StringBuilder();
  private final StringBuilder logForHasAnotherPortfolioWithSameName = new StringBuilder();
  private final StringBuilder logForTotalStockValue = new StringBuilder();
  private final StringBuilder logForSetContainsGivenDate = new StringBuilder();
  private final StringBuilder logForSavePortfolio = new StringBuilder();
  private final StringBuilder logForModelSavePortfolio = new StringBuilder();
  List<String> stockCompanies = List.of("AAPL.txt", "AMZN.txt", "ATVI.txt", "BCS.txt",
          "CAJ.txt", "CSCO.txt", "DIS.txt", "JPM.txt", "MCD.txt", "MSFT.txt", "ORCL.txt",
          "SBUX.txt", "WFC.txt");

  List<String> stockCompanyName = List.of("APPLE", "AMAZON", "ACTIVISION", "BARCLAYS",
          "CANON INC", "CISCO SYSTEMS", "DISNEY", "JP MORGAN", "MCDONALD", "MICROSOFT",
          "ORACLE", "STARBUCKS", "WELLS FARGO");

  String apiErrorMessage = "{\n"
          + "    \"Error Message\": \"Invalid API call. Please retry or visit the documentation "
          + "(https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.\"\n"
          + "}";
  //ArrayList of HashMap containing StockData of companies with date as key and stock value on
  //that date as value.
  List<HashMap<String, String>> stockData = new ArrayList<>();
  String startingDate = "2001-02-02";
  //String endingDate = "2022-10-25";

  String currentDate = startingDate;

  Set<String> listOfDates = new HashSet<>();
  String data;

  Map<String, List<List<String>>> inflexiblePortfolio = new HashMap<>();

  List<String> initialOptions = List.of("Create Inflexible Portfolio",
          "Examine Composition of current Portfolio",
          "Fast Forward Time", "Determine value of stocks on certain Date", "Upload a portfolio",
          "List all portfolios", "Create Flexible Portfolio", "Sell Stocks from a Portfolio",
          "Determine Cost Basis", "Exit");

  Map<String, Map<String, List<List<String>>>> flexiblePort = new HashMap<>();
  Map<String, List<List<String>>> flexiblePortfolio = new HashMap<>();
  List<HashMap<String, String>> apiStockData = new ArrayList<>();

  Map<String, Integer> tickerFinder = new HashMap<>();

  Set<String> companiesInPortfolio = new HashSet<>();
  String flexiblePortContainsCertainKeyLogger;
  String flexiblePortContainsCertainKeyReturnValue;
  HashMap<String, String> addStockDataFlexibleListLogger = new HashMap<>();
  String putCompanyNameInTickerFinderLog;
  String putCompanyNameInTickerFinderReturn;
  StringBuilder getFinalDataLog = new StringBuilder();
  private StringBuilder addApiCompanyStockDataLog = new StringBuilder("");
  private String addApiCompanyStockDataReturnValue;
  private StringBuilder checkIfTickerExistsLogger = new StringBuilder("");
  private String checkIfTickerExistsReturnValue;
  private StringBuilder putNameInCompanyInPortfolioLog = new StringBuilder("");
  private String putNameInCompanyReturnValue;
  private String setFlexibleAddPortfolioLog;
  private StringBuilder validDateLog = new StringBuilder();
  private String flexiblePortfolioContainsCertainKeyLog;
  private String logforParser = "The portfolio provided in the text file is not in proper "
          + "format,please look at the documentation";

  public String getSetFlexibleAddPortfolioLog() {

    return setFlexibleAddPortfolioLog;
  }

  public StringBuilder getLogForCostBasis() {
    return logForCostBasis;
  }

  public String getLogForCheckingHelper() {
    return logForCheckingHelperWorksCorrectly.toString();
  }

  public String getLogForHelper() {
    return logForHelper.toString();
  }

  public String getLogForGetPortfolioSize() {
    return LogForGetPortfolioSize.toString();
  }

  public String getLogForPortfolioKeys() {
    return logForGetPortfolioKeys.toString();
  }


  public String getLogForReadFromFile() {
    return logForReadFromFile.toString();
  }


  public String getLogForMakeString() {
    return logForMakeStringDate.toString();
  }

  public String getLogForIsValidDate() {
    return logForIsValidDate.toString();
  }


  public String getLogForAddPortfolioData() {
    return logForAddPortfolioData.toString();
  }


  public String getLogForCheckingCompanyNameExist() {
    return logForCheckingIfCompanyNameExist.toString();
  }

  public String getLogForSavePortfolio() {
    return logForSavePortfolio.toString();
  }


  public String getLogForSetCurrentDate() {
    return logForSetCurrentDate.toString();
  }


  public String getLogForPortfolioContainsCertainKey() {
    return logForPortfolioContainsCertainKey.toString();
  }


  public String getLogForHasAnotherPortfolioWithSameName() {
    return logForHasAnotherPortfolioWithSameName.toString();
  }


  public String getLogForTotalStockValue() {
    return logForTotalStockValue.toString();
  }


  public String getSetContainsGivenDate() {
    return logForSetContainsGivenDate.toString();
  }


  public String getLogForModelSavePortfolio() {
    return logForModelSavePortfolio.toString();
  }

  @Override
  public Map<String, Map<String, List<List<String>>>> getFlexiblePort() {
    return flexiblePort;
  }

  @Override
  public void setFlexibleNewPortfolio(String name, Map<String,
          List<List<String>>> companyDetails) {
    flexiblePort.put(name, companyDetails);
  }

  @Override
  public void setFlexiblePortfolioWith(String portfolioName, String keyName, List<String> val) {
    flexiblePort.get(portfolioName).put(keyName, List.of(val));
  }

  public String getFlexiblePortContainsCertainKeyLogger() {
    return flexiblePortContainsCertainKeyLogger;
  }


  public String getFlexiblePortContainsCertainKeyReturnValue() {
    return flexiblePortContainsCertainKeyReturnValue;
  }

  @Override
  public boolean flexiblePortContainsCertainKey(String name) {
    flexiblePortContainsCertainKeyLogger = "Received : " + name;
    flexiblePortContainsCertainKeyReturnValue = "" + containsHelper(name);
    return flexiblePort.containsKey(name);
  }


  public boolean containsHelper(String name) {
    return flexiblePort.containsKey(name);
  }

  public Map<String, List<List<String>>> getFlexiblePortfolio() {
    return flexiblePortfolio;
  }

  public Map<String, Integer> getTickerFinder() {
    return tickerFinder;
  }

  public List<HashMap<String, String>> getApiStockData() {
    return apiStockData;
  }

  @Override
  public List<String> getInitialOptions() {
    return initialOptions;
  }

  @Override
  public String getCurrentDate() {
    return currentDate;
  }

  //setter for currentDate
  @Override
  public void setCurrentDate(String currentDate) {
    logForSetCurrentDate.append(currentDate);
    this.currentDate = currentDate;
  }

  @Override
  public Map<String, List<List<String>>> getInflexiblePortfolio() {
    return inflexiblePortfolio;
  }

  @Override
  public void setInflexiblePortfolio(Map<String, List<List<String>>> inflexiblePortfolio) {
    this.inflexiblePortfolio = inflexiblePortfolio;
  }


  @Override
  public List<String> getStockCompanyName() {
    return stockCompanyName;
  }

  @Override
  public void getContentsFromFile() {
    for (String filepath : stockCompanies) {
      try {
        //change this when building jar file
        Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\res\\"
                + "stockData");
        String files = String.valueOf(path);

        data = new String(Files.readAllBytes(Path.of(files + "\\" + filepath)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      stockData.add(convertingStringToHashMap(data));
    }
  }

  @Override
  public HashMap<String, String> convertingStringToHashMap(String data) {
    HashMap<String, String> stockDateAndPrice = new HashMap<>();
    String[] breakingDownFullString = data.split("\r?\n|\r");
    for (String s : breakingDownFullString) {
      String[] separatingWithComa = s.split(",");
      if (separatingWithComa.length == 6) {
        stockDateAndPrice.put(separatingWithComa[0], separatingWithComa[4]);
      }
    }
    return stockDateAndPrice;
  }

  @Override
  public boolean hasAnotherPortfolioWithSameName(String name) {
    logForHasAnotherPortfolioWithSameName.append(name);
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public void addsFinalDataToPortfolio(List<List<String>> dataToAdd, String name,
                                       String currentDate) {
    logForAddPortfolioData.append(name);
    for (List<String> stringList : dataToAdd) {
      for (String s : stringList) {
        logForAddPortfolioData.append(" " + s);
      }
    }

    ArrayList<List<String>> finalData = new ArrayList<>();
    ArrayList<String> data;
    for (List<String> strings : dataToAdd) {
      data = new ArrayList<>(strings);
      finalData.add(data);
      data.add(currentDate);
    }
    inflexiblePortfolio.put(name, finalData);
  }

  @Override
  public boolean checkIfCompanyExists(String name) {
    logForCheckingIfCompanyNameExist.append("Received:" + name);
    return stockCompanyName.contains(name.toUpperCase());
  }

  @Override
  public void saveFlexiblePortfolios() {
    List<String> names = new ArrayList<>();
    flexiblePort.forEach((key, value) -> names.add(key));
    if (names.size() == 0) {
      return;
    }

    for (int i = 0; i < names.size(); i++) {
      Map<String, Map<String, List<List<String>>>> tester = new HashMap<>();
      tester.put(names.get(i), flexiblePort.get(names.get(i)));
      JsonPackage json = new JsonPackage(tester);
      List<String> jsonPortfolios = json.anotherFormatter();

      Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\"
              + "FlexiblePortfolios");
      String newPath = String.valueOf(path);
      newPath += "\\" + names.get(i);
      newPath += ".txt";
      try {

        File myObj = new File(newPath);
        Files.writeString(Path.of(newPath), jsonPortfolios.get(0));
      } catch (FileNotFoundException e) {
        //
      } catch (IOException e) {
        //System.out.println("Exception2");
      }

    }
  }

  @Override
  public void savePortfolio() {
    List<String> names = new ArrayList<>();
    inflexiblePortfolio.forEach((key, value) -> names.add(key));

    JsonPackage jsonp = new JsonPackage(this.inflexiblePortfolio, names);
    List<String> jsonPortfolios = jsonp.formatFromHashMap();


    Path path = Path.of(String.valueOf(Path.of(
            Path.of(System.getProperty("user.dir")) + "\\"
                    + "InFlexiblePortfolios")));
    for (int i = 0; i < jsonPortfolios.size(); i++) {
      String newPath = String.valueOf(path);
      newPath += "\\" + names.get(i);
      newPath += ".txt";
      try {
        File myObj = new File(newPath);
        Files.writeString(Path.of(newPath), jsonPortfolios.get(i));
      } catch (FileNotFoundException e) {
        //handled
      } catch (IOException e) {
        //
      }
    }
  }

  @Override
  public boolean isValidDate(String date) {
    logForIsValidDate.append("Received:" + date);
    try {
      LocalDate.parse(date);
      validDateLog.append("true");
      return true;
    } catch (DateTimeParseException e) {
      validDateLog.append("false");
      return false;
    }
  }


  public String getGetFinalDataLog() {
    return getFinalDataLog.toString();
  }


  public String portfolioUpdated() {
    return "PortfolioUpdated";
  }


  public String getEnterValidStockToSellLog() {
    return "Please enter a valid number. The number is either negative or more than the stocks "
            + "that exists.";
  }


  @Override
  public double getTotalStockValue(String portfolioName, String currentDate) {
    logForTotalStockValue.append(portfolioName);
    logForTotalStockValue.append(currentDate);
    double ans = 1;

    List<List<String>> contents = inflexiblePortfolio.get(portfolioName);
    for (List<String> content : contents) {
      String company = content.get(0);
      double numbers = Double.parseDouble(content.get(1));

      double price;
      try {
        price = Double.parseDouble(stockData.get(stockCompanyName.indexOf(company
                        .toUpperCase()))
                .get(currentDate));
        ans *= (price * numbers);
      } catch (NullPointerException e) {
        //caught
      }
    }
    logForModelSavePortfolio.append(ans);
    return ans;
  }

  @Override
  public int getPortfolioSize() {
    LogForGetPortfolioSize.append(this.inflexiblePortfolio.size());
    return inflexiblePortfolio.size();
  }

  @Override
  public int getFlexiblePortfolioSize() {
    return 0;
  }

  @Override
  public boolean portfolioContainsCertainKey(String name) {
    logForPortfolioContainsCertainKey.append(name);
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public String makeStringDate(int day, int month, int year) {
    logForMakeStringDate.append("Day:" + day + "Month:" + month + "Year:" + year);
    String dateVal;
    String monthVal;
    if (day <= 9) {
      dateVal = "0" + day;
    } else {
      dateVal = String.valueOf(day);
    }
    if (month <= 9) {
      monthVal = "0" + month;
    } else {
      monthVal = String.valueOf(month);
    }

    return year + "-" + monthVal + "-"
            + dateVal;
  }

  @Override
  public void makeListOfDates() {
    Map<String, String> container;
    for (HashMap<String, String> stockDatum : stockData) {
      container = stockDatum;
      container.forEach((key, value) -> listOfDates.add(key));
    }
  }

  @Override
  public boolean setContainsGivenDate(String date) {
    logForSetContainsGivenDate.append(date);
    return listOfDates.contains(date);
  }

  @Override
  public ArrayList<String> getPortfolioKeys() {
    List<String> keys = new ArrayList<>(inflexiblePortfolio.keySet());
    for (String key : keys) {
      logForGetPortfolioKeys.append(key);
    }
    return new ArrayList<>(inflexiblePortfolio.keySet());
  }

  @Override
  public LocalDate localDateParser(String currentDate) {
    return LocalDate.parse(currentDate);
  }

  @Override
  public HashMap<String, List<List<String>>> parseJson(String data) {
    logForParseJson.append(data);
    JsonPackage jsonp = new JsonPackage();
    HashMap<String, List<List<String>>> filePortfolio = jsonp.parser(data);
    return filePortfolio;
  }

  @Override
  public String readFromFile(String path) {
    logForReadFromFile.append("Received:" + path);
    StringBuilder output = new StringBuilder();
    try {
      FileReader filereader = new FileReader(path);
      int c = 0;
      while ((c = filereader.read()) != -1) {
        char character = (char) c;
        if (character == '"') {
          output.append("\"");
        } else {
          output.append(character);
        }
      }
    } catch (IOException e) {
      return "Failure";
      //throw new RuntimeException(e);
    }
    //System.out.println(output);
    logForSavePortfolio.append(output);
    return output.toString();
  }

  @Override
  public boolean checkParsedPortfolio(Map<String, List<List<String>>> parsedPortfolio) {
    List<String> keys = new ArrayList<>(parsedPortfolio.keySet());
    List<List<String>> contents1;
    List<String> insideContents1;
    for (String s : keys) {
      logForCheckParsedPortfolio.append(s);
      contents1 = parsedPortfolio.get(s);
      for (List<String> contents : contents1) {
        insideContents1 = new ArrayList<>(contents);
        logForCheckParsedPortfolio.append(insideContents1.get(0));
        logForCheckParsedPortfolio.append(insideContents1.get(1));
        logForCheckParsedPortfolio.append(insideContents1.get(2));
      }
    }

    List<String> keyset = new ArrayList<>(parsedPortfolio.keySet());
    List<List<String>> contents;
    List<String> insideContents;
    for (String s : keyset) {
      contents = parsedPortfolio.get(s);
      for (List<String> content : contents) {
        insideContents = new ArrayList<>(content);
        if (insideContents.size() != 3) {
          return false;
        }
        try {
          Double.parseDouble(insideContents.get(1));
        } catch (NumberFormatException e) {
          return false;
        }
        try {
          LocalDate.parse(insideContents.get(2));
          logForCostBasis.append(insideContents);
        } catch (DateTimeParseException e) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public List<String> getListOfPortfolio(int choice) {
    Path path = null;
    if (choice == 1) {
      path = Path.of(System.getProperty("user.dir") + "\\"
              + "InFlexiblePortfolios");
    } else if (choice == 2) {
      path = Path.of(System.getProperty("user.dir") + "\\"
              + "FlexiblePortfolios");
    }
    List<String> files;
    File f = new File(String.valueOf(path));
    files = List.of(f.list());
    return files;
  }

  @Override
  public Double helper(Double val) {
    logForHelper.append("Received:" + val);
    logForCheckingHelperWorksCorrectly.append(Double.valueOf(Math.round(val)));
    return Double.valueOf(Math.round(val));
  }

  @Override
  public void createDirectory() {
    try {
      Files.createDirectories(Path.of(String.valueOf(Path.of(
              Path.of(System.getProperty("user.dir")) + "\\"
                      + "InFlexiblePortfolios"))));
      Files.createDirectories(Path.of(String.valueOf(
              Path.of(Path.of(System.getProperty("user.dir")) + "\\"
                      + "FlexiblePortfolios"))));
    } catch (IOException e) {
      //
    }
  }


  public String getAddApiCompanyStockDataLog() {
    return addApiCompanyStockDataLog.toString();
  }

  @Override
  public String addApiCompanyStockData(String companyTicker) {
    addApiCompanyStockDataLog.append("Received : " + companyTicker);
    InputDataSource inp = new AlphaVantageApi();
    String successOrFailure = inp.getData(companyTicker);
    if (successOrFailure.equals(apiErrorMessage)) {
      addApiCompanyStockDataReturnValue = "failure";
      return "failure";
    }
    addApiCompanyStockDataReturnValue = successOrFailure;
    return successOrFailure;
  }


  public String getLogforCheckIfTickerExist() {
    return checkIfTickerExistsLogger.toString();
  }


  public String getCheckIfTickerExistsReturnValue() {
    return checkIfTickerExistsReturnValue;
  }

  @Override
  public boolean checkIfTickerExists(String ticker) {
    checkIfTickerExistsLogger.append("Received : " + ticker);
    checkIfTickerExistsReturnValue = String.valueOf(companiesInPortfolio.contains(ticker));
    return companiesInPortfolio.contains(ticker);
  }

  @Override
  public boolean flexiblePortfolioContainsCertainKey(String name) {
    flexiblePortfolioContainsCertainKeyLog = "Received " + name;
    return flexiblePort.containsKey(name);
  }


  public String getValidDateLog() {
    return validDateLog.toString();
  }


  public HashMap<String, String> getAddStockDataFlexibleListLogger() {
    return addStockDataFlexibleListLogger;
  }

  @Override
  public void addStockDataToFlexibleList(HashMap<String, String> stockData) {
    addStockDataFlexibleListLogger = stockData;
    apiStockData.add(stockData);
  }

  @Override
  public int getApiStockDataSize() {
    return this.apiStockData.size();
  }


  public String getPutCompanyNameInTickerFinderLog() {
    return putCompanyNameInTickerFinderLog;
  }


  public String getPutCompanyNameInTickerFinderReturn() {
    return putCompanyNameInTickerFinderReturn;
  }

  @Override
  public void putCompanyNameInTickerFinder(String name, int number) {
    putCompanyNameInTickerFinderLog = "Received : " + name + ":" + number;
    tickerFinder.put(name, number);
    putCompanyNameInTickerFinderReturn = "" + putCompanyNameInTickerFinderReturn(name, number);
  }

  private boolean putCompanyNameInTickerFinderReturn(String name, int number) {
    if (tickerFinder.containsKey(name)) {
      return tickerFinder.get(name).equals(number);
    }
    return false;
  }

  @Override
  public void setterForFlexiblePortfolio(String name, List<List<String>> companyDetails) {
    flexiblePortfolio.put(name, companyDetails);
  }


  public String getPutNameInCompanyInPortfolioLog() {
    return putNameInCompanyInPortfolioLog.toString();
  }

  @Override
  public void putNameInCompanyInPortfolio(String name) {
    putNameInCompanyInPortfolioLog.append("Received : " + name);
    companiesInPortfolio.add(name);
    putNameInCompanyReturnValue = "" + putNameInCompanyReturnValue(name);
  }


  public String getPutNameInCompanyReturnValue() {
    return putNameInCompanyReturnValue;
  }

  private boolean putNameInCompanyReturnValue(String name) {
    return companiesInPortfolio.contains(name);
  }


  public String getflexiblePortfolioContainsCertainKeyLog() {
    return flexiblePortfolioContainsCertainKeyLog;
  }

  @Override
  public void setFlexibleAddPortfolio(String portfolioName, String key, List<String> companies) {
    List<List<String>> company = new ArrayList<>();
    setFlexibleAddPortfolioLog = "Receive" + "name: " + portfolioName + "key:" + key;
    company.addAll(flexiblePort.get(portfolioName).get(key));
    company.add(companies);
    flexiblePort.get(portfolioName).put(key, company);
  }

  @Override
  public Map<String, Map<String, List<List<String>>>> parseFlexiblePortfolio(String data) {
    JsonPackage json = new JsonPackage();
    Map<String, Map<String, List<List<String>>>> check = json.anotherParser(data);
    return check;
  }

  @Override
  public void setFlexible(Map<String, Map<String, List<List<String>>>> parsed) {
    this.flexiblePort = parsed;
  }

  @Override
  public Map<String, List<List<String>>> getParticularFlexiblePortfolio(String portfolioName) {
    return null;
  }

  @Override
  public void removeTickerFromPortfolio(String ticker, String portfolioName) {
    //
  }

  @Override
  public List<String> getCompaniesInCertainPortfolio(String portfolioName) {
    return new ArrayList<>(flexiblePort.get(portfolioName).keySet());
  }

  @Override
  public List<List<String>> getStockDataInCertainPortfolio(String portfolioName,
                                                           String companyName) {
    return flexiblePort.get(portfolioName).get(companyName);
  }

  @Override
  public HashMap<String, Double> getTotalFlexibleStockValue(String portfolioName,
                                                            String currentDate) {
    HashMap<String, Double> finalData = new HashMap<>();
    Double[] result = {0.0};
    Map<String, List<List<String>>> contents = flexiblePort.get(portfolioName);

    contents.forEach((key, value) -> {
      int ticker = getTickerFinder().get(key);
      HashMap<String, String> companyStock = getApiStockData().get(ticker);
      if (companyStock.get(currentDate) != null) {
        double valueOfStocks = Double.parseDouble(companyStock.get(currentDate));
        double totalStock = 0.0;
        result[0] = 0.0;
        for (int i = 0; i < value.size(); i++) {
          int compareDate =
                  LocalDate.parse(value.get(i).get(3)).compareTo(LocalDate.parse(currentDate));
          if (compareDate <= 0) {
            if (value.get(i).get(0).equals("Buy")) {
              totalStock += Double.parseDouble(value.get(i).get(2));
            } else if (value.get(i).get(0).equals("Sell")) {
              totalStock -= Double.parseDouble(value.get(i).get(2));
            }
          }
        }
        result[0] += valueOfStocks * totalStock;
      } else {
        result[0] = 0.0;
      }
      finalData.put(key, result[0]);
    });
    getFinalDataLog.append(finalData);
    System.out.println(finalData);
    return finalData;
  }

  public String getLogForParser() {
    return logforParser;
  }

  public String getAddApiCompanyStockDataReturnValue() {
    return addApiCompanyStockDataReturnValue;
  }
}
