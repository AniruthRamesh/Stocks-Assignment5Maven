package controller;
import command.Command;
import model.Model;
import view.ViewGui;

public class ControllerGUIImpl implements Features{
  ViewGui view;
  Model model;
  Command command;
  public ControllerGUIImpl(Model model, ViewGui viewGui) {
    this.model = model;
    this.view = viewGui;
  }

  @Override
  public void createNewFlexiblePortfolio() {

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
}
