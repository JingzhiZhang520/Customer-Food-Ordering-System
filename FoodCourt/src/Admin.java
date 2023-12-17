public class Admin {
    private FoodCourt foodCourt;
    private FoodCourtGUI foodCourtGUI;
    

    public Admin(FoodCourt foodCourt, FoodCourtGUI foodCourtGUI) {
        this.foodCourt = foodCourt;
        this.foodCourtGUI = foodCourtGUI;
    }

    public Admin(FoodCourt foodCourt) {
        this.foodCourt = foodCourt;
    }

    public Admin(FoodCourtGUI foodCourtGUI) {
        this.foodCourtGUI = foodCourtGUI;
    }

    public void loginAsAdmin(String username, String password) {
        // Simulate admin login logic
        if ("admin".equals(username) && "adminPassword".equals(password)) {
            AdminFrame adminFrame = new AdminFrame(foodCourt, foodCourtGUI);
            adminFrame.setVisible(true);
        } else {
            // Handle incorrect login
            System.out.println("Incorrect admin credentials");
        }
    }

    public void editMenuItem(String categoryName, String itemName, double newPrice) {
        foodCourt.setMenuItemPrice(categoryName, itemName, newPrice);
        
        //System.out.println("Item " + itemName + " not found in category " + categoryName + ".");
    }

    public void addMenuItem(String categoryName, String itemName, double price, String imageName) {
        for (Menu menu : foodCourt.getMenus()) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase(categoryName)) {
                MenuItem newItem = new MenuItem(itemName, price, imageName);
                menu.getMenuItems().add(newItem);
                foodCourt.getItemCategoryMap().put(newItem, categoryName);
                System.out.println("Item " + itemName + " added to category " + categoryName + ".");
                return;
            }
        }
        System.out.println("Category " + categoryName + " not found.");
    }

}

