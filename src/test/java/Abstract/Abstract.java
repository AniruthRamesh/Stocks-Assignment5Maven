package Abstract;

import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import Controller.Controller;
import Controller.ControllerImpl;
import Mock.MockModel;
import View.View;

/**
 * This class is a helper class which all test classes extend, Every test class needs object of
 * Controller,view,input,out stream, MockModel, all these are created here.
 */
public abstract class Abstract {
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
   * This is a helper method which creates all the necessary objects and returns a MockModel obj.
   *
   * @param inputString String, input which is entered sequentially to test.
   * @return Mock.MockModel object.
   */
  public MockModel testingHelper(String inputString) {
    input = new ByteArrayInputStream(inputString.getBytes());
    MockModel mock = new MockModel();
    controller = new ControllerImpl(mock, view, input);
    controller.start();
    return mock;
  }
}
