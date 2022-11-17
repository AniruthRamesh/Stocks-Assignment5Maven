package Command;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It's a helper class for the getting date, validating if it is a validate or not.
 */
public class DateHelper {
  View view;
  Model model;
  Scanner sc;

  /**
   * Constructor for the DateHelper class. Initializes view,model,sc with values from parameter.
   *
   * @param view  View object to use.
   * @param model Model object to use.
   * @param sc    Scanner object to use.
   */
  public DateHelper(View view, Model model, Scanner sc) {
    this.model = model;
    this.sc = sc;
    this.view = view;
  }

  /**
   * It asks the user for a date, and returns the date in the form of a string.
   *
   * @return A string that is the date that the user wants to change,empty if it is invalid date.
   */
  String helper() {
    view.dateSelectionMenu();
    String date = "";
    int choice;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return "";
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
        return "";
      }
      if (day > 31 || day == 0) {
        view.displayEnterValidDetailsForDate();
        return "";
      }
      view.askForMonth();
      try {
        month = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return "";
      }
      if (month > 12 || month == 0) {
        view.displayEnterValidDetailsForDate();
        return "";
      }
      view.askForYear();
      try {
        year = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return "";
      }
      if (year > 2022 || year < 1999) {
        view.displayEnterValidDetailsForDate();
        return "";
      }

      String dateWishToChange = model.makeStringDate(day, month, year);
      if (model.isValidDate(dateWishToChange)) {
        date = dateWishToChange;
      }
    } else if (choice == 2) {
      //
    } else {
      view.displaySwitchCaseDefault();
    }

    return date;
  }
}
