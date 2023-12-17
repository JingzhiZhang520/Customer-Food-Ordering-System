import java.util.HashMap;
import java.util.Map;


import javax.swing.JOptionPane;

public class LoginManager {
    private Map<String, User> users = new HashMap<>();
    private User currentUser;
    private FoodCourtGUI foodCourtGUI; // Reference to the GUI

    public void setFoodCourtGUI(FoodCourtGUI foodCourtGUI) {
        this.foodCourtGUI = foodCourtGUI;
    }

    public void registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            User newUser = new User(username, password);
            users.put(username, newUser);
            showMessage("User registered successfully."); // Show notification using JOptionPane
        } else {
            showMessage("Username already exists. Please choose a different one."); // Show notification using JOptionPane
        }
    }

    public void login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            showMessage("Login successful."); // Show notification using JOptionPane
        } else {
            showMessage("Invalid username or password."); // Show notification using JOptionPane
        }
    }

    public void logout() {
        currentUser = null;
        showMessage("Logout successful."); // Show notification
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Helper method to display notifications using JOptionPane
    private void showMessage(String message) {
        if (foodCourtGUI != null) {
            JOptionPane.showMessageDialog(foodCourtGUI, message);
        }
    }
}
