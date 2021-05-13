## Getting Started

```no-highlight
et get java-errand-manager
cd java-errand-manager
idea .
```

## One Saturday morning...

With everything that's been happening at Launch, there's so much to do at home! Because you want to both get in some Java practice, and you want to make some headway on getting organized, you decide you're going to manage your chores with data structures. Follow along below.

1. You've saved your tasks to `todo.txt` - read that file in and load each task into an `ArrayList`.

2. You need to go to the ATM to take out cash to pay for groceries. Add `"go to the ATM"` to the list right before `"buy groceries"`.

3. Whoops! You realize you want to keep track of whether you've completed each task, so using your previously created ArrayList of tasks, create a `HashMap` where the key is the task name and the value starts as `"pending"`.

4. You realize that you were able to trim the hedges last weekend, so mark the `"trim hedges"` task as `"complete"` in the `HashMap`.

5. You need to build a grocery list - read in `groceries.json` and create a new `HashMap` to keep track of everything you have to buy, where the name of the grocery is the key, and the quantity is stored as the value. Be sure to store the values as `Integer` objects.

6. You forgot to add `1` "Loaf of Bread" and `1` "Gallon of Milk" to the grocery list - you don't have time to update the file so just add them to the `HashMap` you created.

7. You need to know how much money to take out of the ATM. You find an old receipt and you've inputted all of the prices into `grocery_prices.json`. You've entered the price of each item in cents. Load `grocery_prices.json` in as a `HashMap` with the appropriate data types for the key and value.

8. Calculate the subtotal for everything you have to buy. Remember to take both the price and quantity into account.

9. Update your `"go to the ATM"` task to include how much money to withdraw.

## (Time Permitting) Planning your run...

1. You need to plan your run and to do so, you've created a `streets.json` to include the distances for the roads on your run. Load that into your script as a `HashMap`.

We want to have different routes we can compare:

2. Create an `ArrayList` to represent one running route and fill it with the following strings.

- "South St"
- "Forest St"
- "Vernon St"
- "South St"

3. Create an `ArrayList` to represent another running route and fill it with the following strings - note that if we run up Sally Way, we have to turn back, so we intentionally list it twice.

- "South St"
- "Forest St"
- "Sally Way"
- "Sally Way"
- "South St"

4. Write a method that takes in an `ArrayList` of the route and a `HashMap` of the streets and distances and returns the total run distance from the streets in the `ArrayList`

5. Invoke this method for both routes and output the distances.
