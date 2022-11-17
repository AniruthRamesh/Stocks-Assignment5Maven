package previousTest;

import org.junit.Before;
import org.junit.Test;

import abstractTest.Abstract;
import mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for ControllerImpl(previous version). It tests the controller using a mock
 * model on various
 * scenarios to see if the controller sends the correct data to the model.
 */
public class ControllerTest extends Abstract {
  MockModel tester;

  @Before
  public void setup() {
    super.setup();
  }

  @Test
  public void portfolioCreationTest() {

    String inputString = "1\n1\nsavings\n3\nApple\n16\n3\nAmazon\n20\n3\nstarbucks\n30\n4\n12";
    String assertVal = "savings Apple 16 Amazon 20 starbucks 30";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForAddPortfolioData());
  }

  @Test
  public void checkIfCompanyNameExists() {
    String inputString = "1\n1\nsavings\n3\nApple\n16\n3\nAmazon\n20\n3\nstarbucks\n30\n4\n12";
    String assertVal = "Received:AppleReceived:AppleReceived:AmazonReceived:Amazon" +
            "Received:starbucksReceived:starbucks";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForCheckingCompanyNameExist());
  }

  @Test
  public void checkIfDateIsValid() {
    String inputString = "3\n1\n31\n02\n2006\n2\n12";
    String assertVal = "Received:2006-02-31";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForIsValidDate());
  }

  @Test
  public void checkMakeString() {
    String inputString = "3\n1\n31\n12\n2006\n2\n12";
    String assertVal = "Day:31Month:12Year:2006";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForMakeString());
  }

  @Test
  public void readFromFile() {
    String inputString = "5\nA:\\Intellij\\PDP\\Stocks-Assignment5Maven\\InFlexiblePortfolios" +
            "\\saving.txt\n12";
    String assertVal = "Received:A:\\Intellij\\PDP\\Stocks-Assignment5Maven\\InFlexiblePortfolios" +
            "\\" +
            "saving.txt";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForReadFromFile());
  }

  @Test
  public void helperTest() {
    String inputString = "1\n1\ntest\n3\napple\n1.5\n4\n2\n11\n";
    String assertVal = "Received:1.5";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForHelper());
  }

  @Test
  public void setCurrentDateTest() {
    String inputString = "3\n1\n1\n2\n2022\n2\n12";
    String assertVal = "2022-02-01";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForSetCurrentDate());
  }

  @Test
  public void portfolioContainsCertainKeyTest() {
    String inputString = "1\n1\nabc\n3\namazon\n10\n4\n4\n1\nabc\n4\n12";
    String assertVal = "abc";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForPortfolioContainsCertainKey());
  }

  @Test
  public void hasAnotherPortfolioWithSameNameTest() {
    String inputString = "1\n1\nsavings\n3\namazon\n10\n4\n1\n1\nsavings\n4\n12";
    String assertVal = "savingssavings";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForHasAnotherPortfolioWithSameName());
  }

  @Test
  public void setContainsGivenDateTest() {
    String inputString = "1\n1\nabc\n3\namazon\n10\n4\n4\n1\nabc\n3\n1\n12\n2\n2020\n4\n12";
    String assertVal = "2020-02-12";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getSetContainsGivenDate());
  }

  @Test
  public void getTotalStockValueTest() {
    String inputString = "1\n1\nsavings\n3\napple\n10\n4\n4\n1\nsavings\n2\n4\n12";
    String assertVal = "savings2001-02-02";
    tester = testingHelper(inputString);
    assertEquals(assertVal, tester.getLogForTotalStockValue());
  }
}
