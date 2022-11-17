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
  /**
   * Setting up the environment for the test.
   */

  public void setup() {
    super.setup();
  }

  /**
   * {Controller test} check if correct data is sent to model,checkIfTickerExist method.
   */
  @Test
  public void checkControllerSendProperTickerSymbolForCheckIfTickerExists() {
    String input = "8\n1\nfees\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("Received fees", tester.getflexiblePortfolioContainsCertainKeyLog());
  }
  @Test
  public void checkSetFlexibleAddPortfolioLog(){
    String input ="5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n02\n02\n2022\n20\n2\n11";
            tester = super.testingHelper(input);
    assertEquals("Received",tester.getSetFlexibleAddPortfolioLog());
  }
}
