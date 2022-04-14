import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ErrandsManager {
  // we do have the option to clean up our `calculateTotalDistance` by
  // making our distances accessible to the whole class, all methods
  // then we don't have to hand it in as an argument
  public static Map<String, Double> streetDistances;

  public static void main(String[] args) throws IOException {
    // Step 1 - read txt file and create ArrayList
    ClassLoader classLoader = ErrandsManager.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    File todoFile = new File(classLoader.getResource("todo.txt").getFile());

    Scanner todoScanner = new Scanner(todoFile);

    List<String> todoList = new ArrayList<>();

    while (todoScanner.hasNextLine()) {
      String todoItem = todoScanner.nextLine();
      System.out.println(todoItem);
      todoList.add(todoItem);
    }

    System.out.println("Contents of text file:");
    System.out.println(todoList);

    // Step 2 - Add "go to the ATM"
    int indexOfBuyGroceries = todoList.indexOf("buy groceries");
    todoList.add(indexOfBuyGroceries, "go to the ATM");
    System.out.println("Added going to the ATM:");
    System.out.println(todoList);

    //Step 3
    Map<String, String> taskCompletionTracker = new HashMap<>();

    for (String task : todoList) {
      taskCompletionTracker.put(task, "pending");
    }
    System.out.println(mapper.writeValueAsString(taskCompletionTracker));
//    Step 4
    taskCompletionTracker.put("trim hedges", "complete");
    System.out.println("Mark trim hedges as complete:");
    System.out.println(mapper.writeValueAsString(taskCompletionTracker));

//    Step 5
    File groceriesFile = new File(classLoader.getResource("groceries.json").getFile());
//    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    Map<String, Integer> groceries = new HashMap<>();
    groceries = mapper.readValue(groceriesFile, HashMap.class);
    System.out.println("Read groceries.json into a HashMap");
    String prettyJson = mapper.writeValueAsString(groceries);
    System.out.println(prettyJson);

//    System.out.println(mapper.writeValueAsString((groceries)));

    // Step 6
    groceries.put("Loaf of Bread", 1);
    groceries.put("Gallon of Milk", 1);
    prettyJson = mapper.writeValueAsString(groceries);
    System.out.println("Here are the newly added items:");
//    System.out.println(mapper.writeValueAsString((groceries)));
    System.out.println(prettyJson);

    // Step 7
    File groceryPricesFile = new File(classLoader.getResource("grocery_prices.json").getFile());
    Map<String, Integer> groceryPrices = mapper.readValue(groceryPricesFile, HashMap.class);
    System.out.println("Read in grocery_prices.json");
    System.out.println(mapper.writeValueAsString(groceryPrices));


  // Step 8
//  int total = 0;
    double total = 0;
    System.out.println(groceries.keySet());
    for(String grocery: groceries.keySet()){
      total += groceries.get(grocery) * groceryPrices.get(grocery);
    }
//  double totalInDollars = (double)total/100;
    System.out.println("Total: " + total + " cents");
    System.out.println("Total: $" + total/100);

    // Step 9
    // the below hard-codes the value to "pending" instead of looking at what it is currently in our HashMap
//    taskCompletionTracker.put("go to ATM and withdraw $" + total/100, "pending");
    // instead, we want to look inside our HashMap and find the current status, or value, of "go to the ATM"
    taskCompletionTracker.put("go to ATM and withdraw $" + total/100, taskCompletionTracker.get("go to the ATM"));
    taskCompletionTracker.remove("go to the ATM");
    System.out.println("Update go to ATM to show amount:");
    System.out.println(mapper.writeValueAsString(taskCompletionTracker));

    // Extra steps
    File streetsFile = new File(classLoader.getResource("streets.json").getFile());
    Map<String, Double> streets = mapper.readValue(streetsFile, HashMap.class);
    // if we raise this to the class level, we simply update it here
//    streetDistances = mapper.readValue(streetsFile, HashMap.class);
    System.out.println("Read in streets.json:");
    System.out.println(mapper.writeValueAsString(streets));

    List<String> routeOne = new ArrayList<>();
    routeOne.add("South St");
    routeOne.add("Forest St");
    routeOne.add("Vernon St");
    routeOne.add("South St");

    List<String> routeTwo = new ArrayList<>();
    routeTwo.add("South St");
    routeTwo.add("Forest St");
    routeTwo.add("Sally Way");
    routeTwo.add("Sally Way");
    routeTwo.add("South St");

    System.out.println("Total distance for route one:");
    System.out.println(calculateTotalDistance(routeOne, streets));

    System.out.println("Total distance for route two:");
    System.out.println(calculateTotalDistance(routeTwo, streets));
  }

  public static double calculateTotalDistance(List<String> route, Map<String, Double> distances) {
    double totalDistance = 0.0;
    for(String street : route) {
      System.out.println("street:" + street);
      System.out.println("distance: " + distances.get(street));
      totalDistance += distances.get(street);
      System.out.println("Updated total distance: " + totalDistance);
    }

    return totalDistance;
  }

}
