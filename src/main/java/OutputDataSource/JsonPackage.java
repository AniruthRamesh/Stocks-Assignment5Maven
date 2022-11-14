package OutputDataSource;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public List<String> anotherFormatter() {
    //System.out.println(new Gson().toJson(flexiblePort));
    return List.of(new Gson().toJson(flexiblePort));
  }

  @Override
  public List<String> FormatFromHashMap() {
    JSONObject object = new JSONObject(portfolio);
    List<String> jsonStrings = new ArrayList<>();
    jsonStrings.add(object.toString());
    return jsonStrings;
  }

  @Override
  public HashMap<String, List<List<String>>> Parser(String json) {
    HashMap<String, List<List<String>>> parsed = new Gson().fromJson(json, HashMap.class);
    return parsed;
  }
}
