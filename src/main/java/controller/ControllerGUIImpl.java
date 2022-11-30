package controller;

import javax.swing.*;

import command.Command;
import model.Model;
import view.ViewGui;

public class ControllerGUIImpl implements Features {
  ViewGui view;
  Model model;
  Command command;

  public ControllerGUIImpl(Model model, ViewGui viewGui) {
    this.model = model;
    this.view = viewGui;
    this.view.addFeatures(this);

  }

  @Override
  public void createNewFlexiblePortfolio(JPanel frame, String name, String dayText,
                                         String monthText,
                                         String yearText, String tickerText, String numberText) {
//    String nameText = name;
//    if (nameText.length() == 0) {
//      view.createMessageBox(frame, "Portfolio name cannot be empty");
//    }
//    boolean alreadyContainsTheName = model.hasAnotherPortfolioWithSameName(name);
//    if (alreadyContainsTheName) {
//      view.createMessageBox(frame, "Already exists portfolio with this name");
//    }

  }

  @Override
  public void sellPortfolio() {

  }

  @Override
  public void costBasis() {

  }

  @Override
  public void totalValue() {

  }

  @Override
  public void uploadPortfolio() {

  }

  @Override
  public void savePortfolio() {

  }

  @Override
  public void dollarCostAveraging() {

  }

  @Override
  public void dollarCostAveragingAndQueryCostBasisAndValue() {

  }

  @Override
  public void exit() {
    System.exit(0);
  }

  @Override
  public void createGraph() {

  }

}
