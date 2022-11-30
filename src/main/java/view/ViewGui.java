package view;

import javax.swing.*;

import controller.ControllerGUIImpl;

public interface ViewGui {

  void addFeatures(ControllerGUIImpl features);

  void createMessageBox(JPanel frame, String message);
}
