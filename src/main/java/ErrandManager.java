import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ErrandManager {

  public static void main(String[] args) throws IOException {
    File todoFile = new File(ErrandManager.class.getResource("todo.txt").getFile());
    Scanner scanner = new Scanner(todoFile);
    List<String> toDoList = new ArrayList<>();

    while (scanner.hasNextLine()) {
      toDoList.add(scanner.nextLine());
    }
    System.out.println("\n---\n" + toDoList + "\n sout for todolist");

    int groceryIndex = toDoList.indexOf("buy groceries");
    toDoList.add(groceryIndex, "Got to ATM");

    System.out.println("\n---\n" + toDoList + "\n buying groceris sout for todolist");

    Map<String, String> tasks = new HashMap<>();

    for (String task: toDoList) {
      tasks.put(task, "pending");
    }

    System.out.println("\n---\n" + tasks + "\n hashmap for tasks and value of pending");

    tasks.replace("trim hedges", "complete");

    System.out.println("\n---\n" + tasks + "\n hashmap trim edges complete");

    Map<String, Integer> groceryList = new HashMap<>();
    byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/groceries.json"));
    ObjectMapper objectMapper = new ObjectMapper();
    groceryList = objectMapper.readValue(jsonData, HashMap.class);
    groceryList.put("Loaf of Bread", 1);
    groceryList.put("Gallon of Milk", 1);
    System.out.println("this is your grocery list" + groceryList);

    Map<String, Integer> groceryPrices = new HashMap<>();
    byte[] jsonPriceData = Files.readAllBytes(Paths.get("src/main/resources/grocery_prices.json"));
    groceryPrices = objectMapper.readValue(jsonPriceData, HashMap.class);
    System.out.println(groceryPrices);

    Set<String> groeceryKeys = groceryPrices.keySet();
    Integer totalPrice = 0;
    for(String key: groeceryKeys){
       totalPrice += groceryPrices.get(key);
    }
    System.out.println("\n---\n"+totalPrice + " -total price");

    tasks.remove("Go to ATM");
    tasks.put("Go to ATM: " + totalPrice, "pending");
    System.out.println("\n---\n"+tasks);
  }

}
