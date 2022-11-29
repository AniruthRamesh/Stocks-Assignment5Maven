package view;

import java.awt.*;

import javax.swing.*;

import controller.ControllerGUIImpl;

public class ViewGuiImpl extends JFrame implements ViewGui {

  JButton createFlexiblePortfolio = new JButton("Create Flexible Portfolio");
  JButton sellStocksFromAPortfolio = new JButton("Sell Stocks from a Portfolio");
  JButton determineCostBasis = new JButton("Determine Cost Basis");
  JButton portfolioOnCertainDateFlexible = new JButton("Determine value of Flexible " +
          "portfolio " + "on" + " certain Date");
  JButton portfolioPerformance = new JButton("Flexible Portfolio performance");
  JButton uploadPortfolio = new JButton("Upload Portfolio");
  JButton savePortfolio = new JButton("Save Portfolio");
  JButton dollarCostAvg = new JButton("Dollar Cost Averaging");
  JButton dollarCostAvgPerformance = new JButton("Dollar Cost Average Performance");
  JButton exit = new JButton("Exit");
  private JLabel display;
  private JButton echoButton, exitButton, toggleButton;
  private JTextField input;

  public ViewGuiImpl(String caption) {
    super(caption);
    setSize(1000, 1000);
    setLayout(new GridLayout(5, 2));
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(300, 300));


    this.add(createFlexiblePortfolio);
    this.add(sellStocksFromAPortfolio);
    this.add(determineCostBasis);
    this.add(portfolioOnCertainDateFlexible);
    this.add(portfolioPerformance);
    this.add(uploadPortfolio);
    this.add(savePortfolio);
    this.add(dollarCostAvg);
    this.add(dollarCostAvgPerformance);
    this.add(exit);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(ControllerGUIImpl features) {
    exit.addActionListener(evt -> features.exit());
  }
}
