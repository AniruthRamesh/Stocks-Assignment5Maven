package handllecostbasistest;

import org.junit.Before;
import org.junit.Test;

import abstracttest.Abstract;
import mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the class HandleCostBasis. This class is mainly a testing class
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

  @Test
  public void getCompaniesInCertainPortfolioTest() {
    String input = "5\nA:\\Intellij\\PDP\\Stocks-Assignment5Maven\\FlexiblePortfolios\\fees.txt\n"
            + "2\n9\nfees\n1\n25\n10\n2022\n12";
    tester = super.testingHelper(input);
    assertEquals("Received : fees", tester.getFlexiblePortContainsCertainKeyLogger());
  }
}
