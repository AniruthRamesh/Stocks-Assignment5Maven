package view;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.*;

import controller.ControllerGUIImpl;

public class ViewGuiImpl extends JFrame implements ViewGui {

  private static final int WIDTH = 700;
  private static final int HEIGHT = 700;
  //These are the Panels
  private final JPanel mainPanel;
  String selected = "none";
  ButtonGroup bg;
  JLabel l1, l2, l3, l4, l5, l6, l7, upload_l1, upload_l2, upload_l3;
  JLabel costBasis_l1, costBasis_l2, costBasis_l3, costBasis_l4, costBasis_l5;
  JLabel totalValue_l1, totalValue_l2, totalValue_l3, totalValue_l4, totalValue_l5;
  JLabel sell_l1, sell_l2, sell_l3, sell_l4, sell_l5, sell_l6, sell_l7;

  JLabel dollar_l1, dollar_l2, dollar_l3, dollar_l4, dollar_l5, dollar_l6, datePopup_l1,
          datePopup_l2, datePopup_l3, noDatePopup_l1, noDatePopup_l2, graph_l1, graph_l2,
          graph_l3, graph_l4, graph_l5, graph_l6, graph_l7, graph_l8;
  JLabel dollarPerf_l1, dollarPerf_l2, dollarPerf_l3, dollarPerf_l4, dollarPerf_l5, dollarPerf_l6;
  JTextField dollar_nameOfPort, dollar_day, dollar_month, dollar_year, dollar_ticker, dollar_number;
  JTextField dollarPerf_nameOfPort, dollarPerf_day, dollarPerf_month, dollarPerf_year, dollarPerf_ticker, dollarPerf_number;


  ButtonGroup G1;
  ButtonGroup dollarG1, durationGroup;
  JRadioButton jRadioButton1, dayR, monthR, yearR;
  JRadioButton jRadioButton2;
  JRadioButton jRadioButtonDollar1, jRadioButtonDollar2;
  JTextField nameOfPort, day, month, year, ticker, number, filePath, datePopup_tf1, datePopup_tf2,
          datePopup_tf3, noDatePopup_tf1, noDatePopup_tf2, duration;
  JTextField costBasis_nameOfPort, costBasis_day, costBasis_month, costBasis_year;
  JTextField sell_nameOfPort, sell_day, sell_month, sell_year, sell_ticker, sell_number;
  JTextField totalValue_nameOfPort, totalValue_day, totalValue_month, totalValue_year;
  JTextField graph_name, graph_startDay, graph_startMonth, graph_startYear, graph_endDay,
          graph_endMonth, graph_endYear;
  JButton buyButton, sellButton, uploadButton, totalValueButton, btn4, btnPopup, graphButton;
  JButton dollarCostAvg_button;
  JButton dollarCostAvgPerformanceButton;
  JFrame costPanel;
  JRadioButton cost;
  JRadioButton totalValuePerformance;
  JPanel costPopup;

  private JTextArea textArea;
  private JTextArea costBasis_textArea;
  private JScrollPane scrollPane;
  private JScrollPane costBasis_scrollPane;
  private JPanel commandPanel;
  private JPanel buy;
  private JPanel datePopup, durationPopup;
  private JPanel noDatePopup;

  private JPanel sell, costBasis, totalValue, upload, dollarCostAvg, graph,
          dollarCostAvgPerformance;

  public ViewGuiImpl(String caption) {
    super(caption);

    window();
    mainPanel = new JPanel();
    this.setVisible(true);
    mainPanel.setLayout(new CardLayout());

    mainPanel.add(commandWindow(), "Options Panel");
    mainPanel.add(buyWindow(), "Buy Panel");
    mainPanel.add(sellWindow(), "Sell Panel");
    mainPanel.add(costBasisWindow(), "Cost Basis Panel");
    mainPanel.add(totalValueWindow(), "Total Value Panel");
    mainPanel.add(uploadWindow(), "Upload File Panel");
    mainPanel.add(createGraphWindow(), "Create Graph Panel");
    mainPanel.add(dollarCostAvgWindow(), "Dollar Cost Average Panel");
    mainPanel.add(dollarCostAvgPerformanceWindow(), "Cost Avg Performance Panel");

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
            "Application.<br>" + "</strong><br><br> Choose a command from the “Commands” menu to " +
            "buy, sell, cost " + "basis of portfolio,<br> total value of stocks, upload " +
            "portfolio, investing in " + "portfolio " + "using dollar cost averaging, check cost " +
            "basis and value while creating strategy " + "for a portfolio" + "</html>");
    commandPanel = new JPanel();
    Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\res\\" + "stock.jpeg");
    JLabel picLabel = new JLabel("");
    commandPanel.setLayout(new GridBagLayout());

