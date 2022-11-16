import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import Controller.Controller;
import Controller.ControllerImpl;
import View.View;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test for the class HandleMutablePortfolioCreation. It tests both the Model and the
 * controller by seeing if the controller send the correct data to the model and test the model
 * by checking if it sends correct data back to the controller.
 */
public class HandleMutablePortfolioCreationTest {
  Controller controller;
  View view;
  InputStream input;
  OutputStream outStream;
  MockModel tester;

  @Before
  public void setup() {
    outStream = new ByteArrayOutputStream();
    view = new View(new PrintStream(outStream));
    tester = new MockModel();
  }

  /**
   * This is a helper method for checking if controller sends the proper data to the model.
   * @param inputString String, input which is entered sequentially to test.
   * @return MockModel object.
   */
  public MockModel testingHelper(String inputString) {
    input = new ByteArrayInputStream(inputString.getBytes());
    MockModel mock = new MockModel();
    controller = new ControllerImpl(mock, view, input);
    controller.start();
    return mock;
  }

  /**
   * {Controller test} check if correct data is sent to model,checkIfTickerExist method.
   */
  @Test
  public void checkControllerSendProperTickerSymbolForCheckIfTickerExists(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n10";
    tester = testingHelper(input);
    assertEquals("Received : aapl",tester.getLogforCheckIfTickerExist());
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> false
   * because the ticker does not exist initially) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistFalse(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n2\n4\n10";
    tester = testingHelper(input);
    assertEquals("false",tester.getCheckIfTickerExistsReturnValue());
  }

  /**
   * {Model test} check if correct data is sent from model(checkIfTickerExist-> true
   * because we have already added aapl stock) to controller.
   */
  @Test
  public void checkModelsReturnValueCheckIfTickerExistTrue(){
    String input = "7\n1\n1\nfees\n3\n1\naapl\n1\n12\n12\n2006\n19\n1\naapl\n1\n25\n10\n2022\n18" +
            "\n2\n4\n10";
    tester = testingHelper(input);
    assertEquals("true",tester.getCheckIfTickerExistsReturnValue());
  }



}
