package handleStrategyTest;

import org.junit.Before;
import org.junit.Test;


import abstracttest.Abstract;
import mock.MockModel;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the class HandleStrategy. This class is mainly a testing class
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
  public void checkIfDataIsSentToAddApiStockData(){
    String input = "T\n5\nC:\\Users\\anikr\\Desktop\\Career course\\fees.txt\n2\n12\n1\nfees\n1000\n" +
            "1\naapl\n30\n1\namzn\n40\n1\nmsft\n30\n2\n1\n12\n12\n2006\nN\nY\n2\n2\n13";
    tester = super.testingHelper(input);
    assertEquals("Received : aaplReceived : amznReceived : msft",tester.getAddApiCompanyStockDataLog());
  }

  @Test
  public void checkIfDataIsSentToFlexiblePortContainsCertainKey(){
    String input = "T\n5\nC:\\Users\\anikr\\Desktop\\Career course\\fees.txt\n2\n12\n1\nfees\n1000\n" +
            "1\naapl\n30\n1\namzn\n40\n1\nmsft\n30\n2\n1\n12\n12\n2006\nN\nY\n2\n2\n13";
    tester = super.testingHelper(input);
    String val = "Received : aaplReceived : amznReceived : msftReceived : aaplReceived : " +
            "amznReceived : msftReceived : aaplReceived : amznReceived : msftReceived : " +
            "aaplReceived : amznReceived : msftReceived : aaplReceived : amznReceived : " +
            "msftReceived : aaplReceived : amznReceived : msftReceived : aaplReceived : " +
            "amznReceived : msftReceived : aaplReceived : amznReceived : msftReceived : " +
            "aaplReceived : amznReceived : msft";
    assertEquals(val,tester.getLogforCheckIfTickerExist());
  }

  @Test
  public void checkIfPortContainsCertainName(){
    String input = "T\n5\nC:\\Users\\anikr\\Desktop\\Career course\\fees.txt\n2\n12\n1\nfees\n1000\n" +
            "1\naapl\n30\n1\namzn\n40\n1\nmsft\n30\n2\n1\n12\n12\n2006\nN\nY\n2\n2\n13";
    tester = super.testingHelper(input);
    assertEquals("Received : fees",tester.getFlexiblePortContainsCertainKeyLogger());
  }


}
