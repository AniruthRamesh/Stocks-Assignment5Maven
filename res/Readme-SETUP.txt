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

* The jar file can work only from the res folder, as the stock data files are there.

* Stable internet connection.

* All the stock data retrieved is in res/stockData.

* For now, jar file is in the folder res.

* Project Structure is as follows:
<img width="240" alt="image" src="https://user-images.githubusercontent.com/113320518/199860480-f26212d2-7c3e-4a7c-b647-b5b2a7adb7a9.png">


* We are using stock data of 13 companies ranging from dates 02-02-2001 to 10-25-2022 (mm-dd-yyyy) for inflexible portfolio.

* The new created Inflexible portfolios are saved in the parent of current working directory in InflexiblePortfolios (a
  new folder created).
* The new created Flexible portfolios are saved in the parent of current working directory in FlexiblePortfolios (a
  new folder created).

* Each text file uploaded by the user as input should contain only one portfolio.

* While uploading the file, the user should give the full path of the file like - C:
  \Users\96ras\Desktop\stocks\src\stockData\abc.txt

* The files created using this program is read only, so if you try to create another portfolio with same name, it will not be overwritten. Nothing will happen if you use already existing portfolio
  name.


