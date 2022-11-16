package Command;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It's a command that handles the cost basis of a stock.
 */
public class HandleCostBasis implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleCostBasis(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.displayEnterNameForPortfolio();
    sc.nextLine();
    String portfolioName = sc.nextLine();
    if(!model.flexiblePortContainsCertainKey(portfolioName)){
      view.displayNoSuchPortfolio();
      return model;
    }
    DateHelper dateValue = new DateHelper(view,model,sc);
    String date = dateValue.helper();
    if(date.length()==0){
      view.displayDateIsNotValid();
      return model;
    }
    List<String> companies = model.getCompaniesInCertainPortfolio(portfolioName);
    for(int i=0;i<companies.size();i++){
      System.out.println(companies.get(i));
    }

    return model;
  }
}
