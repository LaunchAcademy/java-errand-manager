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
public class ErrandManager {
  public static void main(String[] args) throws IOException {
    List<String> toDoList = new ArrayList<String>();
    File toDo = new File("src/main/resources/todo.txt");
    Scanner toDoScanner = new Scanner(toDo);
    while (toDoScanner.hasNextLine()) {
      toDoList.add(toDoScanner.nextLine());
    }
    toDoScanner.close();
    System.out.println(toDoList);
    int buyGroceriesIndex = toDoList.indexOf("buy groceries");
    toDoList.add(buyGroceriesIndex, "Take out cash for groceries");
    System.out.println(toDoList);
    Map<String, String> checkingList = new HashMap<>();
    for (String task : toDoList) {
      checkingList.put(task, "pending");
    }
    System.out.println(checkingList);
    checkingList.put("trim hedges", "complete");
    System.out.println(checkingList);
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Integer> groceriesMap = new HashMap<>();
    try {
      byte[] groceriesJson = Files.readAllBytes(Paths.get("src/main/resources/groceries.json"));
      groceriesMap = mapper.readValue(groceriesJson, HashMap.class);
    } catch (IOException io) {
      io.printStackTrace();
    }
    System.out.println(groceriesMap);
    groceriesMap.put("Loaf of Bread", 1);
    groceriesMap.put("Gallon of Milk", 1);
    System.out.println("groceries needed " + groceriesMap);
    Map<String, Integer> groceriesPriceMap = new HashMap<>();
    try {
      byte[] pricesJson = Files.readAllBytes(Paths.get("src/main/resources/grocery_prices.json"));
      groceriesPriceMap = mapper.readValue(pricesJson, HashMap.class);
    } catch (IOException io) {
      io.printStackTrace();
    }
    System.out.println("prices " + groceriesPriceMap);
    int totalCost = 0;
    for (String item : groceriesMap.keySet()) {
      int qty = groceriesMap.get(item);
      int price = groceriesPriceMap.get(item);
      totalCost += qty * price;
    }
    double niceTotal = (totalCost / 100.0);
    System.out.println("$" + niceTotal);
    System.out.println("(╮°-°)╮┳━━┳ ( ╯°□°)╯ ┻━━┻");
    toDoList.set(buyGroceriesIndex, "Take out $" + niceTotal + " cash for groceries");
    System.out.println(toDoList);
    checkingList.remove("Take out cash for groceries");
    //this is using the placeholder method %.2f
    checkingList.put(String.format("Take out $%.2f", niceTotal), "pending");
    System.out.println(checkingList);
    Map<String, Double> runningRoute = new HashMap<>();
    try {
      byte[] routeJson = Files.readAllBytes(Paths.get("src/main/resources/streets.json"));
      runningRoute = mapper.readValue(routeJson, HashMap.class);
    } catch (IOException io) {
      io.printStackTrace();
    }
    System.out.println("Running route: " + runningRoute);
    List<String> pathList1 = new ArrayList<>();
    pathList1.add("South St");
    pathList1.add("Forest St");
    pathList1.add("Vernon St");
    pathList1.add("South St");
    double path1total = getTotalDistance(pathList1, runningRoute);
    List<String> pathList2 = new ArrayList<>(
        List.of("South St", "Forest St", "Sally Way", "Sally Way", "South St"));
    double path2total = getTotalDistance(pathList2, runningRoute);
    System.out.println("Path 1: " + path1total);
    System.out.println("Path 2: " + path2total);
    System.out.println("(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧ good job Lisa");
  }
  private static double getTotalDistance(List<String> pathList, Map<String, Double> pathMap) {
    double pathDistance = 0.0;
    for (String path : pathList) {
      pathDistance += pathMap.get(path);
    }
    return pathDistance;
  }
}
