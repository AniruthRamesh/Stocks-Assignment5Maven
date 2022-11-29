package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controller.ControllerGUIImpl;

/**
 * This class is the main class that creates the GUI that the user interacts with. It extends the JFrame interface.
 */

public class ViewGuiImpl extends JFrame implements ViewGui {

  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;

  private String file;

  private Font printFont = new Font("SansSerif", Font.BOLD, 20);

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

  //These are the buttons
  private JButton resetButton;
  private JButton buyButton;
  private JButton sellButton;
  private JButton prevButton;
  private JButton nextButton;
  private JButton saveButton;
  private JButton searchButton;

  //These are the objects for the Buy Panel
  private JTextField nameTextField_buyPanel = new JTextField();
  private JTextField symbolTextField_buyPanel = new JTextField();
  private JTextField quantityTextField_buyPanel = new JTextField();
  private JTextField priceTextField_buyPanel = new JTextField();
  private JTextArea printArea_buyPanel = new JTextArea();

  private JTextField symbolTextField_sellPanel = new JTextField();
  private JTextField quantityTextField_sellPanel = new JTextField();
  private JTextField priceTextField_sellPanel = new JTextField();
  private JTextArea printArea_sellPanel = new JTextArea();

  //These are the objects for Update Section
  private JTextField symbolTextField_updatePanel = new JTextField();
  private JTextField nameTextField_updatePanel = new JTextField();
  private JTextField priceTextField_updatePanel = new JTextField();
  private JTextArea printArea_updatePanel = new JTextArea();
  protected int location = 0;

  //These are the objects for Search Section
  private JTextField symbolTextField_searchSection = new JTextField();
  private JTextField nameKeyWordTextField_searchSection = new JTextField();
  private JTextField lowPriceTextField_SearchSection = new JTextField();
  private JTextField highPriceTextField_searchSection = new JTextField();
  private JTextArea printArea_searchPanel = new JTextArea();

  //These are the objects for TotalGains Section
  private JTextField totalGainsTextField_totalgainsSection = new JTextField();
  private JTextArea printArea_totalGainsPanel = new JTextArea();

  //OptionList for Buying
  private String[] typeOptions = new String[]{"Stock", "Mutual Fund"};
  private JComboBox<String> typeOptionList = new JComboBox<String>(typeOptions);

  public ViewGuiImpl(String caption) {
    super(caption);

    window();
    mainPanel = new JPanel();
    mainPanel.setLayout(new CardLayout());

    mainPanel.add(commandWindow(), "Command Panel");
    mainPanel.add(buyWindow(), "Buy Panel");
    mainPanel.add(sellWindow(), "Sell Panel");
    mainPanel.add(updateWindow(), "Update Panel");
    mainPanel.add(totalGainsWindow(), "Total Panel");
    mainPanel.add(searchWindow(), "Search Panel");

    //Adding the main Panel to the frame
    this.add(mainPanel);
    //Setting the frame to show...
    this.setVisible(true);
    //portfolioActions();
    this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  //************************************************************//
  //***This section is to create the Main Section panels********//
  //************************************************************//

  /**
   * This method creates the landing page JPanel that the user first sees.
   * @return command JPanel
   */
  public JPanel commandWindow() {

    //Create an Instance of commandPanel
    commandPanel = new JPanel();

    //Creating instance of the main panel for this method
    JPanel topPanel = new JPanel();

    JPanel text = new JPanel(); //This panel carries the Welcome message.

    //Split the main Panel into 2 panels now
    commandPanel.setLayout(new GridLayout(1, 1));

    //Now dealing with the Top panel and it's contents
    JLabel welcomeMessage = new JLabel("<html><strong>Welcome to Investment Portfolio.</strong><br><br> Choose a command from the “Commands” menu to buy or sell <br>an Investment, update prices for all investments, get gain for<br>the portfolio, search for relevant investments, or quit the<br>program </html>");
    topPanel.setLayout(new GridLayout(3, 4));
    welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
    text.add(welcomeMessage);
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());
    topPanel.add(text);
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());
    topPanel.add(new JPanel());

