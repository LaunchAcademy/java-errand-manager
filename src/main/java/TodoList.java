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
import java.util.Set;

public class TodoList {
  private static List<String> todoList = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    // Step 1
    ClassLoader classLoader = TodoList.class.getClassLoader();
    File todoFile = new File(classLoader.getResource("todo.txt").getFile());

    Scanner scanner = new Scanner(todoFile);
    while (scanner.hasNextLine()) {
      todoList.add(scanner.nextLine());
    }
    System.out.println("Read todo.txt into ArrayList");
    System.out.println(todoList);

    // Step 2

    // here, we're hard-coding the location -- so we wouldn't really want to do this
//    todoList.add(2, "go to the ATM");
    // here, we're dynamically finding the index of "buy groceries" in case it changes positions
    todoList.add(todoList.indexOf("buy groceries"), "go to the ATM");
    System.out.println("Add buy groceries");
    System.out.println(todoList);

    // Step 3

    Map<String, String> taskMap = new HashMap<>();
    for(String task : todoList) {
      taskMap.put(task, "pending");
    }
    System.out.println("Create HashMap with pending statuses");
    System.out.println(taskMap);

    // Step 4
    // trying to change "trim hedges" to complete
    taskMap.put("trim hedges", "complete");
    System.out.println("Mark trim hedges as complete");
    System.out.println(taskMap);


    // Step 5
    Map<String, Integer> groceriesMap = new HashMap<>();

    File groceriesJson = new File(TodoList.class.getResource("groceries.json").getFile());
//    File groceriesJson = new File(classLoader.getResource("groceries.json").getFile());

    // Create objectMapper
//    ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    ObjectMapper objectMapper = new ObjectMapper();

    groceriesMap = objectMapper.readValue(groceriesJson, HashMap.class);

    System.out.println("Read groceries.json in as a HashMap");
    System.out.println(groceriesMap);

    // Step 6
    //Add 1 "Loaf of Bread" and 1 "Gallon of Milk"

    groceriesMap.put("Loaf of Bread", 1);
    groceriesMap.put("Gallon of Milk", 1);
    System.out.println("Add bread and milk to groceries");
    System.out.println(groceriesMap);

    //Step 7
    Map<String, Integer> priceMap = new HashMap<>();
    File groceryPrices = new File(TodoList.class.getResource("grocery_prices.json").getFile());
    priceMap = objectMapper.readValue(groceryPrices,HashMap.class);
    System.out.println("Prices:");
    System.out.println(priceMap);

    // what tool to use?
    // txt file == Scanner
    // json file == ObjectMapper

    //Step 8

    int totalPrice= 0;
    //need QTY * price
    // groceriesMap will have qty
    //priceMap will have price

    // we use `.keySet()` to grab a set of the names of the groceries we want to buy
    // we specifically look at `groceriesMap` (NOT `priceMap`) so that we only get the ones we're trying to buy
    Set<String> groceriesToBuy = groceriesMap.keySet();
    System.out.println(groceriesToBuy );
    // we loop through each grocery we want to buy
    for (String item : groceriesToBuy){
      // get the quantity from groceriesMap
     int qty = groceriesMap.get(item);
     // get the price from priceMap
     int price= priceMap.get(item);
     // we calculate the totalItemPrice by multiplying
     int totalItemPrice= qty * price;
      System.out.println("Total price for "+ item + " is: " + totalItemPrice);
     // we add the totalItemPrice to our entire price
     totalPrice += totalItemPrice;
    }
    System.out.println("Total Price: " + totalPrice + " cents");
    Double priceInDollars = totalPrice / 100.00;
    System.out.println("Total price in dollars: $" + priceInDollars);

//    taskMap.put("go to the ATM and withdraw " + totalPrice + " cents", "pending");
//    taskMap.put("go to the ATM and withdraw $" + priceInDollars, "pending");
    taskMap.put("go to the ATM and withdraw $" + priceInDollars, taskMap.get("go to the ATM"));
    System.out.println(taskMap);
    taskMap.remove("go to the ATM");
    System.out.println(taskMap);




  }

}
