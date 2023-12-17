import java.util.List;
import java.util.ArrayList;

interface Menu {
    List<MenuItem> getMenuItems();
}

// Implementation of Menu for Entrees category
class EntreesMenu implements Menu {
    private List<MenuItem> entrees = new ArrayList<>();

    public EntreesMenu(List<MenuItem> entrees) {
        this.entrees.addAll(entrees);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return entrees;
    }
    
}

// Implementation of Menu for Drinks and Desserts category
class DrinksDessertsMenu implements Menu {
    private List<MenuItem> drinksDesserts = new ArrayList<>();

    public DrinksDessertsMenu(List<MenuItem> drinksDesserts) {
        this.drinksDesserts.addAll(drinksDesserts);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return drinksDesserts;
    }
}

// Implementation of Menu for Pizza Slices category
class PizzaSlicesMenu implements Menu {
    private List<MenuItem> pizzaSlices = new ArrayList<>();

    public PizzaSlicesMenu(List<MenuItem> pizzaSlices) {
        this.pizzaSlices.addAll(pizzaSlices);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return pizzaSlices;
    }
}

// Implementation of Menu for Whole Pizzas category
class WholePizzasMenu implements Menu {
    private List<MenuItem> wholePizzas = new ArrayList<>();

    public WholePizzasMenu(List<MenuItem> wholePizzas) {
        this.wholePizzas.addAll(wholePizzas);
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return wholePizzas;
    }
}
