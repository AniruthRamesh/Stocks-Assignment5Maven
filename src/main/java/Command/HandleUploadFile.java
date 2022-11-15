package Command;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It handles the upload file command.
 */
public class HandleUploadFile implements Command {
  final Model model;
  final View view;
  final Scanner sc;

  public HandleUploadFile(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.askForFilePath();
    sc.nextLine();
    String filepath = sc.nextLine();
    String isFileReadSuccessFull = model.readFromFile(filepath);
    if (isFileReadSuccessFull.equals("Failure")) {
      view.displayTheFilePathDoesNotExist();
      return model;
    }
    view.askForFlexibleOrInFlexible();
    int choice = 0;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      sc.next();
    }
    if (choice == 1) {
      Map<String, List<List<String>>> parsedPortfolio = model.parseJson(isFileReadSuccessFull);
      if (parsedPortfolio == null) {
        view.displayDataNotInProperFormat();
        return model;
      }
      boolean checker = false;
      try {
        checker = model.checkParsedPortfolio(parsedPortfolio);
      } catch (Exception e) {
        view.displayDataNotInProperFormat();
        return model;
      }
      if (!checker) {
        view.displayDataNotInProperFormat();
        return model;
      }
      model.setInflexiblePortfolio(parsedPortfolio);
      model.savePortfolio();
    } else if (choice == 2) {
      Map<String, Map<String, List<List<String>>>> parseFlexiblePortfolio =
              model.parseFlexiblePortfolio(isFileReadSuccessFull);
      List<String> keys = new ArrayList<>();
      try {
        keys = new ArrayList<>(parseFlexiblePortfolio.keySet());
      } catch (Exception e) {
        view.displayDataNotInProperFormat();
        return model;
      }
      for (int i = 0; i < keys.size(); i++) {
        Map<String, List<List<String>>> insideContents = parseFlexiblePortfolio.get(keys.get(i));
        List<String> insideKeySet;
        try {
          insideKeySet = new ArrayList<>(insideContents.keySet());
          if (insideKeySet.size() == 0) {
            view.displayDataNotInProperFormat();
            return model;
          }
        } catch (Exception e) {
          view.displayDataNotInProperFormat();
          return model;
        }
        for (int k = 0; k < insideKeySet.size(); k++) {
          if (parseFlexiblePortfolio.get(keys.get(i)).get(insideKeySet.get(k)).size() != 3) {
            view.displayDataNotInProperFormat();
            return model;
          }
        }
      }
      model.setFlexible(parseFlexiblePortfolio);
      model.saveFlexiblePortfolios();
    } else {
      view.displaySwitchCaseDefault();
    }

    return model;
  }
}
