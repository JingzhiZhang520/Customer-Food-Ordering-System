import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodCourt {
    private List<Menu> menus = new ArrayList<>();
    private Order currentOrder = new Order();
    private Map<MenuItem, String> itemCategoryMap = new HashMap<>();
    private LoginManager loginManager = new LoginManager();
    private Admin admin;

    public FoodCourt() {
        // Initialize the menu
        List<MenuItem> entreesList = List.of(new MenuItem("Hot Dog", 1.50, "hotdog.jpg"), new MenuItem("Chicken Bake", 2.75, "chickenbake.jpg"));
        List<MenuItem> drinksDessertsList = List.of(new MenuItem("Soda", 1.25, "soda.jpg"), new MenuItem("Ice Cream", 2.50, "icecream.jpg"));
        List<MenuItem> pizzaSlicesList = List.of(new MenuItem("Cheese Slice", 2.00, "cheesepizza.jpg"), new MenuItem("Pepperoni Slice", 2.25, "pepperoni.jpg"));
        List<MenuItem> wholePizzasList = List.of(new MenuItem("Whole Cheese Pizza", 10.00, "wholecheese.jpg"), new MenuItem("Whole Pepperoni Pizza", 12.00, "wholepepperoni.jpg"));

        // Create menu implementations
        Menu entreesMenu = new EntreesMenu(entreesList);
        Menu drinksDessertsMenu = new DrinksDessertsMenu(drinksDessertsList);
        Menu pizzaSlicesMenu = new PizzaSlicesMenu(pizzaSlicesList);
        Menu wholePizzasMenu = new WholePizzasMenu(wholePizzasList);

        // Add menu implementations to the list of menus
        menus.add(entreesMenu);
        menus.add(drinksDessertsMenu);
        menus.add(pizzaSlicesMenu);
        menus.add(wholePizzasMenu);

        // Populate the itemCategoryMap
        for (Menu menu : menus) {
            for (MenuItem item : menu.getMenuItems()) {
                itemCategoryMap.put(item, menu.getClass().getSimpleName());
            }
        }

        admin = new Admin(this);
    }

    public void setMenuItemPrice(String categoryName, String itemName, double newPrice) {
        for (Menu menu : menus) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase(categoryName)) {
                for (MenuItem item : menu.getMenuItems()) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        // Update the price of the existing MenuItem object in the list
                        item.setPrice(newPrice);
                        return;
                    }
                }
            }
        }
        System.out.println("Item " + itemName + " not found in category " + categoryName + ".");
    }

    public Map<MenuItem, String> getItemCategoryMap() {
        return itemCategoryMap;
    }

    public Admin getAdmin() {
        return admin;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void registerUser(String username, String password) {
        loginManager.registerUser(username, password);
    }

    public void loginUser(String username, String password) {
        loginManager.login(username, password);
    }

    public void logoutUser() {
        loginManager.logout();;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void removeOrderItem(MenuItem item) {
        currentOrder.removeItem(item);
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public String getCategoryName(MenuItem item) {
        String itemString = item.toString();

        for (Menu menu : menus) {
            for (MenuItem menuItem : menu.getMenuItems()) {
                if (menuItem.toString().equals(itemString)) {
                    return menu.getClass().getSimpleName();
                }
            }
        }
        return "";
    }

    public Menu findMenuByCategory(String category) {
        // Implement logic to find the correct menu based on the category
        // This might involve iterating through the menus and comparing categories
        // You can use the menu.getClass().getSimpleName() to get the category name
        // and compare it with the provided category parameter.

        for (Menu menu : menus) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase(category)) {
                return menu;
            }
        }
        return null; // Return null if the category is not found
    }

    public void removeMenuItem(String categoryName, String itemName) {
        // Find the correct menu based on the category
        Menu targetMenu = findMenuByCategory(categoryName);

        if (targetMenu != null) {
            // Find the item in the menu
            MenuItem itemToRemove = null;
            for (MenuItem item : targetMenu.getMenuItems()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    itemToRemove = item;
                    //System.out.println(itemToRemove + "not found");
                    break;
                }
            }

            // Remove the item if found
            if (itemToRemove != null) {
                //System.out.println(targetMenu.getMenuItems());
                targetMenu.getMenuItems().remove(itemToRemove);
                System.out.println("Item " + itemName + " removed from category " + categoryName + ".");
            } else {
                System.out.println("Item " + itemName + " not found in category " + categoryName + ".");
            }
        } else {
            System.out.println("Category " + categoryName + " not found.");
        }
    }


    public void placeOrder(String categoryName, String itemName) {
        for (Menu menu : menus) {
            if (menu instanceof EntreesMenu && categoryName.equalsIgnoreCase("EntreesMenu")
                    || menu instanceof DrinksDessertsMenu && categoryName.equalsIgnoreCase("DrinksDessertsMenu")
                    || menu instanceof PizzaSlicesMenu && categoryName.equalsIgnoreCase("PizzaSlicesMenu")
                    || menu instanceof WholePizzasMenu && categoryName.equalsIgnoreCase("WholePizzasMenu")) {

                for (MenuItem item : menu.getMenuItems()) {
                    if (item.getName().equalsIgnoreCase(itemName)) {
                        currentOrder.addItem(item);
                        System.out.println("Added " + item.getName() + " to your order.");
                        return;
                    }
                }
            }
        }
        System.out.println("Sorry, " + itemName + " is not on the menu or in the specified category.");
    }

    public void displayOrder() {
        System.out.println("=== Your Order ===");
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("Your order is empty.");
        } else {
            for (Menu menu : menus) {
                System.out.println("Category:");
                for (MenuItem item : menu.getMenuItems()) {
                    long itemCount = currentOrder.getItems().stream().filter(orderItem -> orderItem.getName().equalsIgnoreCase(item.getName())).count();
                    if (itemCount > 0) {
                        System.out.println(item.getName() + " x" + itemCount + ": $" + (item.getPrice() * itemCount));
                    }
                }
            }
            System.out.println("Total: $" + currentOrder.calculateTotal());
        }
    }

    public String getOrderMessage() {
        StringBuilder orderText = new StringBuilder("=== Your Order ===\n");
    
        if (currentOrder.getItems().isEmpty()) {
            orderText.append("Your order is empty.");
        } else {
            for (Menu menu : menus) {
                //orderText.append("Category:\n");
                for (MenuItem item : menu.getMenuItems()) {
                    long itemCount = currentOrder.getItems().stream().filter(orderItem -> orderItem.getName().equalsIgnoreCase(item.getName())).count();
                    if (itemCount > 0) {
                        orderText.append(item.getName()).append(" x").append(itemCount).append(": $").append(item.getPrice() * itemCount).append("\n");
                    }
                }
            }
            orderText.append("Total: $").append(currentOrder.calculateTotal());
        }
    
        return orderText.toString();
    }
}