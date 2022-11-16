package Command;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import Model.Model;
import View.View;

public class HandleTotalStockForFlexiblePortfolio implements Command {
  Scanner sc;
  View view;
  Model model;

  public HandleTotalStockForFlexiblePortfolio(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }


  @Override
  public Model execute() {
    boolean continueLoop = false;
    String name = "";
    int num = model.getFlexiblePortfolioSize();
    if (num == 0) {
      view.displayNoPortfolio();
    } else {
      while (!continueLoop) {
        int choice;
        view.displayStockValueMenu();
        try {
          choice = sc.nextInt();
        } catch (InputMismatchException e) {
          view.displayOnlyIntegers();
          sc.next();
          continue;
        }
        switch (choice) {
          case 1:
            if (name.length() != 0) {
              view.displayPortfolioNameAlreadyEntered();
              break;
            }
            //check if we need to add try catch
            view.displayEnterNameForPortfolio();
            sc.nextLine();
            name = sc.nextLine();


            if (!model.flexiblePortfolioContainsCertainKey(name)) {
              view.displayNoPortfolio();
              name = "";
            }
            break;
          case 2:
            if (name.length() == 0) {
              view.displayNameCannotBeEmpty();
              break;
            }
            String result = handleTotalStockOnCurrentDate(name, model.getCurrentDate());
            if (Objects.equals(result, "Failure")) {
              name = "";
              view.displayNoSuchPortfolio();
            }
            break;
          case 3:
            if (name.length() == 0) {
              view.displayNameCannotBeEmpty();
              break;
            }
            handleTotalStockOnDifferentDate(name);
            break;
          case 4:
            continueLoop = true;
            break;
          default:
            view.displaySwitchCaseDefault();
            break;
        }
      }
    }
    return model;
  }


  public String handleTotalStockOnCurrentDate(String portfolioName, String date) {
    HashMap<String, Double> totalValue = model.getTotalFlexibleStockValue(portfolioName, date);
    Double amount = 0.0;
    for (Map.Entry<String, Double> set : totalValue.entrySet()) {
      amount += set.getValue();
      view.displayTotalStockValue(portfolioName, model.getCurrentDate(),
              new BigDecimal(amount).toPlainString());
    }
    if (amount == -1) {
      return "Failure";
    }
    view.displayTotalStockValue(portfolioName, model.getCurrentDate(),
            new BigDecimal(amount)
                    .toPlainString());
    return "Success";
  }

  public void handleTotalStockOnDifferentDate(String portfolioName) {
    int choice;
    view.displaySelectDateOption(model.getCurrentDate());
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
    }

    if (choice == 1) {
      int day;
      int month;
      int year;
      view.askForDayOfTheMonth();
      try {
        day = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (day > 31 || day == 0) {
        view.displayEnterValidDetailsForDate();
        return;
      }
      view.askForMonth();
      try {
        month = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (month > 12 || month == 0) {
        view.displayEnterValidDetailsForDate();
        return;
      }
      view.askForYear();
      try {
        year = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return;
      }
      if (year > 2022 || year < 2001) {
        view.displayEnterValidDetailsForDate();
        return;
      }

      String dateWishToChange = model.makeStringDate(day, month, year);

      boolean checker1 = model.isValidDate(dateWishToChange);
      if (checker1) {
        //check if date exist
        boolean checker = model.setContainsGivenDate(dateWishToChange);
        if (checker) {
          HashMap<String, Double> totalValueData = model.getTotalFlexibleStockValue(portfolioName,
                  dateWishToChange);
          double totalValue = 0.0;
          for (Map.Entry<String, Double> set :
                  totalValueData.entrySet()) {
            totalValue += set.getValue();
            if (set.getValue() != 0) {
              view.displayTotalFlexValue(set.getKey(), String.valueOf(set.getValue()));

            } else {
              view.displayTotalFlexValue(set.getKey(), "no value for selected date");

            }

          }
          view.displayTotalStockValue(portfolioName, dateWishToChange, new BigDecimal(totalValue)
                  .toPlainString());
        } else {
          view.displayNoStockDataForGivenDate();
        }
      } else {
        view.displayDateIsNotValid();
      }
    } else if (choice == 2) {
      //
    } else {
      view.displaySwitchCaseDefault();
    }
  }
}

