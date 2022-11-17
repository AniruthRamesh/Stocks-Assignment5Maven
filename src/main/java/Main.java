import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import view.View;

/**
 * This is the class which contains main method. Creates an object for view with PrintStream
 * as System.out. Creates an object for Model.Model Interface and provides all these objects
 * as parameter to controller. Calls the controller's start method, which is the starting point
 * in the application.
 */
public class Main {
  /**
   * It creates a new Model.Model, View.View, and Controller.Controller, and then starts the
   * controller.
   *
   * @param args accepts a single argument of type String array
   */
  public static void main(String[] args) {
    //use this for main
    Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\res\\");

    //use this for jar
    //Path path = Path.of(Path.of(System.getProperty("user.dir"))+"");

    String zipFilePath = path.toString() + "\\stockData.zip";
    String destDir = path.toString();
    unzip(zipFilePath, destDir);


    Model model = new ModelImpl();
    View view = new View(System.out);
    model.createDirectory();
    Controller controller = new ControllerImpl(model, view, System.in);
    controller.start();

    //change model.getContentsFromFile when building jar
  }

  private static void unzip(String zipFilePath, String destDir) {
    File dir = new File(destDir);
    if (!dir.exists()) dir.mkdirs();
    {
      FileInputStream fis;
      byte[] buffer = new byte[1024];
      try {
        fis = new FileInputStream(zipFilePath);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
          String fileName = ze.getName();
          File newFile = new File(destDir + File.separator + fileName);
          new File(newFile.getParent()).mkdirs();
          FileOutputStream fos = new FileOutputStream(newFile);
          int len;
          while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
          }
          fos.close();
          zis.closeEntry();
          ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
