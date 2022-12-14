package handlesellportfolio;

import org.junit.Before;
import org.junit.Test;

import abstracttest.Abstract;
import mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the class HandleSellPortfolio. This class is mainly a testing class
 * for the controller by seeing if the controller send the correct data to the model. A mock model
 * logs the data received, and we check based on that.
 */
public class ControllerTest extends Abstract {
  MockModel tester;

  /**
   * Setting up the environment for the test.
   */
  @Before
  public void setup() {
    super.setup();
  }

  /**
   * {Controller test} check if correct data is sent to model,checkIfTickerExist method.
   */
  @Test
  public void checkControllerSendProperTickerSymbolForCheckIfTickerExists() {
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("Received : aapl", tester.getLogforCheckIfTickerExist());
  }

  @Test
  public void checkControllerSendProperTickerSymbolForAddApiStockData() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn", tester.getAddApiCompanyStockDataLog());
  }

  @Test
  public void checkControllerSentPutNameInCompanyInPortfolio() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn", tester.getPutNameInCompanyInPortfolioLog());
  }

  @Test
  public void checkControllerSentPutCompanyNameInTickerFinder() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn:0", tester.getPutCompanyNameInTickerFinderLog());
  }

  @Test
  public void checkSetFlexibleAddPortfolioLog() {
    String input = "5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n02\n02\n2022\n20\n2\n12";
    tester = super.testingHelper(input);
    assertEquals("Received", tester.getSetFlexibleAddPortfolioLog());
  }

  @Test
  public void getflexiblePortfolioContainsCertainKeyLogTest() {
    String input = "8\n1\nfees\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("Received fees", tester.getflexiblePortfolioContainsCertainKeyLog());
  }


}

