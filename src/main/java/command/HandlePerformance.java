package command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Model;
import view.View;

public class HandlePerformance implements Command {
  Model model;
  View view;
  Scanner sc;

  /**
   * Constructor for this class. Gets what model, view, scanner to use and processes
   * accordingly.
   *
   * @param model Model object.
   * @param view  View object.
   * @param sc    Scanner object.
   */
  public HandlePerformance(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;

  }

  @Override
  public Model execute() {
    view.displayEnterNameForPortfolio();
    sc.nextLine();
    String portfolioName = sc.nextLine();
    if (!model.flexiblePortContainsCertainKey(portfolioName)) {
      view.displayNoSuchPortfolio();
      return model;
    }
    view.displayEnterStartingDateFirstAndEndingSecond();

    DateHelper dateValue = new DateHelper(view, model, sc);
    String startingDate = dateValue.helper();
    if (startingDate.length() == 0) {
      view.displayDateIsNotValid();
      return model;
    }
    String endingDate = dateValue.helper();
    if (endingDate.length() == 0) {
      view.displayDateIsNotValid();
      return model;
    }
    if (startingDate.compareTo(endingDate) >= 0) {
      view.displayEndingDateCannotBeSameOrSmallerThanStartingDate();
      return model;
    }
    long differenceBetweenDates = model.numberOfDays(LocalDate.parse(startingDate),
            LocalDate.parse(endingDate));

    if (differenceBetweenDates < 10) {
      view.displayRangeIsSmall();
      return model;
    }

    if (!(model.isValidDate(startingDate) && model.isValidDate(endingDate))) {
      view.displayDateIsNotValid();
      return model;
    }
    LocalDate dateStartingDate = LocalDate.parse(startingDate);
    LocalDate dateEndingDate = LocalDate.parse(endingDate);

    Map<String, String> dateValues = new HashMap<>();
    if (differenceBetweenDates >= 1825) {
      dateValues = model.calculatingYears(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 1825 && differenceBetweenDates >= 900) {
      dateValues = model.moreMonths(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 900 && differenceBetweenDates >= 150) {
      dateValues = model.calculatingMonths(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 150 && differenceBetweenDates >= 30) {
      dateValues = model.moreDays(dateStartingDate, dateEndingDate);
    } else if (differenceBetweenDates < 30 && differenceBetweenDates >= 5) {
      dateValues = model.calculatingDays(dateStartingDate, dateEndingDate);
    }

    List<String> keys = new ArrayList<>(dateValues.keySet());
    List<Double> values = new ArrayList<>();
    for (int i = 0; i < keys.size(); i++) {
      values.add(model.calculateValueForGraph(model.getTotalFlexibleStockValue(portfolioName
              , dateValues.get(keys.get(i)))));
    }
    view.displayContentsInParameter("Performance of portfolio " + portfolioName + " from " +
            startingDate + " to " + endingDate);
    view.displayEmptyLine();
    for (int i = 0; i < values.size(); i++) {
      int val = values.get(i).intValue();
      view.displayContentsInParameter(keys.get(i) + ": " + helper(val / 1000));
    }
    view.displayEmptyLine();
    view.displayContentsInParameter("Scale: * = $1000");
    view.displayEmptyLine();

    return model;
  }

  private String helper(int number) {
    StringBuilder help = new StringBuilder();
    for (int i = 0; i < number; i++) {
      help.append("*");
    }
    return help.toString();
  }


}
