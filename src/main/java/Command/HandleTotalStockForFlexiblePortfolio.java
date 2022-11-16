package Command;

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
    return null;
  }
}
