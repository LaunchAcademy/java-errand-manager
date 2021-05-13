import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ErrandManager {

  private static final String TO_DO_PATH = "todo.txt";
  private static final String GROCERIES_PATH = "groceries.json";
  private static final String GROCERY_PRICES_PATH = "grocery_prices.json";
  private static final String STREETS_PATH = "streets.json";

  public static void main(String[] args) throws IOException {
    ClassLoader classLoader = ErrandManager.class.getClassLoader();
    File todoListFile = new File(classLoader.getResource(TO_DO_PATH).getFile());
    File groceryFile = new File(classLoader.getResource(GROCERIES_PATH).getFile());
    File groceryPricesFile = new File(classLoader.getResource(GROCERY_PRICES_PATH).getFile());
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    Scanner todoListScanner = new Scanner(todoListFile);

    //step 1
    List<String> todoList = new ArrayList<>();
    while (todoListScanner.hasNextLine()) {
      todoList.add(todoListScanner.nextLine());
    }

    //step 2
    todoList.add(todoList.indexOf("buy groceries"), "go to the ATM");

    //step 3
    Map<String, String> toDoMap = new HashMap<>();
    for (String item : todoList) {
      toDoMap.put(item, "pending");
    }

    //step 4
    if (toDoMap.containsKey("trim hedges")) {
      toDoMap.replace("trim hedges", "complete");
    } else {
      System.out.println("The task \"trim hedges\" was not found.");
    }

    //step 5
    Map<String, Integer> groceries = new HashMap<>();
    groceries = objectMapper.readValue(groceryFile, HashMap.class);

    //step 6
    groceries.put("Loaf of Bread", 1);
    groceries.put("Gallon of Milk", 1);

    // step 7
    Map<String, Integer> groceriesPrices = new HashMap<>();
    groceriesPrices = objectMapper.readValue(groceryPricesFile, HashMap.class);

    //step 8
    int totalSum = 0;
    for (String itemToBuy : groceries.keySet()) {
      int numberToBuy = groceries.get(itemToBuy);
      int priceOfItem = groceriesPrices.get(itemToBuy);
      totalSum += numberToBuy * priceOfItem;
    }

    //step 9
    todoList.remove("go to the ATM");
    double dolarTotal = (double) totalSum / 100;
    todoList.add(todoList.indexOf("buy groceries"), "go to the ATM $ " + dolarTotal);

    toDoMap.put("go to the ATM $ " + dolarTotal, "pending");
    toDoMap.remove("go to the ATM");

//    System.out.println(totalSum);
//    System.out.println(todoList);
//    System.out.println(toDoMap);
//    System.out.println(groceries);
//    System.out.println(groceriesPrices);

    // Step 10
    File streetsFile = new File(classLoader.getResource(STREETS_PATH).getFile());
    Map<String, Double> streets = objectMapper.readValue(streetsFile, HashMap.class);
    System.out.println(streets);

    // Step 11
    List<String> runningRoutes = new ArrayList<String>(
        List.of("South St", "Forest St", "Vernon St", "South St"));
    System.out.println(runningRoutes);

    //Step 12
    List<String> secondRunningRoute = new ArrayList<>();
    secondRunningRoute.add("South St");
    secondRunningRoute.add("Forest St");
    secondRunningRoute.add("Sally Way");
    secondRunningRoute.add("Sally Way");
    secondRunningRoute.add("South St");
    System.out.println(secondRunningRoute);

    double runDistance = totalRunDistance(secondRunningRoute, streets);
    System.out.println("Route 2: " + runDistance);
    System.out.println("Route 1: " + totalRunDistance(runningRoutes, streets));
  }

  private static double totalRunDistance(List<String> routes, Map<String, Double> streets) {
    double total = 0.0;
    for (String route : routes) {
      double distance = streets.get(route);

      total += distance;
    }

    return total;
  }
}
