package outputdatasource;

import java.util.HashMap;
import java.util.List;

/**
 * Defining a new interface for saving the data source.
 */
public interface SavingDataSource {
  /**
   * It returns a list of strings.
   *
   * @return A list of strings.
   */
  List<String> formatFromHashMap();

  /**
   * Given a JSON string, return a HashMap that maps each key to a list of lists of strings.
   *
   * @param json The json string to be parsed.
   * @return A HashMap with a String as the key and a List of List of Strings as the value.
   */
  HashMap<String, List<List<String>>> parser(String json);
}