    //Adding the main panel of this method to the mainPanel of the Frame
    commandPanel.add(topPanel);

    return commandPanel;
  }

  /**
   * This method creates the buy JPanel where the user can enter values to buy Funds
   * @return buy JPanel
   */
  public JPanel buyWindow() {
    buy = new JPanel(new GridLayout(2, 1));
    buy.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    typeOptionList.setFont(printFont);

    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JPanel topLeft = new JPanel(new GridLayout(8, 1));

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel panelName = new JLabel("   Buying an Investment");
    panelName.setHorizontalAlignment(SwingConstants.LEFT);
    panelName.setFont(printFont);

    JLabel type = new JLabel("Type");
    type.setFont(printFont);

    JPanel specialTypePanel = new JPanel(new GridLayout(1, 2));
    specialTypePanel.add(type);
    specialTypePanel.add(typeOptionList);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Name Panel creation
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new GridLayout(0, 2));

    JLabel name = new JLabel("Name");
    name.setFont(printFont);

    nameTextField_buyPanel.setFont(printFont);

    namePanel.add(name);
    namePanel.add(nameTextField_buyPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Symbol Panel creation
    JPanel symbolPanel = new JPanel();
    symbolPanel.setLayout(new GridLayout(0, 2));

    JLabel symbolName = new JLabel("Symbol");
    symbolName.setFont(printFont);

    symbolTextField_buyPanel.setFont(printFont);

    symbolPanel.add(symbolName);
    symbolPanel.add(symbolTextField_buyPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Quantity Panel creation
    JPanel quantityPanel = new JPanel();
    quantityPanel.setLayout(new GridLayout(0, 2));

    JLabel quantityName = new JLabel("Quantity");
    quantityName.setFont(printFont);

    quantityTextField_buyPanel.setFont(printFont);

    quantityPanel.add(quantityName);
    quantityPanel.add(quantityTextField_buyPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Price Panel creation
    JPanel pricePanel = new JPanel();
    pricePanel.setLayout(new GridLayout(0, 2));

    JLabel priceName = new JLabel("Price");
    priceName.setFont(printFont);

    priceTextField_buyPanel.setFont(printFont);

    pricePanel.add(priceName);
    pricePanel.add(priceTextField_buyPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    topLeft.add(panelName);
    topLeft.add(fillerPanel());
    topLeft.add(specialTypePanel);
    topLeft.add(symbolPanel);
    topLeft.add(namePanel);
    topLeft.add(quantityPanel);
    topLeft.add(pricePanel);

    topPanel.add(topLeft);

    topPanel.add(resetBuyPanel());

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Text Area

    JPanel printAreaPanel = new JPanel(new BorderLayout());

    printArea_buyPanel = new JTextArea();
    printArea_buyPanel.setFont(printFont);
    printArea_buyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    printArea_buyPanel.setEditable(false);
    JScrollPane scroll = new JScrollPane(printArea_buyPanel);

    printAreaPanel.add(scroll);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    JLabel messageAreaLabel = new JLabel("Message");
    messageAreaLabel.setFont(printFont);

    bottomPanel.add(messageAreaLabel, BorderLayout.PAGE_START);
    bottomPanel.add(printAreaPanel, BorderLayout.CENTER);

    buy.add(topPanel);
    buy.add(bottomPanel);

    return buy;
  }

  /**
   * This method creates the sell JPanel where the user can enter values to sell Funds
   * @return sell JPanel
   */
  public JPanel sellWindow() {
    sell = new JPanel(new GridLayout(2, 1));
    sell.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    Font printFont = new Font("SansSerif", Font.BOLD, 24);

    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JPanel topLeft = new JPanel(new GridLayout(8, 1));
    //TopRight is produced in a method
    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel panelName = new JLabel("   Selling an Investment");
    panelName.setHorizontalAlignment(SwingConstants.LEFT);
    panelName.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Symbol Panel creation
    JPanel symbolPanel = new JPanel();
    symbolPanel.setLayout(new GridLayout(0, 2));

    JLabel symbolName = new JLabel("Symbol");
    symbolName.setFont(printFont);

    symbolTextField_sellPanel.setFont(printFont);

    symbolPanel.add(symbolName);
    symbolPanel.add(symbolTextField_sellPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Quantity Panel creation
    JPanel quantityPanel = new JPanel();
    quantityPanel.setLayout(new GridLayout(0, 2));

    JLabel quantityName = new JLabel("Quantity");
    quantityName.setFont(printFont);

    quantityTextField_sellPanel.setFont(printFont);

    quantityPanel.add(quantityName);
    quantityPanel.add(quantityTextField_sellPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Price Panel creation
    JPanel pricePanel = new JPanel();
    pricePanel.setLayout(new GridLayout(0, 2));

    JLabel priceName = new JLabel("Price");
    priceName.setFont(printFont);

    priceTextField_sellPanel.setFont(printFont);

    pricePanel.add(priceName);
    pricePanel.add(priceTextField_sellPanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END


    topLeft.add(panelName);
    topLeft.add(fillerPanel());

    topLeft.add(symbolPanel);
    topLeft.add(fillerPanel());

    topLeft.add(quantityPanel);
    topLeft.add(fillerPanel());

    topLeft.add(pricePanel);
    topLeft.add(fillerPanel());

    topPanel.add(topLeft);

    topPanel.add(resetSellPanel());

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Text Area

    JPanel printAreaPanel = new JPanel(new BorderLayout());

    printArea_sellPanel = new JTextArea();
    printArea_sellPanel.setFont(printFont);
    printArea_sellPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    printArea_sellPanel.setEditable(false);
    JScrollPane scroll = new JScrollPane(printArea_sellPanel);

    printAreaPanel.add(scroll);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    JLabel messageAreaLabel = new JLabel("Message");
    messageAreaLabel.setFont(printFont);

    bottomPanel.add(messageAreaLabel, BorderLayout.PAGE_START);
    bottomPanel.add(printAreaPanel, BorderLayout.CENTER);

    sell.add(topPanel);
    sell.add(bottomPanel);

    return sell;
  }

  /**
   * This method creates the update JPanel where the user can enter values to update Funds previously bought
   * @return update JPanel
   */
  public JPanel updateWindow() {

    costBasis = new JPanel(new GridLayout(2, 1));
    costBasis.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    Font printFont = new Font("SansSerif", Font.BOLD, 24);

    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JPanel topLeft = new JPanel(new GridLayout(8, 1));
    JPanel topRight = new JPanel(new GridLayout(7, 3));

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel panelName = new JLabel("   Updating Investment");
    panelName.setHorizontalAlignment(SwingConstants.LEFT);
    panelName.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Name Panel creation
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new GridLayout(0, 2));

    JLabel name = new JLabel("Name");
    name.setFont(printFont);

    nameTextField_updatePanel.setFont(printFont);

    namePanel.add(name);
    namePanel.add(nameTextField_updatePanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Symbol Panel creation
    JPanel symbolPanel = new JPanel();
    symbolPanel.setLayout(new GridLayout(0, 2));

    JLabel symbolName = new JLabel("Symbol");
    symbolName.setFont(printFont);

    symbolTextField_updatePanel.setFont(printFont);

    symbolPanel.add(symbolName);
    symbolPanel.add(symbolTextField_updatePanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Price Panel creation
    JPanel pricePanel = new JPanel();
    pricePanel.setLayout(new GridLayout(0, 2));

    JLabel priceName = new JLabel("Price");
    priceName.setFont(printFont);

    priceTextField_updatePanel.setFont(printFont);

    pricePanel.add(priceName);
    pricePanel.add(priceTextField_updatePanel);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END


    topLeft.add(panelName);
    topLeft.add(fillerPanel());

    topLeft.add(symbolPanel);
    topLeft.add(fillerPanel());

    topLeft.add(namePanel);
    topLeft.add(fillerPanel());

    topLeft.add(pricePanel);
    topLeft.add(fillerPanel());

    topPanel.add(topLeft);


    for (int i = 0; i < 5; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    prevButton = new JButton("Prev");
    prevButton.addActionListener(new updateButton());
    topRight.add(prevButton);

    for (int i = 0; i < 7; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    nextButton = new JButton("Next");
    nextButton.addActionListener(new updateButton());
    topRight.add(nextButton);
    for (int i = 0; i < 7; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    saveButton = new JButton("Save");
    saveButton.addActionListener(new updateButton());
    topRight.add(saveButton);
    for (int i = 0; i < 6; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }

    topPanel.add(topRight);

    JLabel messageAreaLabel = new JLabel("Messages");
    messageAreaLabel.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Text Area

    JPanel printAreaPanel = new JPanel(new BorderLayout());

    printArea_updatePanel = new JTextArea();
    printArea_updatePanel.setFont(printFont);
    printArea_updatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    printArea_updatePanel.setEditable(false);
    JScrollPane scroll = new JScrollPane(printArea_updatePanel);

    printAreaPanel.add(scroll);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    bottomPanel.add(messageAreaLabel, BorderLayout.PAGE_START);
    bottomPanel.add(printAreaPanel, BorderLayout.CENTER);

    costBasis.add(topPanel);
    costBasis.add(bottomPanel);

    return costBasis;
  }

  /**
   * This method creates the Gains JPanel where the user can view their Gains for Funds
   * @return getGain JPanel
   */
  public JPanel totalGainsWindow() {
    totalValue = new JPanel(new GridLayout(2, 1));
    totalValue.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    Font printFont = new Font("SansSerif", Font.BOLD, 24);

    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JPanel topLeft = new JPanel(new GridLayout(7, 1));
    //TopRight is produced in a method
    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel panelName = new JLabel("   Getting total gain");
    panelName.setHorizontalAlignment(SwingConstants.LEFT);
    panelName.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Total Gains
    JPanel totalGain = new JPanel();
    totalGain.setLayout(new GridLayout(0, 2));

    JLabel name = new JLabel("Total gain");
    name.setFont(printFont);

    totalGainsTextField_totalgainsSection.setFont(printFont);
    totalGainsTextField_totalgainsSection.setEditable(false);

    totalGain.add(name);
    totalGain.add(totalGainsTextField_totalgainsSection);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    topLeft.add(panelName);
    topLeft.add(fillerPanel());
    topLeft.add(totalGain);

    topPanel.add(topLeft);
    topPanel.add(fillerPanel());

    JLabel messageAreaLabel = new JLabel("Individual gains");
    messageAreaLabel.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Text Area

    JPanel printAreaPanel = new JPanel(new BorderLayout());

    printArea_totalGainsPanel = new JTextArea();
    printArea_totalGainsPanel.setFont(printFont);
    printArea_totalGainsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    printArea_totalGainsPanel.setEditable(false);
    JScrollPane scroll = new JScrollPane(printArea_totalGainsPanel);

    printAreaPanel.add(scroll);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    bottomPanel.add(messageAreaLabel, BorderLayout.PAGE_START);
    bottomPanel.add(printAreaPanel, BorderLayout.CENTER);

    totalValue.add(topPanel);
    totalValue.add(bottomPanel);

    return totalValue;
  }

  /**
   * This method creates the search JPanel where the user can enter values to search for Criteria in their Funds.
   * @return search JPanel
   */
  public JPanel searchWindow() {
    upload = new JPanel(new GridLayout(2, 1));
    upload.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    Font printFont = new Font("SansSerif", Font.BOLD, 24);

    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JPanel topLeft = new JPanel(new GridLayout(7, 1));
    //TopRight is produced in a method
    JPanel bottomPanel = new JPanel(new BorderLayout());

    JLabel panelName = new JLabel("Searching Investment");
    panelName.setHorizontalAlignment(SwingConstants.CENTER);
    panelName.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Symbol Panel creation
    JPanel symbolPanel = new JPanel();
    symbolPanel.setLayout(new GridLayout(0, 2));

    JLabel symbolName = new JLabel("Symbol");
    symbolName.setFont(printFont);

    symbolTextField_searchSection.setFont(printFont);

    symbolPanel.add(symbolName);
    symbolPanel.add(symbolTextField_searchSection);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>name Key Word Panel creation
    JPanel nameKeywordPanel = new JPanel();
    nameKeywordPanel.setLayout(new GridLayout(0, 2));

    JLabel nameKeyname = new JLabel("Name keywords");
    nameKeyname.setFont(printFont);

    nameKeyWordTextField_searchSection.setFont(printFont);

    nameKeywordPanel.add(nameKeyname);
    nameKeywordPanel.add(nameKeyWordTextField_searchSection);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>low price Panel creation
    JPanel lowPrice = new JPanel();
    lowPrice.setLayout(new GridLayout(0, 2));

    JLabel lowName = new JLabel("Low price");
    lowName.setFont(printFont);

    lowPriceTextField_SearchSection.setFont(printFont);

    lowPrice.add(lowName);
    lowPrice.add(lowPriceTextField_SearchSection);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>high price Panel creation
    JPanel highPrice = new JPanel();
    highPrice.setLayout(new GridLayout(0, 2));

    JLabel highName = new JLabel("High price");
    highName.setFont(printFont);

    highPriceTextField_searchSection.setFont(printFont);

    highPrice.add(highName);
    highPrice.add(highPriceTextField_searchSection);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END


    topLeft.add(panelName);
    topLeft.add(fillerPanel());
    topLeft.add(symbolPanel);
    topLeft.add(nameKeywordPanel);
    topLeft.add(lowPrice);
    topLeft.add(highPrice);

    topPanel.add(topLeft);

    topPanel.add(resetSearchPanel());

    JLabel messageAreaLabel = new JLabel("Search results");
    messageAreaLabel.setFont(printFont);

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Text Area

    JPanel printAreaPanel = new JPanel(new BorderLayout());

    printArea_searchPanel = new JTextArea();
    printArea_searchPanel.setFont(printFont);
    printArea_searchPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    printArea_searchPanel.setEditable(false);
    JScrollPane scroll = new JScrollPane(printArea_searchPanel);

    printAreaPanel.add(scroll);
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>END

    bottomPanel.add(messageAreaLabel, BorderLayout.PAGE_START);
    bottomPanel.add(printAreaPanel, BorderLayout.CENTER);

    upload.add(topPanel);
    upload.add(bottomPanel);

    return upload;
  }

  //************************************************************//
  //***This section is for the reset + Support Buttons**********//
  //************************************************************//

  /**
   * This method creates a filler JPanel used to pad the Window panels (Buy, sell, update, getGains and Search)
   * @return filler JPanel
   */
  public JPanel fillerPanel() {

    JPanel fillerPanel = new JPanel();
    fillerPanel.setLayout(new GridLayout(0, 2));

    fillerPanel.add(new JPanel());
    fillerPanel.add(new JPanel());

    return fillerPanel;
  }

  /**
   * This method creates a Reset + Buy JPanel for the Buy Window
   * @return JPanel
   */
  public JPanel resetBuyPanel() {

    JPanel topRight = new JPanel(new GridLayout(7, 3));

    for (int i = 0; i < 7; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    resetButton = new JButton("Reset");
    resetButton.addActionListener(new resetButtonAction());
    topRight.add(resetButton);
    for (int i = 0; i < 5; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    buyButton = new JButton("Buy");
    buyButton.addActionListener(new buyButtonAction());
    topRight.add(buyButton);
    for (int i = 0; i < 6; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }

    return topRight;
  }

  /**
   * This method creates the reset + Sell Button panel for the Sell Window
   * @return JPanel
   */
  public JPanel resetSellPanel() {

    JPanel topRight = new JPanel(new GridLayout(7, 3));

    for (int i = 0; i < 7; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    resetButton = new JButton("Reset");
    resetButton.addActionListener(new resetButtonAction());
    topRight.add(resetButton);
    for (int i = 0; i < 5; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    sellButton = new JButton("Sell");
    sellButton.addActionListener(new sellButtonAction());
    topRight.add(sellButton);
    for (int i = 0; i < 6; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }

    return topRight;
  }

  /**
   * This method creates the reset + Search Button panel for the Search Window
   * @return JPanel
   */
  public JPanel resetSearchPanel() {

    JPanel topRight = new JPanel(new GridLayout(7, 3));

    for (int i = 0; i < 7; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    resetButton = new JButton("Reset");
    resetButton.addActionListener(new resetButtonAction());
    topRight.add(resetButton);
    for (int i = 0; i < 5; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }
    searchButton = new JButton("Search");
    searchButton.addActionListener(new searchButton());
    topRight.add(searchButton);
    for (int i = 0; i < 6; i++) {
      JPanel filler = new JPanel();
      topRight.add(filler);
    }

    return topRight;
  }

  /**
   * This method creates add onto the Frame specifications and the Menu bar.
   */
  public void window() {

    setTitle("Investment Portfolio");
    setSize(WIDTH, HEIGHT);
    //setMinimumSize(new Dimension(WIDTH, HEIGHT));
    //Creating the Menu Bar
    JMenuBar menuBar = new JMenuBar();
    //Creating the Menu
    JMenu menu = new JMenu("Commands");
    JMenuItem buy = new JMenuItem("Buy");
    JMenuItem sell = new JMenuItem("Sell");
    JMenuItem updatePage = new JMenuItem("Update");
    JMenuItem totalGain = new JMenuItem("Get Gains");
    JMenuItem search = new JMenuItem("Search");
    JMenuItem quit = new JMenuItem("Quit");
    //Adding the action listeners
    buy.addActionListener(new buyPanelShow());
    sell.addActionListener(new sellPanelShow());
    updatePage.addActionListener(new updatePanelShow());
    totalGain.addActionListener(new totalGainsPanelShow());
    search.addActionListener(new searchPanelShow());
    quit.addActionListener(new quitPanelShow());
    //Adding the Items to the Menu
    menu.add(buy);
    menu.add(sell);
    menu.add(updatePage);
    menu.add(totalGain);
    menu.add(search);
    menu.add(quit);
    //Adding the menu to the MenuBar
    menuBar.add(menu);
    //Adding the menuBar to the frame
    setJMenuBar(menuBar);
  }

  @Override
  public void addFeatures(ControllerGUIImpl features) {

  }

  //**************************************************//
  //***This section is for the inter Classes**********//
  //**************************************************//

  /**
   * This class is used to implement ActionListener to update the frame and show the Buy Panel
   */
  private class buyPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Buy")) {
        emptyTextFields();
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Buy Panel");
      }
    }
  }

  /**
   * This class is used to implement ActionListener to update the frame and show the Sell Panel
   */
  private class sellPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Sell")) {
        emptyTextFields();
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Sell Panel");
      }
    }
  }

  /**
   * This class is used to implement ActionListener to update the frame and show the update Panel
   */
  private class updatePanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Update")) {
        location = 0;
        emptyTextFields();
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Update Panel");
        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        saveButton.setEnabled(true);
      }

    }
  }

  /**
   * This class is used to implement ActionListener to update the frame and show the getGains Panel
   */
  private class totalGainsPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Get Gains")) {
        emptyTextFields();
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Total Panel");
      }
    }
  }

  /**
   * This class is used to implement ActionListener to update the frame and show the Search Panel
   */
  private class searchPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Search")) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "Search Panel");
      }
    }
  }

  /**
   * This class is used to implement ActionListener to quit the frame and program
   */
  private class quitPanelShow implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
    }
  }

  //********************************************************************//
  //***This section is for the ActionListeners for the buttons**********//
  //********************************************************************//

  /**
   * This class is used to implement ActionListener to reset the JTextAreas with user inserted Text
   */
  private class resetButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Reset")) {

        buyPanelReset();

        sellPanelReset();

        updatePanelReset();

        searchPanelReset();

      }
    }
  }

  /**
   * This class is used to implement ActionListener to buy funds with the information in the JTextAreas which the user inserted
   */
  private class buyButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Buy")) {
        String symbol = symbolTextField_buyPanel.getText();
        String name = nameTextField_buyPanel.getText();
        int quantity = 0;
        double price = 0;

        try {
          if (!(quantityTextField_buyPanel.getText().isEmpty())) {
            quantity = Integer.parseInt(quantityTextField_buyPanel.getText());
            if (quantity < 0) {
              printArea_buyPanel.append(">> Fund Quantity cannot be Zero!\n");
              return;
            }
          } else {
            printArea_buyPanel.append(">> Fund quantity required!\n");
            return;
          }
        } catch (NumberFormatException a) {
          printArea_buyPanel.append(">> Invalid Quantity entered!\n");
          return;
        }

        try {
          if (!(priceTextField_buyPanel.getText().isEmpty())) {
            price = Integer.parseInt(priceTextField_buyPanel.getText());
            if (price < 0.0001) {
              printArea_buyPanel.append(">> Fund Price cannot be Zero!\n");
              return;
            }
          } else {
            printArea_buyPanel.append(">> Fund Price required!\n");
            return;
          }
        } catch (NumberFormatException a) {
          printArea_buyPanel.append(">> Fund Price required!\n");
          return;
        }

        String currentState = (String) typeOptionList.getSelectedItem();
        buyPanelReset();
      }
    }
  }

  /**
   * This class is used to implement ActionListener to sell funds with the information in the JTextAreas which the user inserted
   */
  private class sellButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      if (buttonString.equals("Sell")) {
        String symbol;
        int quantity = -1;
        double price = -1;

        if (!symbolTextField_sellPanel.getText().isEmpty()) {
          symbol = symbolTextField_sellPanel.getText();
          if (symbol.equals("") || symbol.trim().isEmpty()) {
            printArea_sellPanel.append(">> Fund Symbol required!\n");
            return;
          }
        } else {
          printArea_buyPanel.append(">> Fund Symbol required!\n");
          return;
        }

        if(!quantityTextField_sellPanel.getText().isEmpty()) {
          quantity = Integer.parseInt(quantityTextField_sellPanel.getText());
          if (quantity <=0) {
            printArea_sellPanel.append(">> Cannot Sell this amount!\n");
            return;
          }
        } else {
          printArea_sellPanel.append(">> Fund Quantity required!\n");
          return;
        }

        if(!priceTextField_sellPanel.getText().isEmpty()) {
          price = Integer.parseInt(priceTextField_sellPanel.getText());
          if (price <=0) {
            printArea_sellPanel.append(">> Cannot Sell at this price!\n");
            return;
          }
        } else {
          printArea_buyPanel.append(">> Fund Price required!\n");
          return;
        }
      }
    }
  }

  /**
   * This class is used to implement ActionListener to update funds with the information in the JTextAreas which the user inserted
   */
  private class updateButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String buttonString = e.getActionCommand();
      symbolTextField_updatePanel.setEditable(false);
      nameTextField_updatePanel.setEditable(false);
      if (buttonString.equals("Prev")) {
        nextButton.setEnabled(true);
        if (!((location-1) < 0)) {
          location = location - 1;
          symbolTextField_updatePanel.setEditable(false);
          nameTextField_updatePanel.setEditable(false);
          if ((location-1) < 0) {
            prevButton.setEnabled(false);
          }
          return;
        }
        else {
          prevButton.setEnabled(false);

          return;
        }
      }
      if(buttonString.equals("Save")) {
        double price = 0.1;
        try {
          if (!priceTextField_updatePanel.getText().isEmpty()) {
            price = Double.parseDouble(priceTextField_updatePanel.getText());
            if (price <= 0) {
              printArea_updatePanel.append(">> Cannot update price to this value!\n");
              return;
            }
          } else {
            printArea_updatePanel.append(">> Fund Price required!\n");
            return;
          }
        } catch (Exception ex) {
          printArea_updatePanel.append(">> Incorrect Fund price format...\n");
        }
        printArea_updatePanel.append(">> Price has been updated!\n");
      }
    }
  }

  /**
   * This class is used to implement ActionListener to saerch for funds with the information that the user inserted in the JTextArea.
   */
  private class searchButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      printArea_searchPanel.setText("");
      String symbolSearch = "";
      String nameKeys = "";
      double onlyNum = -1;
      double minNum = -1;
      double maxNum = -1;
      String buttonString = e.getActionCommand();
      if(buttonString.equals("Search")) {

        if (!symbolTextField_searchSection.getText().isEmpty()) {
          symbolSearch = symbolTextField_searchSection.getText();
        }

        if (!nameKeyWordTextField_searchSection.getText().isEmpty()) {
          nameKeys = nameKeyWordTextField_searchSection.getText();
        }

        if(!lowPriceTextField_SearchSection.getText().isEmpty()) {
          try {
            minNum = Double.parseDouble(lowPriceTextField_SearchSection.getText());
            if (minNum <= 0) {
              printArea_searchPanel.append(">> Cannot Have Low Price less than 0!\n");
              return;
            }
          } catch (Exception ex) {
            printArea_searchPanel.append(">> Incorrect Low Price format...\n");
            return;
          }

        }
        if(!highPriceTextField_searchSection.getText().isEmpty()) {
          try {
            maxNum = Double.parseDouble(highPriceTextField_searchSection.getText());
            if (maxNum <= 0) {
              printArea_searchPanel.append(">> Cannot Have High Price less than 0!\n");
              return;
            }
          } catch (Exception ex) {
            printArea_searchPanel.append(">> Incorrect High Price format...\n");
            return;
          }
        }
        //If both Min and Max value provided...
        if ((minNum != -1) && (maxNum !=-1)) {
          //If the min number is greater than Max
          if (minNum > maxNum) {
            printArea_searchPanel.append(">> Cannot Have High Price less than Low Price!\n");
            return;
          }
          //If the min and Max are the same
          if (minNum == maxNum) {
            onlyNum = minNum;
            minNum = maxNum = -1;
          }
        }
        //If only Min Value provided
        if (((minNum) != -1) && (maxNum == -1)) {
          maxNum = 0;
          onlyNum = -1;
        }
        //If only Max Value provided
        if (((minNum) == -1) && (maxNum != -1)) {
          minNum = 0;
          onlyNum = -1;
        }
        searchPanelReset();
      }
    }
  }


  //**************************************************//
  //****This section is for Functions to use**********//
  //**************************************************//

  /**
   * This method is used to reset the Buy panel's JTextArea
   */
  private void buyPanelReset() {
    //For the Buy Panel
    nameTextField_buyPanel.setText("");
    symbolTextField_buyPanel.setText("");
    quantityTextField_buyPanel.setText("");
    priceTextField_buyPanel.setText("");
  }

  /**
   * This method is used to reset the Sell panel's JTextArea
   */
  private void sellPanelReset() {
    //For the Sell Panel
    symbolTextField_sellPanel.setText("");
    quantityTextField_sellPanel.setText("");
    priceTextField_sellPanel.setText("");
  }

  /**
   * This method is used to reset the Update panel's JTextArea
   */
  private void updatePanelReset() {
    //For the Update Panel
    symbolTextField_updatePanel.setText("");
    nameTextField_updatePanel.setText("");
    priceTextField_updatePanel.setText("");
  }

  /**
   * This method is used to reset the Search panel's JTextArea
   */
  private void searchPanelReset() {
    //For the Search Panel
    symbolTextField_searchSection.setText("");
    nameKeyWordTextField_searchSection.setText("");
    lowPriceTextField_SearchSection.setText("");
    highPriceTextField_searchSection.setText("");
  }

  /**
   * This method is used to reset the JTextField on all the panel in the program
   */
  private void emptyTextFields() {
    printArea_buyPanel.setText("");
    printArea_sellPanel.setText("");
    printArea_totalGainsPanel.setText("");
    printArea_updatePanel.setText("");
    printArea_searchPanel.setText("");
  }

}