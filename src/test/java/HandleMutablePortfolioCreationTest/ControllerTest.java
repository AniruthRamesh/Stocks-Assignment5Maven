package HandleMutablePortfolioCreationTest;
import org.junit.Before;
import org.junit.Test;

import Abstract.Abstract;
import Mock.MockModel;

import static org.junit.Assert.assertEquals;


/**
 * A Junit test for the class HandleMutablePortfolioCreation. This class is mainly a testing class
 * for the controller by seeing if the controller send the correct data to the model. A mock model
 * logs the data received, and we check based on that.
 */
public class ControllerTest extends Abstract {
  MockModel tester;

  @Before
  public void setup() {
    super.setup();
  }

  /**
   * {Controller test} check if correct data is sent to model,checkIfTickerExist method.
   */
  @Test
  public void checkControllerSendProperTickerSymbolForCheckIfTickerExists(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("Received : aapl",tester.getLogforCheckIfTickerExist());
  }

  @Test
  public void checkControllerSendProperTickerSymbolForAddApiStockData(){
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn",tester.getAddApiCompanyStockDataLog());
  }

  @Test
  public void checkControllerSentPutNameInCompanyInPortfolio(){
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn",tester.getPutNameInCompanyInPortfolioLog());
  }

  @Test
  public void checkControllerSentPutCompanyNameInTickerFinder(){
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("Received : amzn:0",tester.getPutCompanyNameInTickerFinderLog());
  }



}
