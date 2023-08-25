import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ErrandManager {

  public static void main(String[] args) throws IOException {
//    1. read todo into ArrayList
    ClassLoader classLoader = ErrandManager.class.getClassLoader();
    File todoFile = new File(classLoader.getResource("todo.txt").getFile());
    Scanner todoScanner = new Scanner(todoFile);
    List<String> todoList = new ArrayList();

    while(todoScanner.hasNextLine()) {
      todoList.add(todoScanner.nextLine());
    }
    System.out.println("1. My To Do List:");
    System.out.println(todoList);

//    2. add go to ATM before buy groceries
    int indexGroceries = todoList.indexOf("buy groceries");
    todoList.add(indexGroceries, "go to the ATM");

    System.out.println("\n2. Added ATM ...");
    System.out.println(todoList);

//    3. track task status in HashMap
    Map<String, String> taskCompletionTracker = new HashMap<>();
    for(String task: todoList) {
      taskCompletionTracker.put(task, "pending");
    }

    System.out.println("\n3. Current task status:");
    System.out.println(taskCompletionTracker);

//    4. mark trim hedges as complete
    taskCompletionTracker.put("trim hedges", "complete");

    System.out.println("\n4. Completed trim hedges ...");
    System.out.println(taskCompletionTracker);

//    5. grocery list HashMap
    File groceriesFile = new File(classLoader.getResource("groceries.json").getFile());
    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    Map<String, Integer> groceryList = new HashMap<>();
    groceryList = objectMapper.readValue(groceriesFile, HashMap.class);
    String prettyJson = objectMapper.writeValueAsString(groceryList);
    System.out.println("\n5. Grocery List:");
    System.out.println(prettyJson);

//    6. add bread and milk
    groceryList.put("Loaf of Bread", 1);
    groceryList.put("Gallon of Milk", 1);

    System.out.println("\n6. Added bread and milk:");
    System.out.println(objectMapper.writeValueAsString(groceryList));

//    7. grocery price HashMap
    File groceryPricesFile = new File(classLoader.getResource("grocery_prices.json").getFile());
    Map<String, Integer> groceryPrices = objectMapper.readValue(groceryPricesFile, HashMap.class);

    System.out.println("Grocery Prices:");
    System.out.println(objectMapper.writeValueAsString(groceryPrices));

//    8. calculate grocery prices
    double totalPrice = 0;
    for(String item: groceryList.keySet()) {
      totalPrice += groceryList.get(item) * groceryPrices.get(item);
    }

    System.out.println("\n8. Total Price:");
    System.out.println(totalPrice/100);

//    9. update "got to ATM" to include withdraw amount
    taskCompletionTracker.put("go to the ATM and withdraw $" + totalPrice/100, taskCompletionTracker.get("go to the ATM"));
    taskCompletionTracker.remove("go to the ATM");

    System.out.println("\n9. ATM Withdraw amount:");
    System.out.println(objectMapper.writeValueAsString(taskCompletionTracker));

//    BONUS
//    10. planning run
    File streetsFile = new File(classLoader.getResource("streets.json").getFile());
    Map<String, Double> streets = objectMapper.readValue(streetsFile, HashMap.class);

    System.out.println("Streets:");
    System.out.println(objectMapper.writeValueAsString(streets));

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

    double routeOneDistance = calculateTotalDistance(routeOne, streets);
    System.out.println("\nRoute one distance: " + routeOneDistance);

    double routeTwoDistance = calculateTotalDistance(routeTwo, streets);
    System.out.println("Route two distance: " + routeTwoDistance);
  }

  public static double calculateTotalDistance(List<String> route, Map<String, Double> distances) {
    double totalDistance = 0.0;
    for(String street : route) {
      System.out.println("street: " + street);
      System.out.println("distance: " + distances.get(street));
      totalDistance += distances.get(street);
      System.out.println("updated total distance: " + totalDistance);
    }

    return totalDistance;
  }
}
