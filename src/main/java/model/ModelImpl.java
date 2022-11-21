package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import inputdata.AlphaVantageApi;
import inputdata.InputDataSource;
import outputdatasource.JsonPackage;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class is the implementation of the Model interface. It manages how the data modifications
 * are done in our code.
 */
public class ModelImpl implements Model {
  List<String> stockCompanies = List.of("AAPL.txt", "AMZN.txt", "ATVI.txt", "BCS.txt", "CAJ.txt",
          "CSCO.txt", "DIS.txt", "JPM.txt", "MCD.txt", "MSFT.txt", "ORCL.txt", "SBUX.txt", "WFC"
                  + ".txt");

  List<String> stockCompanyName = List.of("APPLE", "AMAZON", "ACTIVISION", "BARCLAYS", "CANON "
                  + "INC", "CISCO SYSTEMS", "DISNEY", "JP MORGAN", "MCDONALD", "MICROSOFT",
          "ORACLE",
          "STARBUCKS", "WELLS FARGO");

  String apiErrorMessage = "{\n" + "    \"Error Message\": \"Invalid API call. Please retry or "
          + "visit the documentation " + "(https://www.alphavantage.co/documentation/) for "
          + "TIME_SERIES_DAILY.\"\n" + "}";
  //ArrayList of HashMap containing StockData of companies with date as key and stock value on
  //that date as value.
  List<HashMap<String, String>> stockData = new ArrayList<>();
  String startingDate = "2001-02-02";
  //String endingDate = "2022-10-25";

  String currentDate = startingDate;

  Set<String> listOfDates = new HashSet<>();
  String data;

  Map<String, List<List<String>>> inflexiblePortfolio = new HashMap<>();

  List<String> initialOptions = List.of("Create Inflexible Portfolio", "Examine Composition of "
                  + "current Portfolio", "Fast Forward Time", "Determine value of stocks on certain"
                  + " Date",
          "Upload a portfolio", "List all portfolios", "Create Flexible Portfolio", "Sell Stocks "
                  + "from a Portfolio", "Determine Cost Basis", "Determine value of Flexible "
                  + "portfolio on certain Date", "Flexible Portfolio performance", "Exit");

  Map<String, Map<String, List<List<String>>>> flexiblePort = new HashMap<>();
  Map<String, List<List<String>>> flexiblePortfolio = new HashMap<>();
  List<HashMap<String, String>> apiStockData = new ArrayList<>();

  Map<String, Integer> tickerFinder = new HashMap<>();

  Set<String> companiesInPortfolio = new HashSet<>();
  private Map<String, String> dates = new HashMap<>();

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

  @Override
  public boolean flexiblePortContainsCertainKey(String name) {
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
    this.currentDate = currentDate;
  }

  //getter for portfolio
  @Override
  public Map<String, List<List<String>>> getInflexiblePortfolio() {
    return inflexiblePortfolio;
  }

  @Override
  public void setInflexiblePortfolio(Map<String, List<List<String>>> inflexiblePortfolio) {
    this.inflexiblePortfolio = inflexiblePortfolio;
  }

  //getter for stockCompanyName
  @Override
  public List<String> getStockCompanyName() {
    return stockCompanyName;
  }

