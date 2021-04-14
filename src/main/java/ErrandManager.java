import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ErrandManager {

  public static void main(String[] args) throws FileNotFoundException {
    File originalFile = new File("/Users/tran/Desktop/Launch Academy/Curriculum/repos/java-errand-manager/src/main/resources/todo.txt");
    Scanner scanner = new Scanner(originalFile);
    List<String> taskToDo = new ArrayList<>();
    while (scanner.hasNextLine()) {
      taskToDo.add(scanner.nextLine());
    }

    taskToDo.add(taskToDo.indexOf("buy groceries"),"go to ATM");
    System.out.println(taskToDo);
    Map<String, String> toDoList = new HashMap<>();
    for(String task : taskToDo) {
      toDoList.put(task, "pending");

    }
    System.out.println(toDoList);
    // find the item in the map
//    toDoList.put("trim hedges", "complete");
    toDoList.replace("trim hedges", "complete");
    System.out.println(toDoList);

    //read in groceries.json
    //create a hashMap for a grocery list (groceryName, int)
    File groceryFile = new File("/Users/tran/Desktop/Launch Academy/Curriculum/repos/java-errand-manager/src/main/resources/groceries.json");
    Map <String, Integer> groceryMap = new HashMap<String, Integer>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      groceryMap = mapper.readValue(groceryFile, HashMap.class);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    System.out.println(groceryMap);

    groceryMap.put("Loaf of Bread", 1);
    groceryMap.put("Gallon of Milk", 1);
    System.out.println(groceryMap);

    System.out.println("====Step 7=====");

    File pricesFile = new File("/Users/tran/Desktop/Launch Academy/Curriculum/repos/java-errand-manager/src/main/resources/grocery_prices.json");
    Map<String, Integer> groceryPrices = new HashMap<>();
    try {
      groceryPrices = mapper.readValue(pricesFile, HashMap.class);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    System.out.println(groceryPrices);

    int total = 0;
    Set<String> items = groceryPrices.keySet();
    for (String item : items) {
      total += groceryMap.get(item) * groceryPrices.get(item);
    }

//    for(Map.Entry<String, Integer> itemsEntry : groceryPrices.entrySet()) {
//      String key = itemsEntry.getKey();
//      Integer value = itemsEntry.getValue();
//      total += groceryMap.get(key) * value;
//    }


    System.out.println("Total price in cent: " + total);
    System.out.println(toDoList);
    toDoList.remove("go to ATM");

    toDoList.put("withdraw from ATM: $" + (double)total/100, "pending");
    System.out.println(toDoList);

    //Load run route as HashMap
    Map<String, Double> streetsHash = new HashMap<String, Double>();
    try {
      byte[] mapData = Files.readAllBytes(Paths.get("/Users/tran/Desktop/Launch Academy/Curriculum/repos/java-errand-manager/src/main/resources/streets.json"));
      streetsHash = mapper.readValue(mapData, HashMap.class);
    } catch(IOException io) {
      io.printStackTrace();
    }
    System.out.println(streetsHash);

   List<String> option1 = new ArrayList<>();
   option1.add("South St");
    option1.add("Forest St");
    option1.add("Vernon St");
    option1.add("South St");

    List<String> option2 = new ArrayList<>();
    option2.add("South St");
    option2.add("Forest St");
    option2.add("Sally Way");
    option2.add("Sally Way");
    option2.add("South St");

    System.out.println("Route 1: " + runDistance(option1, streetsHash));
    System.out.println("Route 2: " + runDistance(option2, streetsHash));
  }

  public static double runDistance(List<String> listRun, Map<String, Double> streetDistance) {
    double counter = 0.0;
    for (String route : listRun) {
      counter += streetDistance.get(route);
    }
    return counter;
  }

}
