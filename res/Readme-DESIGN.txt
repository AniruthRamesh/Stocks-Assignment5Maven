# PDP Stocks- Design

## Introduction

This Stocks project in Java is using MVC approach.

* model: It is an object to carry the data that can also contain the logic to update controller if
  data is changed.
* view: It is used to visualize the data that the model contains.
* controller: It works on both the model and view. It is used to manage the flow of application,
  i.e. data flow in the model object and to update the view whenever data is changed.

## Authors

Aniruth Ramesh

Rashi Jain

## About Readme

This Readme comprise the design strategy used in the project.

## Design

We have a model Interface, and we have a class which implements the Interface.

```
public interface model {}
public ModelImpl implements model{}
```

We have a controller Interface and a class implementing the interface.

```
public interface controller {}
public controllerImpl implements controller{}
```

Even though its text-based interface, instead of having all the text in controller itself, we have a
separate class,

```
public class view {}
```

The model uses a helper class Which has two methods as defined below,

```
public class Json {
    List<String> jsonFormatFromHashMap() {}
    HashMap<String, List<List<String>>> jsonParser(String json) {}
}
```

This class is a custom Json Parser class used by the model to convert a string in Json Format to
HashMap of String and List of List of String(Our representation of Portfolios ) and vice versa.

## Design Changes from previous assignment in model:

* In the model interface, we added new methods to accommodate for our changes for flexible
portfolio.

Following are the new methods added :

1. Method to get Total Stock Value for flexible portfolio.
 ```
HashMap<String, Double> getTotalFlexibleStockValue(String portfolioName, String currentDate);
```
2. Method returns a map of the portfolio name and a list of lists of the portfolio's
   * flexible positions.
 ```
 Map<String, List<List<String>>> getParticularFlexiblePortfolio(String portfolioName);
```

3. Method to set Flexible Portfolio.
 ```
 void setFlexible(Map<String, Map<String, List<List<String>>>> parsed);
```

4. Method to parse Flexible Portfolio.
 ```
 Map<String, Map<String, List<List<String>>>> parseFlexiblePortfolio(String data);
```

5. Method to get Flexible Portfolio.
 ```
 Map<String, Map<String, List<List<String>>>> getFlexiblePort();
```

6. Method to save flexible portfolio.
```
 void saveFlexiblePortfolios();
```
7. Method to get API Stock Data

```
List<HashMap<String, String>> getApiStockData();
```
8. Method to get size of API stock data
```
int getApiStockDataSize();
```
9. Method is to find if a portfolio is present in the current session.
```
boolean flexiblePortfolioContainsCertainKey(String name);
```
10. Method creates an Object of InputDataSource(with the source which we are using, currently we
are using AlphaVantageAPI).
```
String addApiCompanyStockData(String companyTicker);
```

## Design Changes from previous assignment in controller(Assignment : 5):

* We were using switch cases inside our controller earlier. In code walk got a suggestion that we
can
use
command pattern in our project. We implemented the same in controller.
 It gave us the following advantages:

   -- It decoupled the classes that invoke the operation from the object that knows how to execute
    the operation.

   -- It allows us to create a sequence of commands by providing a queue system.

The new files that we created are :

1. command Interface
```java
public interface command {}
```
It acts as a helper interface for our controller. We are following command design pattern to
overcome the problem of having our controller polluted with several methods.

2. Class to handle Cost Basis.
```java
public class HandleCostBasis implements command {}
```
Handles calculating the cost basis, and it tells the model to calculate necessary logic and
tells view what to display of the cost basis.

3. Class to handle Mutable portfolio Creation
```
public class HandleMutablePortfolioCreation implements command {}
```
handles the creation of a mutable portfolio. Stocks can be added or to a new portfolio or add
stock to existing portfolio.

4. Class to handle portfolio composition
```
public class HandlePortfolioComposition implements command {}
```
Handles what is the composition of portfolio is.

5. Class to handle sell portfolio
```
public class HandleSellPortfolio implements command {}
```
It handles the selling of stocks of a portfolio on any date.

6. Classs to handle total stock value of flexible portfolio.
```
public class HandleTotalStockForFlexiblePortfolio implements command {
```
Handles total stock value of flexible portfolio on any date.

7. Class to upload file
```
public class HandleUploadFile implements command {}
```
handles the upload file command from the controller interface. Asks the user if the uploaded file
 is inflexible or flexible and validates the contents of the file, if it is a valid file stores the
 content of the file in model.

## Design Changes from previous assignment in controller(Assignment : 6):

1. New View interface created for GUI
```
public interface ViewGui {}
```
2. New ViewGuiImpl class created for GUI
```
public class ViewGuiImpl extends JFrame implements ViewGui {}
```
3. All the methods required for GUI are implemented in this above-mentioned.

4. A new Controller for GUI is implemented.
```
public interface Features {}
```
5. New controller class implementing the Features
```
public class ControllerGUIImpl implements Features {}
```
6. No major model changes done.
}
}