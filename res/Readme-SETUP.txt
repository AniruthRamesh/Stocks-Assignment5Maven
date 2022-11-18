# PDP Stocks- Setup

## Introduction

This is a stock project in Java where the user can see the listed stocks, create a portfolio, save
the portfolio and later retrieve it. New features are added in which user can modify an
existing portfolio, calculate cost basis and total value of portfolio on a particular date. Also,
the portfolio performance is displayed at glance in form of bar graph.

## Author

Aniruth Ramesh

Rashi Jain

## About Readme

This Readme comprise all the setup required by the project.

## Setup

* Java 18 and JDK 11 required.

* The jar file can work only from the res folder. As the stock data for inflexible portfolio is there.

* Stable internet connection.

* All the stock data retrieved is in res/stockData.

* For now, jar file is in the folder res.

* We are using stock data of 13 companies ranging from dates 02-02-2001 to 16-11-2022 (mm-dd-yyyy)
for inflexible portfolio.

* The new created Inflexible portfolios are saved in the parent of current working directory in InflexiblePortfolios (a
  new folder created).
* The new created Flexible portfolios are saved in the parent of current working directory in FlexiblePortfolios (a
  new folder created).

* Each text file uploaded by the user as input should contain only one portfolio.

* While uploading the file, the user should give the full path of the file like - C:
  \Users\96ras\Desktop\stocks\src\stockData\abc.txt

* The files created using this program is read only, so if you try to create another portfolio with same name, it will not be overwritten. Nothing will happen if you use already existing portfolio
  name.

* We are using AlphaVantage API so our program supports all ticker symbol in that API. Example - AAPL(apple), AMZN (Amazon), MSFT(Microsoft), SBUX(Starbucks), DIS(Disney), MCD(McDonalds). The dates are based on the availability in the API. If the date is not available the user will be prompted to enter date again.

* For commission, we are including and deducting a fixed rate of 10% in every transaction of buy and sell. We are not asking for commission rate from the user.

* UML Diagram, Jar file and Bar chart are in res folder.

## Steps to run program for grading (Flexible portfolio)-
* On start of the program it shows menu with all options.
* Select option 7 Create portfolio
* Option 1 Enter name of portfolio
* click 1 again and give the name of portfolio (like fees)
* click 3 to add company stock
* click 1 and enter ticker symbol (like AAPL). If its the first time, adding stocks of this company it will take 2-5 secs for the next step to load as data is fetched from the API.
* click 1 to enter date.
* enter day - 02
* enter month - 02
* enter year - 2006
* Enter number of stocks - 16
* Enter 1 to enter another company's ticker like AMZN
* click 1 to enter date.
* enter day - 12
* enter month - 12
* enter year - 2006
* Enter number of stocks - 18
* Enter 1 to enter another company's ticker like MSFT
* click 1 to enter date.
* enter day - 25
* enter month - 10
* enter year - 2022
* Enter number of stocks - 100
* click 2 to exit
* click 4 to return to main menu
* click 10 to check the value on specific date
* click 1 to enter portfolio name
* enter 3 to query value on different date
* click 1 to enter date.
* enter day - 15
* enter month - 11
* enter year - 2022
* The value of portfolio will be displayed
* click 4 to return to main menu.
* click 9 for cost basis
* enter name of portfolio
* click 1 to enter date.
* enter day - 16
* enter month - 11
* enter year - 2022
* The cost basis data will be displayed

