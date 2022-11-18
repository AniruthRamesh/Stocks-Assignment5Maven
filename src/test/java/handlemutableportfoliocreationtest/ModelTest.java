package handlemutableportfoliocreationtest;

import org.junit.Before;
import org.junit.Test;

import abstracttest.Abstract;
import inputdata.AlphaVantageApi;
import inputdata.InputDataSource;
import mock.MockModel;

import static org.junit.Assert.assertEquals;


/**
 * A Junit test for the class HandleMutablePortfolioCreation. This class is mainly a testing class
 * for the Model. A mock Model logs the calculated values and we check by retrieving values
 * from the logger.
 */
public class ModelTest extends Abstract {
  MockModel tester;

  @Before
  public void setup() {
    super.setup();
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist false
   * because the ticker does not exist initially) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistFalse() {
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("false", tester.getCheckIfTickerExistsReturnValue());
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist true
   * because we have already added aapl stock) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistTrue() {
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n1\naapl\n1\n25\n10\n2022\n18"
            + "\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getCheckIfTickerExistsReturnValue());
  }

  @Test
  public void checkModelReturnValueAddApiCompanyStockData() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    InputDataSource inp = new AlphaVantageApi();
    String successOrFailure = inp.getData("amzn");
    assertEquals(successOrFailure, tester.getAddApiCompanyStockDataReturnValue());
  }

  @Test
  public void checkModelReturnValueAddApiCompanyStockDataFailure() {
    String input = "7\n1\n1\nfees\n3\n1\nabcdefghijk\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("failure", tester.getAddApiCompanyStockDataReturnValue());
  }

  @Test
  public void checkModelProperlyAddsCompanyNameInPutNameInCompanyPortfolio() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getPutNameInCompanyReturnValue());
  }

  @Test
  public void checkModelProperlyAddsCompanyNameInTickerFinder() {
    String input = "7\n1\n1\nfees\n3\n1\namzn\n1\n25\n10\n2022\n19\n2\n4\n12";
    tester = super.testingHelper(input);
    assertEquals("true", tester.getPutCompanyNameInTickerFinderReturn());
  }

  @Test
  public void checkParser() {
    String input = "5\nA:\\Intellij\\PDP\\Stocks-Assignment5Maven\\FlexiblePortfolios\\"
            + "invalid.txt\n2\n12";
    tester = super.testingHelper(input);
    assertEquals("The portfolio provided in the text file is not in proper format"
            + ",please look at the documentation", tester.getLogForParser());

  }

  @Test
  public void checkValidDateFailTest() {
    String input = "7\n1\n1\ntest\n3\n1\naapl\n1\n30\n15\n2022\n2\n12";
    tester = super.testingHelper(input);
    assertEquals("truetruetruetruetruetruetruetruefalse", tester.getValidDateLog());
  }

  @Test
  public void checkValidDatePassTest() {
    String input = "7\n1\n1\ntest\n3\n1\naapl\n1\n30\n10\n2022\n2\n12";
    tester = super.testingHelper(input);
    assertEquals("truetruetruetruetruetruetruetruetrue", tester.getValidDateLog());
  }


}
