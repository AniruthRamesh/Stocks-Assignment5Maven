package handlleCostBasisTest;

import org.junit.Before;
import org.junit.Test;

import abstractTest.Abstract;
import mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the class HandleMutablePortfolioCreation. This class is mainly a testing class
 * for the Model. A mock Model logs the calculated values and we check by retrieving values
 * from the logger.
 */
public class ModelTest extends Abstract {
  MockModel tester;

  /**
   * Setting up the environment for the test.
   */
  @Before
  public void setup() {
    super.setup();
  }

  @Test
  public void getFlexiblePortContainsCertainKeyReturn() {
    String input = "5\nA:\\Intellij\\PDP\\Stocks-Assignment5Maven\\FlexiblePortfolios\\fees.txt\n" +
            "2\n9\nfees\n1\n25\n10\n2022\n12";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getFlexiblePortContainsCertainKeyReturnValue());
  }

  @Test
  public void getCalculatedCostBasis() {
    String input = "5\nD:\\qwe.txt\n2\n9\nqwe\n1\n25\n10\n2022\n12";
    tester = super.testingHelper(input);
    assertEquals("[Buy, aapl, 200.0, 2010-10-20, 6210.60, 62106.00]",
            tester.getLogForCostBasis());
  }
}