    ImageIcon imageIcon1 =
            new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(100, 100,
                    Image.SCALE_DEFAULT));
    picLabel.setIcon(imageIcon1);
    commandPanel.add(picLabel);
    commandPanel.add(new JPanel());

    commandPanel.add(welcomeMessage);
    welcomeMessage.setPreferredSize(new Dimension(500, 500));

    return commandPanel;
  }

  public void window() {

    setTitle("Investment Portfolio");
    setSize(WIDTH, HEIGHT);
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("Options");
    JMenuItem buy = new JMenuItem("Buy");
    JMenuItem sell = new JMenuItem("Sell");
    JMenuItem cost_basis = new JMenuItem("Cost Basis");
    JMenuItem total_value = new JMenuItem("Total Value");
    JMenuItem upload_file = new JMenuItem("Upload File");
    JMenuItem dollar_cost_average = new JMenuItem("Dollar Cost Average");
    JMenuItem dollar_cost_avg_performance = new JMenuItem("Cost Avg Performance");
    JMenuItem create_graph = new JMenuItem("Create Graph");
    JMenuItem quit = new JMenuItem("Quit");
    menu.add(buy);
    menu.add(sell);
    menu.add(total_value);
    menu.add(cost_basis);
    menu.add(upload_file);
    menu.add(dollar_cost_average);
    menu.add(dollar_cost_avg_performance);
    menu.add(create_graph);
    menu.add(quit);
    menuBar.add(menu);
    setJMenuBar(menuBar);
    buy.addActionListener(new buyPanelShow());
    sell.addActionListener(new sellPanelShow());
    total_value.addActionListener(new totalValuePanelShow());
    upload_file.addActionListener(new uploadPanelShow());
    cost_basis.addActionListener(new costBasisPanelShow());
    create_graph.addActionListener(new createGraph());
    dollar_cost_average.addActionListener(new dollarCostPanelShow());
    dollar_cost_avg_performance.addActionListener(new dollarCostPerformancePanelShow());
//    quit.addActionListener(new buyPanelShow());
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
    buyButton = new JButton("Submit");
    l1.setBounds(100, 30, 400, 30);
    l2.setBounds(80, 70, 200, 30);
    l3.setBounds(80, 110, 200, 30);
    l4.setBounds(80, 150, 200, 30);
    l5.setBounds(80, 190, 200, 30);
    l6.setBounds(80, 230, 200, 30);
    l7.setBounds(80, 270, 200, 30);
    nameOfPort.setBounds(300, 70, 200, 30);
    day.setBounds(300, 110, 200, 30);
    month.setBounds(300, 150, 200, 30);
    year.setBounds(300, 190, 200, 30);
    ticker.setBounds(300, 230, 200, 30);
    number.setBounds(300, 270, 200, 30);
    buyButton.setBounds(50, 350, 100, 30);
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
    buy.add(buyButton);
    return buy;
  }

  private JPanel sellWindow() {
    sell = new JPanel();
    sell.setPreferredSize(new Dimension(500, 500));
    sell.setVisible(true);
    sell.setLayout(null);
    sell_l1 = new JLabel("Sell Stocks of a flexible portfolio");
    sell_l1.setForeground(Color.blue);
    sell_l1.setFont(new Font("Serif", Font.BOLD, 20));
    sell_l2 = new JLabel("Name of Portfolio:");
    sell_l3 = new JLabel("Day:");
    sell_l4 = new JLabel("Month:");
    sell_l5 = new JLabel("Year:");
    sell_l6 = new JLabel("Enter the ticker symbol:");
    sell_l7 = new JLabel("Number of stocks:");
    sell_nameOfPort = new JTextField();
    sell_day = new JTextField();
    sell_ticker = new JTextField();
    sell_number = new JTextField();
    sell_month = new JTextField();
    sell_year = new JTextField();
    sellButton = new JButton("Submit");
    sell_l1.setBounds(100, 30, 400, 30);
    sell_l2.setBounds(80, 70, 200, 30);
    sell_l3.setBounds(80, 110, 200, 30);
    sell_l4.setBounds(80, 150, 200, 30);
    sell_l5.setBounds(80, 190, 200, 30);
    sell_l6.setBounds(80, 230, 200, 30);
    sell_l7.setBounds(80, 270, 200, 30);
    sell_nameOfPort.setBounds(300, 70, 200, 30);
    sell_day.setBounds(300, 110, 200, 30);
    sell_month.setBounds(300, 150, 200, 30);
    sell_year.setBounds(300, 190, 200, 30);
    sell_ticker.setBounds(300, 230, 200, 30);
    sell_number.setBounds(300, 270, 200, 30);
    sellButton.setBounds(50, 350, 100, 30);
    sell.add(sell_l1);
    sell.add(sell_l2);
    sell.add(sell_nameOfPort);
    sell.add(sell_l3);
    sell.add(sell_day);
    sell.add(sell_l4);
    sell.add(sell_month);
    sell.add(sell_l5);
    sell.add(sell_year);
    sell.add(sell_l6);
    sell.add(sell_ticker);
    sell.add(sell_l7);
    sell.add(sell_number);
    sell.add(sellButton);
    return sell;
  }

  private JPanel dollarCostAvgPerformanceWindow() {
    dollarCostAvgPerformance = new JPanel();
    dollarCostAvgPerformance.setPreferredSize(new Dimension(500, 500));
    dollarCostAvgPerformance.setVisible(true);
    dollarCostAvgPerformance.setLayout(null);
    dollarPerf_l1 = new JLabel("Check Cost basis and Total Value for strategy");
    dollarPerf_l1.setForeground(Color.blue);
    dollarPerf_l1.setFont(new Font("Serif", Font.BOLD, 20));
    dollarPerf_l2 = new JLabel("Name of the Portfolio:");
    dollarPerf_l3 = new JLabel("Amount:");
    dollarPerf_l4 = new JLabel("Start date day:");
    dollarPerf_l5 = new JLabel("Start date month:");
    dollarPerf_l6 = new JLabel("Start date Year:");
    jRadioButtonDollar1 = new JRadioButton();
    jRadioButtonDollar2 = new JRadioButton();
    dollarPerf_nameOfPort = new JTextField();
    dollarPerf_number = new JTextField();
    dollarPerf_day = new JTextField();
    dollarPerf_month = new JTextField();
    dollarPerf_year = new JTextField();
    dollarG1 = new ButtonGroup();
    dollarCostAvgPerformanceButton = new JButton("Submit");
    dollarG1.add(jRadioButtonDollar1);
    dollarG1.add(jRadioButtonDollar2);
    dollarPerf_l1.setBounds(100, 30, 400, 30);
    dollarPerf_l2.setBounds(80, 70, 200, 30);
    dollarPerf_l3.setBounds(80, 110, 200, 30);
    dollarPerf_l4.setBounds(80, 150, 200, 30);
    dollarPerf_l5.setBounds(80, 190, 200, 30);
    dollarPerf_l6.setBounds(80, 230, 200, 30);
    dollarPerf_nameOfPort.setBounds(300, 70, 200, 30);
    dollarPerf_number.setBounds(300, 110, 200, 30);
    dollarPerf_day.setBounds(300, 150, 200, 30);
    dollarPerf_month.setBounds(300, 190, 200, 30);
    dollarPerf_year.setBounds(300, 230, 200, 30);
    jRadioButtonDollar1.setBounds(300, 270, 100, 30);
    jRadioButtonDollar2.setBounds(400, 270, 200, 30);
    dollarCostAvgPerformanceButton.setBounds(410, 320, 100, 30);
    jRadioButtonDollar1.setText("End Date");
    jRadioButtonDollar2.setText("No End Date");
    dollarCostAvgPerformance.add(dollarPerf_l1);
    dollarCostAvgPerformance.add(dollarPerf_l2);
    dollarCostAvgPerformance.add(dollarPerf_nameOfPort);
    dollarCostAvgPerformance.add(dollarPerf_l3);
    dollarCostAvgPerformance.add(dollarPerf_number);
    dollarCostAvgPerformance.add(dollarPerf_l4);
    dollarCostAvgPerformance.add(dollarPerf_day);
    dollarCostAvgPerformance.add(dollarPerf_l5);
    dollarCostAvgPerformance.add(dollarPerf_month);
    dollarCostAvgPerformance.add(dollarPerf_l6);
    dollarCostAvgPerformance.add(dollarPerf_year);
    dollarCostAvgPerformance.add(jRadioButtonDollar1);
    dollarCostAvgPerformance.add(jRadioButtonDollar2);
    dollarCostAvgPerformance.add(dollarCostAvgPerformanceButton);
    return dollarCostAvgPerformance;


  }

  private JPanel dollarCostAvgWindow() {
    dollarCostAvg = new JPanel();
    dollarCostAvg.setPreferredSize(new Dimension(500, 500));
    dollarCostAvg.setVisible(true);
    dollarCostAvg.setLayout(null);
    dollar_l1 = new JLabel("Dollar Cost Average Strategy Creation");
    dollar_l1.setForeground(Color.blue);
    dollar_l1.setFont(new Font("Serif", Font.BOLD, 20));
    dollar_l2 = new JLabel("Name of the Portfolio:");
    dollar_l3 = new JLabel("Amount:");
    dollar_l4 = new JLabel("Start date day:");
    dollar_l5 = new JLabel("Start date month:");
    dollar_l6 = new JLabel("Start date Year:");
    jRadioButtonDollar1 = new JRadioButton();
    jRadioButtonDollar2 = new JRadioButton();
    dollar_nameOfPort = new JTextField();
    dollar_number = new JTextField();
    dollar_day = new JTextField();
    dollar_month = new JTextField();
    dollar_year = new JTextField();
    dollarG1 = new ButtonGroup();
    dollarCostAvg_button = new JButton("Submit");
    dollarG1.add(jRadioButtonDollar1);
    dollarG1.add(jRadioButtonDollar2);
    dollar_l1.setBounds(100, 30, 400, 30);
    dollar_l2.setBounds(80, 70, 200, 30);
    dollar_l3.setBounds(80, 110, 200, 30);
    dollar_l4.setBounds(80, 150, 200, 30);
    dollar_l5.setBounds(80, 190, 200, 30);
    dollar_l6.setBounds(80, 230, 200, 30);
    dollar_nameOfPort.setBounds(300, 70, 200, 30);
    dollar_number.setBounds(300, 110, 200, 30);
    dollar_day.setBounds(300, 150, 200, 30);
    dollar_month.setBounds(300, 190, 200, 30);
    dollar_year.setBounds(300, 230, 200, 30);
    jRadioButtonDollar1.setBounds(300, 270, 100, 30);
    jRadioButtonDollar2.setBounds(400, 270, 200, 30);
    dollarCostAvg_button.setBounds(410, 320, 100, 30);
    jRadioButtonDollar1.setText("End Date");
    jRadioButtonDollar2.setText("No End Date");
    dollarCostAvg.add(dollar_l1);
    dollarCostAvg.add(dollar_l2);
    dollarCostAvg.add(dollar_nameOfPort);
    dollarCostAvg.add(dollar_l3);
    dollarCostAvg.add(dollar_number);
    dollarCostAvg.add(dollar_l4);
    dollarCostAvg.add(dollar_day);
    dollarCostAvg.add(dollar_l5);
    dollarCostAvg.add(dollar_month);
    dollarCostAvg.add(dollar_l6);
    dollarCostAvg.add(dollar_year);
    dollarCostAvg.add(jRadioButtonDollar1);
    dollarCostAvg.add(jRadioButtonDollar2);
    dollarCostAvg.add(dollarCostAvg_button);
    return dollarCostAvg;
  }

  private JPanel createGraphWindow() {
    graph = new JPanel();
    graph.setPreferredSize(new Dimension(500, 500));
    graph.setVisible(true);
    graph.setLayout(null);
    graph_l1 = new JLabel("Show Performance");
    graph_l1.setForeground(Color.blue);
    graph_l1.setFont(new Font("Serif", Font.BOLD, 20));
    graph_l2 = new JLabel("Name of the Portfolio:");
    graph_l3 = new JLabel("Start date day:");
    graph_l4 = new JLabel("Start date month:");
    graph_l5 = new JLabel("Start date year:");
    graph_l6 = new JLabel("End date day:");
    graph_l7 = new JLabel("End date month:");
    graph_l8 = new JLabel("End date year:");
    graph_name = new JTextField();
    graph_startDay = new JTextField();
    graph_startMonth = new JTextField();
    graph_startYear = new JTextField();
    graph_endDay = new JTextField();
    graph_endMonth = new JTextField();
    graph_endYear = new JTextField();
    graphButton = new JButton("Submit");
    graph_l1.setBounds(100, 30, 400, 30);
    graph_l2.setBounds(80, 70, 200, 30);
    graph_l3.setBounds(80, 110, 200, 30);
    graph_l4.setBounds(80, 150, 200, 30);
    graph_l5.setBounds(80, 190, 200, 30);
    graph_l6.setBounds(80, 230, 200, 30);
    graph_l7.setBounds(80, 270, 200, 30);
    graph_l8.setBounds(80, 300, 200, 30);
    graph_name.setBounds(300, 70, 200, 30);
    graph_startDay.setBounds(300, 110, 200, 30);
    graph_startMonth.setBounds(300, 150, 200, 30);
    graph_startYear.setBounds(300, 190, 200, 30);
    graph_endDay.setBounds(300, 230, 200, 30);
    graph_endMonth.setBounds(300, 270, 200, 30);
    graph_endYear.setBounds(300, 310, 200, 30);
    graphButton.setBounds(50, 370, 100, 30);
    graph.add(graph_l1);
    graph.add(graph_l2);
    graph.add(graph_name);
    graph.add(graph_l3);
    graph.add(graph_startDay);
    graph.add(graph_l4);
    graph.add(graph_startMonth);
    graph.add(graph_l5);
    graph.add(graph_startYear);
    graph.add(graph_l6);
    graph.add(graph_endDay);
    graph.add(graph_l7);
    graph.add(graph_endMonth);
    graph.add(graph_l8);
    graph.add(graph_endYear);
    graph.add(graphButton);

    return graph;
  }

  private JPanel uploadWindow() {
    upload = new JPanel();
    upload.setPreferredSize(new Dimension(500, 500));
    upload.setVisible(true);
    upload.setLayout(null);
    jRadioButton1 = new JRadioButton();
    jRadioButton2 = new JRadioButton();
    upload_l1 = new JLabel("Upload portfolio");
    upload_l1.setForeground(Color.blue);
    upload_l1.setFont(new Font("Serif", Font.BOLD, 20));
    upload_l2 = new JLabel("Enter path of Portfolio:");
    upload_l3 = new JLabel("Select Type of Portfolio");
    filePath = new JTextField();
    G1 = new ButtonGroup();
    G1.add(jRadioButton1);
    G1.add(jRadioButton2);
    uploadButton = new JButton("Submit");
    upload_l1.setBounds(100, 30, 400, 30);
    upload_l2.setBounds(80, 70, 200, 30);
    upload_l3.setBounds(80, 110, 200, 30);
    jRadioButton1.setBounds(300, 110, 100, 30);
    jRadioButton2.setBounds(400, 110, 200, 30);
    filePath.setBounds(300, 70, 200, 30);
    uploadButton.setBounds(80, 150, 100, 30);
    jRadioButton1.setText("Inflexible");
    jRadioButton2.setText("Flexible");
    upload.add(upload_l1);
    upload.add(upload_l2);
    upload.add(upload_l3);

    upload.add(filePath);
    upload.add(jRadioButton1);
    upload.add(jRadioButton2);
    upload.add(uploadButton);
    return upload;
  }

  private JPanel totalValueWindow() {
    totalValue = new JPanel();
    totalValue.setPreferredSize(new Dimension(500, 500));
    totalValue.setVisible(true);
    totalValue.setLayout(null);
    totalValue_l1 = new JLabel("Get Total Value of Portfolio");
    totalValue_l1.setForeground(Color.blue);
    totalValue_l1.setFont(new Font("Serif", Font.BOLD, 20));
    totalValue_l2 = new JLabel("Name of Portfolio:");
    totalValue_l3 = new JLabel("Day:");
    totalValue_l4 = new JLabel("Month:");
    totalValue_l5 = new JLabel("Year:");
    totalValue_nameOfPort = new JTextField();
    totalValue_day = new JTextField();

    totalValue_month = new JTextField();
    totalValue_year = new JTextField();
    totalValueButton = new JButton("Submit");
    textArea = new JTextArea("");
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    scrollPane = new JScrollPane(textArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    totalValue.add(scrollPane);
    scrollPane.setBounds(50, 300, 500, 300);
    totalValue_l1.setBounds(100, 30, 400, 30);
    totalValue_l2.setBounds(80, 70, 200, 30);
    totalValue_l3.setBounds(80, 110, 200, 30);
    totalValue_l4.setBounds(80, 150, 200, 30);
    totalValue_l5.setBounds(80, 190, 200, 30);
    totalValue_nameOfPort.setBounds(300, 70, 200, 30);
    totalValue_day.setBounds(300, 110, 200, 30);
    totalValue_month.setBounds(300, 150, 200, 30);
    totalValue_year.setBounds(300, 190, 200, 30);
    totalValueButton.setBounds(50, 230, 100, 30);
    totalValue.add(totalValue_l1);
    totalValue.add(totalValue_l2);
    totalValue.add(totalValue_nameOfPort);
    totalValue.add(totalValue_l3);
    totalValue.add(totalValue_day);
    totalValue.add(totalValue_l4);
    totalValue.add(totalValue_month);
    totalValue.add(totalValue_l5);
    totalValue.add(totalValue_year);
    totalValue.add(totalValueButton);
    return totalValue;
  }

  private JPanel costBasisWindow() {
    costBasis = new JPanel();
    costBasis.setPreferredSize(new Dimension(500, 500));
    costBasis.setVisible(true);
    costBasis.setLayout(null);
    costBasis_l1 = new JLabel("View the Cost Basis");
    costBasis_l1.setForeground(Color.darkGray);
    costBasis_l1.setFont(new Font("Serif", Font.BOLD, 20));
    costBasis_l2 = new JLabel("Name of Portfolio:");
    costBasis_l3 = new JLabel("Day:");
    costBasis_l4 = new JLabel("Month:");
    costBasis_l5 = new JLabel("Year:");
    costBasis_nameOfPort = new JTextField();
    costBasis_day = new JTextField();
    costBasis_month = new JTextField();
    costBasis_year = new JTextField();
    btn4 = new JButton("Submit");

    costBasis_textArea = new JTextArea("");
    costBasis_textArea.setEditable(false);
    costBasis_textArea.setLineWrap(true);
    costBasis_scrollPane = new JScrollPane(costBasis_textArea);
    costBasis_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    costBasis.add(costBasis_scrollPane);

    costBasis_l1.setBounds(100, 30, 400, 30);
    costBasis_l2.setBounds(80, 70, 200, 30);
    costBasis_l3.setBounds(80, 110, 200, 30);
    costBasis_l4.setBounds(80, 150, 200, 30);
    costBasis_l5.setBounds(80, 190, 200, 30);
    costBasis_nameOfPort.setBounds(300, 70, 200, 30);
    costBasis_day.setBounds(300, 110, 200, 30);
    costBasis_month.setBounds(300, 150, 200, 30);
    costBasis_year.setBounds(300, 190, 200, 30);
    btn4.setBounds(50, 250, 100, 30);
    costBasis_scrollPane.setBounds(50, 350, 500, 200);

    costBasis.add(costBasis_l1);
    costBasis.add(costBasis_l2);
    costBasis.add(costBasis_nameOfPort);
    costBasis.add(costBasis_l3);
    costBasis.add(costBasis_day);
    costBasis.add(costBasis_l4);
    costBasis.add(costBasis_month);
    costBasis.add(costBasis_l5);
    costBasis.add(costBasis_year);
    costBasis.add(btn4);
    return costBasis;
  }

  @Override
  public void addFeatures(ControllerGUIImpl features) {
    buyButton.addActionListener(evt -> features.createNewFlexiblePortfolio(buy,
            nameOfPort.getText(), day.getText(), month.getText(), year.getText(),
            ticker.getText(), number.getText()));
    jRadioButtonDollar1.addActionListener(evt -> enterDatePopUp(features, dollarCostAvg));
    jRadioButtonDollar2.addActionListener(evt -> noDatePopUp(features, dollarCostAvg));
    sellButton.addActionListener(evt -> features.sellPortfolio(sell, sell_nameOfPort.getText(),
            sell_day.getText(), sell_month.getText(), sell_year.getText(), sell_ticker.getText(),
            sell_number.getText()));
    totalValueButton.addActionListener(evt -> features.totalValue(totalValue,
            totalValue_nameOfPort.getText(), totalValue_day.getText(), totalValue_month.getText()
            , totalValue_year.getText()));
    graphButton.addActionListener(evt -> features.createGraph(graph, graph_name.getText(),
            graph_startDay.getText(), graph_startMonth.getText(), graph_startYear.getText(),
            graph_endDay.getText(), graph_endMonth.getText(), graph_endYear.getText()));
//    graphButton.addActionListener(evt -> createGraphDemo());
    uploadButton.addActionListener(evt -> {
              int selected = 0;
              if (jRadioButton1.isSelected()) {
                selected = 1;
              } else if (jRadioButton2.isSelected()) {
                selected = 2;
              } else {
                createMessageBox(upload, "Please select an option");
              }
              features.uploadPortfolio(upload,
                      filePath.getText(), selected);
            }
    );
    btn4.addActionListener(evt -> features.costBasis(costBasis, costBasis_nameOfPort.getText(),
            costBasis_day.getText(), costBasis_month.getText(), costBasis_year.getText()));
    dollarCostAvg_button.addActionListener(evt -> features.dollarCostAveraging(dollarCostAvg,
            dollar_nameOfPort.getText(), dollar_number.getText(), dollar_day.getText(),
            dollar_month.getText(), dollar_year.getText()));
  }

  @Override
  public void createLineGraph(DefaultCategoryDataset dataset) {
    SwingUtilities.invokeLater(() -> {
      LineChart lineGraphFrame = new LineChart("Stock Performance Line Graph", dataset);
      lineGraphFrame.setAlwaysOnTop(true);
      lineGraphFrame.pack();
      lineGraphFrame.setSize(800, 800);
      lineGraphFrame.setVisible(true);
    });


  }

  public void createMessageBox(JPanel frame, String message) {
    JOptionPane.showMessageDialog(frame, message);
  }


  public void displayDynamicDataTotalValue(JPanel frame, String companyName, String value) {
    String message = companyName + "--> $" + value;
    textArea.append(message);
    textArea.append("\n" + "\n");
  }

  public void displayTotalValue(JPanel frame, String portfolioName, String date, String value) {
    textArea.append("\n" + "Portfolio: " + portfolioName + "\n" + "Date: " + date + "\n" + "Total" +
            " " +
            "Value: $ " + value);
  }

  public void displayCostBasis(String data) {
    costBasis_textArea.append("\n" + data + "\n");
  }

  private void durationPopUp(ControllerGUIImpl features, JPanel frame) {
    durationPopup = new JPanel();
    durationPopup.setLayout(new GridLayout(4, 2));
    durationPopup.setPreferredSize(new Dimension(200, 100));
    durationPopup.setVisible(true);
    dayR = new JRadioButton();
    monthR = new JRadioButton();
    yearR = new JRadioButton();
    durationGroup = new ButtonGroup();
    JLabel durationLabel = new JLabel("Enter the gap");
    duration = new JTextField();
    durationGroup.add(dayR);
    durationGroup.add(monthR);
    durationGroup.add(yearR);
    dayR.setBounds(100, 100, 100, 100);
    monthR.setBounds(100, 130, 100, 100);
    yearR.setBounds(100, 160, 100, 100);
    durationLabel.setBounds(150, 100, 100, 30);
    duration.setBounds(150, 130, 100, 100);
    dayR.setText("Day");
    monthR.setText("Month");
    yearR.setText("Year");
    durationPopup.add(dayR);
    durationPopup.add(monthR);
    durationPopup.add(yearR);
    //durationPopup.add(durationLabel);
    durationPopup.add(duration);
    int result = JOptionPane.showConfirmDialog(dollarCostAvg, durationPopup,
            null, JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      if (dayR.isSelected()) {
        selected = "day";
      } else if (monthR.isSelected()) {
        selected = "month";
      } else if (yearR.isSelected()) {
        selected = "year";
      }
      boolean close = features.durationCheck(frame, selected, duration.getText());
      if (!close) {
        durationPopUp(features, frame);
      }
    }
    if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
      boolean closer = features.mydChecker();
      if (!closer) {
        createMessageBox(frame, "Cannot close, please enter the details");
        durationPopUp(features, frame);
      }

    }

  }

  private void noDatePopUp(ControllerGUIImpl features, JPanel frame) {
    noDatePopup = new JPanel();
    noDatePopup.setLayout(new GridLayout(4, 2));
    noDatePopup.setPreferredSize(new Dimension(200, 100));
    noDatePopup.setVisible(true);
    noDatePopup_l1 = new JLabel("Ticker");
    noDatePopup_l2 = new JLabel("Percentage");
    noDatePopup_tf1 = new JTextField();
    noDatePopup_tf2 = new JTextField();
    noDatePopup_tf1.setPreferredSize(new Dimension(50, 30));
    noDatePopup_tf2.setPreferredSize(new Dimension(50, 30));

    noDatePopup.add(noDatePopup_l1);
    noDatePopup.add(noDatePopup_tf1);
    noDatePopup.add(noDatePopup_l2);
    noDatePopup.add(noDatePopup_tf2);
    int result = JOptionPane.showConfirmDialog(dollarCostAvg, noDatePopup,
            null, JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      boolean close = features.noDate(frame, noDatePopup_tf1.getText(), noDatePopup_tf2.getText());
      if (close == false) {
        return;
      }
      if (!features.getPercentage()) {
        noDatePopUp(features, frame);
      } else {
        createMessageBox(frame, "Select Month or year or Day and enter duration gap in which" +
                "you want to invest");
        durationPopUp(features, frame);
      }
//      System.out.println("x value: " + xField.getText());
//      System.out.println("y value: " + yField.getText());
    } else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
      boolean yes = features.getPercentage();
      if (!yes) {
        createMessageBox(frame, "Cannot exit, percentage does not add upto 100");
        noDatePopUp(features, frame);
      }

    }
  }

  public void enterDatePopUp(ControllerGUIImpl features, JPanel frame) {
    datePopup = new JPanel();
    datePopup.setLayout(new GridLayout(4, 2));
    datePopup.setPreferredSize(new Dimension(200, 100));
    datePopup.setVisible(true);
    datePopup_l1 = new JLabel("End Day:");
    datePopup_l2 = new JLabel("End Month:");
    datePopup_l3 = new JLabel("End Year:");
    datePopup_tf1 = new JTextField();
    datePopup_tf2 = new JTextField();
    datePopup_tf3 = new JTextField();

    datePopup_tf1.setPreferredSize(new Dimension(50, 30));
    datePopup_tf2.setPreferredSize(new Dimension(50, 30));
    datePopup_tf3.setPreferredSize(new Dimension(50, 30));

    btnPopup = new JButton("Submit");

    datePopup.add(datePopup_l1);
    datePopup.add(datePopup_tf1);
    datePopup.add(datePopup_l2);
    datePopup.add(datePopup_tf2);
    datePopup.add(datePopup_l3);
    datePopup.add(datePopup_tf3);
    int result = JOptionPane.showConfirmDialog(dollarCostAvg, datePopup,
            null, JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      boolean yes = features.enteredDate(frame, datePopup_tf1.getText(), datePopup_tf2.getText(),
              datePopup_tf3.getText());
      if (yes) {
        noDatePopUp(features, frame);
      }
//      System.out.println("x value: " + xField.getText());
//      System.out.println("y value: " + yField.getText());
    }
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

  private class totalValuePanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Total Value")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Total Value Panel");
      }
    }

  }

  private class sellPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Sell")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Sell Panel");
      }
    }
  }

  private class costBasisPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Cost Basis")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Cost Basis Panel");
      }
    }
  }

  private class createGraph implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Create Graph")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Create Graph Panel");
      }
    }
  }


  private class uploadPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Upload File")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Upload File Panel");
      }
    }
  }

  private class dollarCostPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Dollar Cost Average")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Dollar Cost Average Panel");
      }
    }
  }

  private class dollarCostPerformancePanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Cost Avg Performance")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Cost Avg Performance Panel");
      }
    }
  }
}
