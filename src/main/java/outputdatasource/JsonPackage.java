package outputdatasource;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to convert the portfolio data into a JSON format and vice versa.
 */
public class JsonPackage implements SavingDataSource {
  Map<String, List<List<String>>> portfolio;
  Map<String, Map<String, List<List<String>>>> flexiblePort;
  List<String> keys;

  /**
   * Constructor for the Json class. Initializes the fields portfolio, keys with argument.
   *
   * @param portfolio Map of string and a nested List of strings, represents the portfolio data.
   * @param keys      List of strings, containing keys(names of companies) of the portfolio.
   */
  public JsonPackage(Map<String, List<List<String>>> portfolio, List<String> keys) {
    this.portfolio = portfolio;
    this.keys = keys;
  }

  /**
   * Constructor for JsonPackage class which initializes flexiblePort.
   *
   * @param flexiblePort Nested hashmap of strings and nested list of strings.
   */
  public JsonPackage(Map<String, Map<String, List<List<String>>>> flexiblePort) {
    this.flexiblePort = flexiblePort;
  }


  /**
   * A default constructor for the Json class. Initializes field with default values.
   */
  public JsonPackage() {
    portfolio = new HashMap<>();
    keys = new ArrayList<>();
  }

  /**
   * We're going to create a new Gson object, and then we're going to use that Gson
   * object to convert our flexiblePort object into a JSON string, and then we're going to return
   * that JSON string as a
   * list.
   *
   * @return A list of strings.
   */
  public List<String> anotherFormatter() {
    return List.of(new Gson().toJson(flexiblePort));
  }

  /**
   * It parses a JSON string into a Map of Maps of nested Lists of Strings.
   *
   * @param json The JSON string to parse.
   * @return A map of maps of nested lists of strings.
   */
  public Map<String, Map<String, List<List<String>>>> anotherParser(String json) {
    Map<String, Map<String, List<List<String>>>> obj = new Gson().fromJson(json, HashMap.class);
    return obj;
  }

  @Override
  public List<String> formatFromHashMap() {
    JSONObject object = new JSONObject(portfolio);
    List<String> jsonStrings = new ArrayList<>();
    jsonStrings.add(object.toString());
    return jsonStrings;
  }

  @Override
  public HashMap<String, List<List<String>>> parser(String json) {
    HashMap<String, List<List<String>>> parsed = new Gson().fromJson(json, HashMap.class);
    return parsed;
  }
}
