package HandleMutablePortfolioCreationTest;
import org.junit.Before;
import org.junit.Test;

import Abstract.Abstract;
import Mock.MockModel;

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
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> false
   * because the ticker does not exist initially) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistFalse(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("false",tester.getCheckIfTickerExistsReturnValue());
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> true
   * because we have already added aapl stock) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistTrue(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n1\naapl\n1\n25\n10\n2022\n18" +
            "\n2\n4\n11";
    tester = super.testingHelper(input);
    assertEquals("true",tester.getCheckIfTickerExistsReturnValue());
  }
}
