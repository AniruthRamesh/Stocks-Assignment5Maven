package HandleSellPortfolioTest;

import org.junit.Before;
import org.junit.Test;

import Abstract.Abstract;
import InputData.AlphaVantageAPI;
import InputData.InputDataSource;
import Mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the model class by checking if the correct data is sent from model to
 * controller.
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

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> false
   * because the ticker does not exist initially) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistFalse() {
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("false", tester.getCheckIfTickerExistsReturnValue());
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> true
   * because we have already added aapl stock) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistTrue() {
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n1\naapl\n1\n25\n10\n2022\n18" +
            "\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getCheckIfTickerExistsReturnValue());
  }

  @Test
  public void checkModelReturnValueAddApiCompanyStockData() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    InputDataSource inp = new AlphaVantageAPI();
    String successOrFailure = inp.getData("amzn");
    assertEquals(successOrFailure, tester.getAddApiCompanyStockDataReturnValue());
  }

  @Test
  public void checkModelReturnValueAddApiCompanyStockDataFailure() {
    String input = "7\n1\n1\nfees\n3\n1\nabcdefghijk\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("failure", tester.getAddApiCompanyStockDataReturnValue());
  }

  @Test
  public void checkModelProperlyAddsCompanyNameInPutNameInCompanyPortfolio() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getPutNameInCompanyReturnValue());
  }

  @Test
  public void sellExistingStocks() {
    String input = "5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n02\n02\n2022\n20\n2\n11";
    tester = super.testingHelper(input);
    assertEquals("ProfileUpdated", tester.getEnterValidStockToSellLog());
  }

  @Test
  public void sellMoreThanExistingStocks() {
    String input = "5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n02\n02\n2022\n20000\n2\n11";
    tester = super.testingHelper(input);
    assertEquals("Please enter a valid number. The number is either negative or more than the " +
            "stocks that exists.", tester.getEnterValidStockToSellLog());
  }

  @Test
  public void checkValidDateFailTest() {
    String input = "5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n30\n02\n2022\n2\n11";
    tester = super.testingHelper(input);
    assertEquals("false", tester.getValidDateLog());
  }

  @Test
  public void checkValidDatePassTest() {
    String input = "5\nD://test.txt\n2\n8\n1\ntest\n1\naapl\n1\n02\n02\n2022\n2\n11";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getValidDateLog());
  }


}
