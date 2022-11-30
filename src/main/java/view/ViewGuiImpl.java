package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import controller.ControllerGUIImpl;

public class ViewGuiImpl extends JFrame implements ViewGui {

  private static final int WIDTH = 700;
  private static final int HEIGHT = 700;
  JLabel l1, l2, l3, l4, l5, l6, l7;
  JTextField nameOfPort, day, month, year, ticker, number;
  JButton btn1;
  //These are the Panels
  private JPanel mainPanel;
  private JPanel commandPanel;
  private JPanel buy;
  private JPanel sell;
  private JPanel costBasis;
  private JPanel totalValue;
  private JPanel upload;
  private JPanel save;
  private JPanel dollarCostAvg;
  private JPanel dollarCostAvgPerformance;

  public ViewGuiImpl(String caption) {
    super(caption);

    window();
    mainPanel = new JPanel();
    this.setVisible(true);
    mainPanel.setLayout(new CardLayout());

    mainPanel.add(commandWindow(), "Command Panel");
    mainPanel.add(buyWindow(), "Buy Panel");
//    mainPanel.add(sellWindow(), "Sell Panel");
//    mainPanel.add(costBasisWindow(), "Cost Basis Panel");
//    mainPanel.add(totalValueWindow(), "Total Value Panel");
//    mainPanel.add(uploadWindow(), "Upload File Panel");
//    mainPanel.add(saveWindow(), "Save Panel");
//    mainPanel.add(dollarCostAvgWindow(), "Dollar Cost Average Panel");
//    mainPanel.add(dollarCostAvgPerformanceWindow(), "Cost Avg Performance Panel");

    this.add(mainPanel);

    this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  public JPanel commandWindow() {
    JLabel welcomeMessage = new JLabel("<html><strong>This is a Portfolio Management " +
            "Application<br>" +
            ".</strong><br><br> Choose a command from the “Commands” menu to buy, sell, cost " +
            "basis of portfolio,<br> total value of stocks, upload portfolio, investing in " +
            "portfolio " +
            "using dollar cost averaging, check cost basis and value while creating strategy " +
            "for a portfolio" +
            "</html>");
    commandPanel = new JPanel();
    commandPanel.setLayout(new GridBagLayout());
    commandPanel.add(welcomeMessage);

    return commandPanel;
  }

  public void window() {

    setTitle("Investment Portfolio");
    setSize(WIDTH, HEIGHT);
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("Commands");
    JMenuItem buy = new JMenuItem("Buy");
    JMenuItem sell = new JMenuItem("Sell");
    JMenuItem cost_basis = new JMenuItem("Cost Basis");
    JMenuItem total_value = new JMenuItem("Total Value");
    JMenuItem upload_file = new JMenuItem("Upload File");
    JMenuItem save1 = new JMenuItem("Save");
    JMenuItem dollar_cost_average = new JMenuItem("Dollar Cost Average");
    JMenuItem dollar_cost_avg_performance = new JMenuItem("Cost Avg Performance");
    JMenuItem quit = new JMenuItem("Quit");
    menu.add(buy);
    menu.add(sell);
    menu.add(total_value);
    menu.add(cost_basis);
    menu.add(upload_file);
    menu.add(save1);
    menu.add(dollar_cost_average);
    menu.add(dollar_cost_avg_performance);
    menu.add(quit);
    menuBar.add(menu);
    setJMenuBar(menuBar);
    buy.addActionListener(new buyPanelShow());
  }

  public JPanel buyWindow() {
    buy = new JPanel();
    buy.setPreferredSize(new Dimension(500, 500));
    buy.setVisible(true);
    buy.setLayout(null);
    l1 = new JLabel("Create a flexible portfolio");
    l1.setForeground(Color.blue);
    l1.setFont(new Font("Serif", Font.BOLD, 20));
    l2 = new JLabel("Name of Portfolio:");
    l3 = new JLabel("Day:");
    l4 = new JLabel("Month:");
    l5 = new JLabel("Year:");
    l6 = new JLabel("Enter the ticker symbol:");
    l7 = new JLabel("Number of stocks:");
    nameOfPort = new JTextField();
    day = new JTextField();
    ticker = new JTextField();
    number = new JTextField();
    month = new JTextField();
    year = new JTextField();
    btn1 = new JButton("Submit");
    l1.setBounds(100, 30, 400, 30);
    l2.setBounds(80, 70, 200, 30);
    l3.setBounds(80, 110, 200, 30);
    l4.setBounds(80, 150, 200, 30);
    l5.setBounds(80, 190, 200, 30);
    l6.setBounds(80, 230, 200, 30);
    l7.setBounds(80, 270, 200, 30);
    nameOfPort.setBounds(300, 70, 200, 30);
    day.setBounds(300, 110, 200, 30);
    ticker.setBounds(300, 150, 200, 30);
    number.setBounds(300, 190, 200, 30);
    month.setBounds(300, 230, 200, 30);
    year.setBounds(300, 270, 200, 30);
    btn1.setBounds(50, 350, 100, 30);
    buy.add(l1);
    buy.add(l2);
    buy.add(nameOfPort);
    buy.add(l3);
    buy.add(day);
    buy.add(l4);
    buy.add(month);
    buy.add(l5);
    buy.add(year);
    buy.add(l6);
    buy.add(ticker);
    buy.add(l7);
    buy.add(number);
    buy.add(btn1);
    return buy;
  }

  private JPanel sellWindow() {
    sell = new JPanel();
    return sell;
  }

  private JPanel dollarCostAvgPerformanceWindow() {
    dollarCostAvgPerformance = new JPanel();
    return dollarCostAvgPerformance;
  }

  private JPanel dollarCostAvgWindow() {
    dollarCostAvg = new JPanel();
    return dollarCostAvg;
  }

  private JPanel saveWindow() {
    save = new JPanel();
    return save;
  }

  private JPanel uploadWindow() {
    upload = new JPanel();
    return upload;
  }

  private JPanel totalValueWindow() {
    totalValue = new JPanel();
    return totalValue;
  }

  private JPanel costBasisWindow() {
    costBasis = new JPanel();
    return costBasis;
  }

  @Override
  public void addFeatures(ControllerGUIImpl features) {
    btn1.addActionListener(evt -> features.createNewFlexiblePortfolio(buy, nameOfPort.getText(),
            day.getText(), month.getText(), year.getText(), ticker.getText(), number.getText()));
  }

  public void createMessageBox(JPanel frame, String message) {
    JOptionPane.showMessageDialog(frame, message);
  }

  private class buyPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Buy")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Buy Panel");
      }
    }
  }
}
