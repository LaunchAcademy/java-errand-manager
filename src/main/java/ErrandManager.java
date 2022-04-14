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
    ClassLoader classLoader = ErrandManager.class.getClassLoader();
    File toDo = new File(classLoader.getResource("todo.txt").getFile());
    Scanner scanner = new Scanner(toDo);

    //step 1
    List<String> toDoList = new ArrayList<>();
    while (scanner.hasNextLine()) {
      toDoList.add(scanner.nextLine());
    }

    //step 2
    int index = toDoList.indexOf("buy groceries");
    toDoList.add(index, "go to the ATM");

    //step 3
    HashMap<String, String> toDoMap = new HashMap<>();
    for (String chore : toDoList) {
      toDoMap.put(chore, "pending");
    }

    //step 4
    toDoMap.replace("trim hedges", "complete");

    //step 5
    File groceriesJson = new File(classLoader.getResource("groceries.json").getFile());
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Integer> groceryMap = new HashMap<>();
    groceryMap = objectMapper.readValue(groceriesJson, HashMap.class);

    //step 6
    groceryMap.put("Loaf of Bread", 1);
    groceryMap.put("Gallon of Milk", 1);

    //step 7
    File groceryPricesJson = new File(classLoader.getResource("grocery_prices.json").getFile());
    HashMap<String, Integer> groceryPrices = new HashMap<>();
    groceryPrices = objectMapper.readValue(groceryPricesJson, HashMap.class);

    //step 8
    int total = 0;
    Set<String> groceries = groceryMap.keySet();
    for (String grocery : groceries) {
      int price = groceryPrices.get(grocery);
      int quantity = groceryMap.get(grocery);
      total += price * quantity;
    }

//    System.out.println(total);

    //step 9
    double dollarTotal = (double) total / 100;
//    String stringTotal = String.valueOf(dollarTotal);
    toDoMap.remove("go to the ATM");
    toDoMap.put("go to the ATM withdraw $" + dollarTotal, "pending");

//    System.out.println(toDoList);
//    System.out.println(toDoMap);
    //System.out.println(groceryMap);
    //System.out.println(groceryPrices);

    // Part 2
    // step 1
    File streetsJson = new File(classLoader.getResource("streets.json").getFile());
    HashMap<String, Double> streetDistances = new HashMap<>();
    streetDistances = objectMapper.readValue(streetsJson, HashMap.class);
    System.out.println(streetDistances);

    // step 2
    List<String> runningRoute = new ArrayList<>();
    runningRoute.add("South St");
    runningRoute.add("Forest St");
    runningRoute.add("Vernon St");
    runningRoute.add("South St");

    // step 3
    ArrayList<String> secondRunningRoute = new ArrayList<>();
    secondRunningRoute.add("South St");
    secondRunningRoute.add("Forest St");
    secondRunningRoute.add("Sally Way");
    secondRunningRoute.add("Sally Way");
    secondRunningRoute.add("South St");

    System.out.println(totalDistance(runningRoute, streetDistances));
    System.out.println(totalDistance(secondRunningRoute, streetDistances));


  }

  // step 4
  public static double totalDistance(List<String> route, Map<String, Double> distanceByStreet) {
    double totalDistance = 0.0;
    for (String street : route) {
      double streetDistance = distanceByStreet.get(street);
      totalDistance += streetDistance;
    }
    return totalDistance;
  }
}
