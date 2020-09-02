import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ErrandManager {

  public static void main(String[] args) throws IOException {
    // String pathnameStatic = "src/main/resources/todo.txt";
    // dynamic option:
    String txtFilePathname = ErrandManager.class.getResource("todo.txt").getFile();
    System.out.println(txtFilePathname);

    File txtFile = new File(txtFilePathname);
    // combined version in one line:
//    File txtFile = new File(ErrandManager.class.getResource("todo.txt").getFile());

    Scanner newScanner = new Scanner(txtFile);
    List<String> taskArrayList = new ArrayList<String>();

    while (newScanner.hasNextLine()) {
      taskArrayList.add(newScanner.nextLine());
    }
    int atmGroceryIndex = taskArrayList.indexOf("buy groceries");
    taskArrayList.add(atmGroceryIndex, "go to ATM");
    System.out.println(taskArrayList);

    Map<String, String> taskMap = new HashMap<>();
    for (String task : taskArrayList) {
      taskMap.put(task, "pending");
    }
    System.out.println(taskMap);
    // we could refactor this to be in an abstracted method `updateTaskStatus`
    taskMap.replace("trim hedges", "complete");
//    if (taskMap.replace("trim hedges", "complete") == null) {
//      throw new Exception("key not found");
//    }
    System.out.println(taskMap);

    File groceryList = new File(ErrandManager.class.getResource("groceries.json").getFile());

//    Map<String, Integer> groceries = new HashMap<>();
    Map<String, Integer> groceries;

    ObjectMapper groceriesMapper = new ObjectMapper();
    groceries = groceriesMapper.readValue(groceryList, HashMap.class);

    System.out.println("Grocery List: " + groceries);

    groceries.put("Loaf of Bread", 1);
    groceries.put("Gallon of Milk", 1);

    System.out.println("Updated Grocery List: " + groceries);

    File groceriesPricesFile = new File(ErrandManager.class.getResource("grocery_prices.json").getFile());
    Map<String, Integer> groceriesPriceMap;
    ObjectMapper groceriesPricesMapper = new ObjectMapper();
    groceriesPriceMap = groceriesPricesMapper.readValue(groceriesPricesFile, HashMap.class);
    System.out.println(groceriesPriceMap);

    Integer subtotal = 0;

    // Option one: get all keys and iterate through the keys
//    Set groceryNames = groceries.keySet();
//    for(String groceryName : groceryNames) {
//      int quantity = groceries.get(groceryName);
//      int priceInCents = groceriesPriceMap.get(groceryName);
//      subtotal += quantity * priceInCents;
//    }

    // Option two: iterate through each key-value pair
    for (Map.Entry<String, Integer> grocery: groceries.entrySet()) {
//      System.out.println(grocery);
      int quantity = grocery.getValue();
      String groceryName = grocery.getKey();
      int priceInCents = groceriesPriceMap.get(groceryName);
      subtotal += quantity * priceInCents;
      // lines 78-81 can be combined to:
//      subtotal += grocery.getValue() * groceriesPriceMap.get(grocery.getKey());
    }

    // Option three: forEach
//    groceries.forEach((name, quantity) -> {
//      subtotal += quantity * groceriesPriceMap.get(name);
//    });
    System.out.println("Subtotal: " + subtotal);

    taskMap.remove("go to ATM");
    String newKey = "go to ATM and withdraw " + subtotal + " cents";
    taskMap.put(newKey, "pending");
    System.out.println("Updated taskMap: " + taskMap);
  }
}
