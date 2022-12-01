# PDP Stocks- Features

## Introduction

This is a stock project in Java where the user can see the listed stocks, create a portfolio,
save the portfolio and later retrieve it. New features are added in which user can modify an
existing portfolio, calculate cost basis and total value of portfolio on a particular date. Also,
the portfolio performance is displayed at glance in form of bar graph.

## Authors

Aniruth Ramesh

Rashi Jain

## About Readme

This Readme comprise all the currently working features in our project.

## Features of our project

1. Allow a user to create one or more portfolios with shares of one or more stock. Once created,
   shares cannot be added or removed from the portfolio.

2. Examine the composition of a portfolio.

3. Determine the total value of a portfolio on a certain date.

4. Persisting a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files).

5. Files saved are in human-readable format. User can also upload a portfolio.

6. Same name portfolio cannot be created.

7. User cannot buy stocks in fraction.

8. User can check the value of stock on any date he wants.

9. Portfolio files created are only read-only.

10. The user can change the date and set the desired date for buying stocks.

11. Suitable text-based interface is created that allows a user to use all the above-mentioned
functionality.

## New Features Added (Assignment 5):


12. Purchase a specific number of shares of a specific stock on a specified date, and add them to
the portfolio.

13. Sell a specific number of shares of a specific stock on a specified date from a given portfolio.

14. Determine the cost basis (i.e. the total amount of money invested in a portfolio) by a specific
date. This would include all the purchases made in that portfolio till that date.

15. Determine the value of a portfolio on a specific date (to be exact, the end of that day). The
value for a portfolio before the date of its first purchase would be 0, since each stock in the
portfolio now was purchased at a specific point in time.

16. Added support to specify and incorporate commissions fees in your program.

17. Commission rates are included as part of cost basis when calculated.

18. Integrated the Alpha Vantage API for fetching stock data.

19. Displaying performance at a glance for a specific portfolio and a specified time range as bar
graph.

20. Suitable text-based interface is created that allows a user to use all the above-mentioned
    functionality.

## New Features Added (Assignment 6):

21. Ability to invest a specific amount in an existing flexible portfolio on a specific date by
    specifying the weights of how that money should be invested in each stock inside that portfolio.

22. ability to create a portfolio using dollar-cost averaging as specified above, and query cost
    basis and value of such a portfolio at a specific date.

23. Created a GUI that has the following features:

    - The ability to create a new flexible portfolio

    - The ability to buy/sell stocks by specifying the number of shares , and date (with or without commission fees)

    - The ability to query the cost basis and value of a flexible portfolio at a certain date

    - The ability to save and retrieve flexible portfolios from files

    - The ability to invest a specific amount in an existing flexible portfolio on a specific date by specifying the weights of how that money should be invested in each stock inside that portfolio.

    - The ability to create a portfolio using dollar-cost averaging as specified above, and query cost basis and value of such a portfolio at a specific date.

24. Created Line graph for performance in GUI.