  //get All the stock data from the given text file and create a hashMap for each company,
  //add the hashmap to the stockData arraylist.
  @Override
  public void getContentsFromFile() {
    for (String filepath : stockCompanies) {
      try {
        Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\" + "stockData");


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
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public void addsFinalDataToPortfolio(List<List<String>> dataToAdd, String name,
                                       String currentDate) {
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
    return stockCompanyName.contains(name.toUpperCase());
  }

  @Override
  public void saveFlexiblePortfolios() {
    List<String> names = new ArrayList<>();
    flexiblePort.forEach((key, value) -> names.add(key));
    if (names.size() == 0) {
      return;
    }
    //Json json = new Json(this.flexiblePortfolio, names);
    for (int i = 0; i < names.size(); i++) {
      Map<String, Map<String, List<List<String>>>> tester = new HashMap<>();
      tester.put(names.get(i), flexiblePort.get(names.get(i)));
      JsonPackage json = new JsonPackage(tester);
      List<String> jsonPortfolios = json.anotherFormatter();

      Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\" + "FlexiblePortfolios");
      String newPath = String.valueOf(path);
      newPath += "\\" + names.get(i);
      newPath += ".txt";
      try {

        File myObj = new File(newPath);
        Files.writeString(Path.of(newPath), jsonPortfolios.get(0));
      } catch (IOException e) {
        //
      }
    }
  }

  @Override
  public void savePortfolio() {
    List<String> names = new ArrayList<>();
    inflexiblePortfolio.forEach((key, value) -> names.add(key));
    //Json json = new Json(this.inflexiblePortfolio, names);

    JsonPackage jsonp = new JsonPackage(this.inflexiblePortfolio, names);
    List<String> jsonPortfolios = jsonp.formatFromHashMap();
    Path path = Path.of(String.valueOf(Path.of(Path.of(System.getProperty("user.dir")) + "\\"
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
    try {
      LocalDate.parse(date);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  //if there is no stock data on certain date, we add 0.
  @Override
  public double getTotalStockValue(String portfolioName, String currentDate) {
    double ans = 1;

    List<List<String>> contents = inflexiblePortfolio.get(portfolioName);
    for (List<String> content : contents) {
      String company = content.get(0);
      double numbers = Double.parseDouble(content.get(1));

      double price;
      try {
        price = Double.parseDouble(stockData.get(stockCompanyName.indexOf(company.toUpperCase()))
                .get(currentDate));
        ans *= (price * numbers);
      } catch (NullPointerException e) {
        //caught
      }
    }
    return ans;
  }

  @Override
  public int getPortfolioSize() {
    return inflexiblePortfolio.size();
  }

  @Override
  public int getFlexiblePortfolioSize() {
    return flexiblePort.size();
  }

  @Override
  public boolean portfolioContainsCertainKey(String name) {
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public String makeStringDate(int day, int month, int year) {
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

    return year + "-" + monthVal + "-" + dateVal;
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
    return listOfDates.contains(date);
  }

  @Override
  public ArrayList<String> getPortfolioKeys() {
    return new ArrayList<>(inflexiblePortfolio.keySet());
  }

  @Override
  public LocalDate localDateParser(String currentDate) {
    return LocalDate.parse(currentDate);
  }

  @Override
  public HashMap<String, List<List<String>>> parseJson(String data) {
    JsonPackage jsonp = new JsonPackage();
    return jsonp.parser(data);
  }

  @Override
  public String readFromFile(String path) {
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
    return output.toString();
  }

  @Override
  public boolean checkParsedPortfolio(Map<String, List<List<String>>> parsedPortfolio) {
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
      path = Path.of(System.getProperty("user.dir") + "\\" + "InFlexiblePortfolios");
    } else if (choice == 2) {
      path = Path.of(System.getProperty("user.dir") + "\\" + "FlexiblePortfolios");
    }
    //Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\" + "portfolios");
    List<String> files;
    File f = new File(String.valueOf(path));
    files = List.of(f.list());
    return files;
  }

  @Override
  public Double helper(Double val) {
    return (double) Math.round(val);
  }

  @Override
  public void createDirectory() {
    try {
      Files.createDirectories(Path.of(String.valueOf(Path.of(Path.of(System.getProperty("user"
              + ".dir")) + "\\" + "InFlexiblePortfolios"))));
      Files.createDirectories(Path.of(String.valueOf(Path.of(Path.of(System.getProperty("user"
              + ".dir")) + "\\" + "FlexiblePortfolios"))));
    } catch (IOException e) {
      //
    }
  }

  @Override
  public String addApiCompanyStockData(String companyTicker) {
    InputDataSource inp = new AlphaVantageApi();
    String successOrFailure = inp.getData(companyTicker);
    if (successOrFailure.equals(apiErrorMessage)) {
      return "failure";
    }
    return successOrFailure;
  }

  @Override
  public boolean checkIfTickerExists(String ticker) {
    return companiesInPortfolio.contains(ticker);
  }

  @Override
  public boolean flexiblePortfolioContainsCertainKey(String name) {
    return flexiblePort.containsKey(name);
  }

  @Override
  public void addStockDataToFlexibleList(HashMap<String, String> stockData) {
    apiStockData.add(stockData);
  }

  @Override
  public int getApiStockDataSize() {
    return this.apiStockData.size();
  }

  @Override
  public void putCompanyNameInTickerFinder(String name, int number) {
    tickerFinder.put(name, number);
  }

  @Override
  public void setterForFlexiblePortfolio(String name, List<List<String>> companyDetails) {
    flexiblePortfolio.put(name, companyDetails);
  }

  @Override
  public void putNameInCompanyInPortfolio(String name) {
    companiesInPortfolio.add(name);
  }

  @Override
  public void setFlexibleAddPortfolio(String portfolioName, String key, List<String> companies) {
    List<List<String>> company = new ArrayList<>();
    company.addAll(flexiblePort.get(portfolioName).get(key));
    company.add(companies);
    flexiblePort.get(portfolioName).put(key, company);
  }

  @Override
  public Map<String, List<List<String>>> getParticularFlexiblePortfolio(String portfolioName) {
    return flexiblePort.get(portfolioName);
  }

  @Override
  public void removeTickerFromPortfolio(String ticker, String portfolioName) {
    flexiblePort.get(portfolioName).remove(ticker);
  }

  @Override
  public Map<String, Map<String, List<List<String>>>> parseFlexiblePortfolio(String data) {
    JsonPackage json = new JsonPackage();
    return json.anotherParser(data);
  }

  public void setFlexible(Map<String, Map<String, List<List<String>>>> parsed) {
    this.flexiblePort = parsed;
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
    final String[] currentDateCopy = {currentDate};
    contents.forEach((key, value) -> {
      int ticker = getTickerFinder().get(key);
      HashMap<String, String> companyStock = getApiStockData().get(ticker);
      if (!companyStock.containsKey(currentDateCopy[0])) {
        boolean continueOrNot = true;
        while (continueOrNot) {
          try {
            LocalDate date = LocalDate.parse(currentDateCopy[0]);
            currentDateCopy[0] = date.plusDays(1).toString();
            if (currentDateCopy[0].compareTo("2022-11-17") > 0) {
              currentDateCopy[0] = null;
              break;
            }
            if (companyStock.containsKey(currentDateCopy[0])) {
              continueOrNot = false;
            }
          } catch (Exception e) {
            continue;
          }
        }
      }


      if (companyStock.get(currentDateCopy[0]) != null) {
        double valueOfStocks = Double.parseDouble(companyStock.get(currentDateCopy[0]));
        double totalStock = 0.0;
        result[0] = 0.0;
        for (int i = 0; i < value.size(); i++) {
          int compareDate =
                  LocalDate.parse(value.get(i).get(3)).compareTo(LocalDate.

                          parse(currentDateCopy[0]));
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

    return finalData;
  }

  @Override
  public long numberOfDays(LocalDate date1, LocalDate date2) {
    return DAYS.between(date1, date2);
  }

  private List<String> helperForDate(LocalDate startingDate, LocalDate endingDate) {
    List<String> dates = new ArrayList<>();
    dates.add(startingDate.getMonth().toString());
    dates.add(String.valueOf(startingDate.getYear()));
    dates.add(endingDate.getMonth().toString());
    dates.add(String.valueOf(endingDate.getYear()));
    return dates;
  }

  private String helperForDay(int number, LocalDate date) {
    String day = "";
    if (number == 2) {
      day = date.getYear() + "-" + "02" + "-28";
    } else if (number == 4 || number == 6 || number == 9 || number == 11) {
      day = date.getYear() + "-" + String.format("%02d", number) + "-30";
    } else {
      day = date.getYear() + "-" + String.format("%02d", number) + "-31";
    }
    return day;
  }

  @Override
  public Map<String, String> calculatingMonths(LocalDate startingDate, LocalDate endingDate) {
    dates = new LinkedHashMap<>();
    LocalDate intermediate = startingDate;
    while (intermediate.isBefore(endingDate) || intermediate.isEqual(endingDate)) {
      int mon = intermediate.getMonthValue();
      String display = intermediate.getMonth().toString().substring(0, 3)
              + " " + intermediate.getYear();
      String day = helperForDay(mon, intermediate);
      dates.put(display, LocalDate.parse(day).toString());
      intermediate = intermediate.plusMonths(1);
    }
    return dates;
  }

  @Override
  public Map<String, String> calculatingYears(LocalDate startingDate, LocalDate endingDate) {
    dates = new LinkedHashMap<>();
    int year1 = startingDate.getYear();
    int year2 = endingDate.getYear();
    DateTimeFormatter formatUsing = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (int i = year1; i <= year2; i++) {
      String lastDay = i + "-12-31";
      LocalDate day = LocalDate.parse(lastDay, formatUsing);
      if (day.isAfter(LocalDate.now())) {
        dates.put(String.valueOf(i), LocalDate.now().toString());
      } else {
        dates.put(String.valueOf(i), day.toString());
      }
    }
    return dates;
  }

  @Override
  public Map<String, String> moreMonths(LocalDate startingDate, LocalDate endingDate) {
    dates = new LinkedHashMap<>();
    LocalDate temp = startingDate;

    while (temp.isBefore(endingDate) || temp.isEqual(endingDate)) {
      int mon = temp.getMonthValue();
      String display = String.format("%02d", mon) + " " + temp.getYear();
      String day = helperForDay(mon, temp);
      dates.put(display, LocalDate.parse(day).toString());
      temp = temp.plusMonths(5);
    }
    return dates;
  }

  @Override
  public Map<String, String> calculatingDays(LocalDate startingDate, LocalDate endingDate) {
    dates = new TreeMap<>();
    LocalDate temp = startingDate;
    while (temp.isBefore(endingDate) || temp.isEqual(endingDate)) {
      String display = temp.getYear() + "-" + String.format("%02d",
              temp.getMonthValue()) + "-" + String.format("%02d", temp.getDayOfMonth());
      String day = temp.toString();
      dates.put(display, LocalDate.parse(day).toString());
      temp = temp.plusDays(1);
    }
    return dates;
  }

  @Override
  public Map<String, String> moreDays(LocalDate startingDate, LocalDate endingDate) {
    dates = new LinkedHashMap<>();
    LocalDate temp = startingDate;
    while (temp.isBefore(endingDate) || temp.isEqual(endingDate)) {
      String display = temp.getDayOfMonth() + " " + temp.getMonth()
              + " " + temp.getYear();
      String day = temp.toString();
      dates.put(display, LocalDate.parse(day).toString());
      temp = temp.plusDays(5);
    }
    return dates;
  }

  @Override
  public Double calculateValueForGraph(HashMap<String, Double> totalValueData) {
    double totalValue = 0.0;
    for (Map.Entry<String, Double> set :
            totalValueData.entrySet()) {
      totalValue += set.getValue();
    }
    return totalValue;
  }
}
