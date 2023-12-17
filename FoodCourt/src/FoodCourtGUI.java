import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


class FoodCourtGUI extends JFrame {

    private FoodCourt foodCourt;
    private JTextArea orderTextArea;
    private JPanel menuPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel logoLabel;
    private JTextArea instructionsTextArea;
    

    public FoodCourtGUI() {
        foodCourt = new FoodCourt();
        foodCourt.getLoginManager().setFoodCourtGUI(this);
        
        setTitle("Food Court");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();
        
    }

    private void createComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Menu Panel
        menuPanel = new JPanel(new GridLayout(0, 2));
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu"));
        menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(menuScrollPane, BorderLayout.WEST);

        menuScrollPane.setViewportView(menuPanel);

        // Order Panel
        JPanel orderPanel = new JPanel();
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order"));
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        mainPanel.add(orderPanel, BorderLayout.CENTER);
        // Big logo
        ImageIcon originalLogoIcon = new ImageIcon(getClass().getResource("logo.jpg")); // Adjust the logo file name
        Image originalLogoImage = originalLogoIcon.getImage();
        Image resizedLogoImage = originalLogoImage.getScaledInstance(350, 150, Image.SCALE_SMOOTH); // Adjust the size as needed
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogoImage);
        logoLabel = new JLabel(resizedLogoIcon);
        orderPanel.add(logoLabel);
        // Order instructions
        instructionsTextArea = new JTextArea("Please tap your selection to begin your order.");
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setPreferredSize(new Dimension(300, 50)); // Adjust the size as needed
        orderPanel.add(instructionsTextArea);

        orderPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space

        orderTextArea = new JTextArea();
        orderTextArea.setPreferredSize(new Dimension(100, 100)); // Adjust the size as needed
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        scrollPane.setPreferredSize(new Dimension(100, 800)); // Adjust the size as needed
        orderPanel.add(scrollPane, BorderLayout.SOUTH);

        //Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login / Register"));
        loginPanel.setLayout(new GridLayout(2,2,5,5));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        loginPanel.add(loginButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new LogoutButtonListener());
        loginPanel.add(logoutButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        loginPanel.add(registerButton);

        // Set preferred size for the login panel
        loginPanel.setPreferredSize(new Dimension(250, 200));

        add(loginPanel, BorderLayout.SOUTH);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton viewOrderButton = new JButton("View Order");
        viewOrderButton.addActionListener(new ViewOrderButtonListener());
        controlPanel.add(viewOrderButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new CheckoutButtonListener());
        controlPanel.add(checkoutButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitButtonListener());
        controlPanel.add(exitButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);

        for (Menu menu : foodCourt.getMenus()) {
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
            JLabel categoryLabel = new JLabel("<html><b>" + menu.getClass().getSimpleName() + "</b></html>");
            categoryPanel.add(categoryLabel);
    
            for (MenuItem item : menu.getMenuItems()) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
    
                // Load and display image
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(item.getImageName()));
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                JLabel imageLabel = new JLabel(resizedIcon);
                itemPanel.add(imageLabel);
    
                JButton itemButton = new JButton("<html><center>" + item.getName() + "<br>$" + item.getPrice() + "</center></html>");
                itemButton.addActionListener(new OrderButtonListener(item));
                itemPanel.add(itemButton);
    
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new RemoveButtonListener(item));
                itemPanel.add(removeButton);
    
                JComboBox<Integer> quantityComboBox = new JComboBox<>(getQuantityArray());
                itemPanel.add(quantityComboBox);
    
                categoryPanel.add(itemPanel);
            }
    
            menuPanel.add(categoryPanel);
    }
    add(mainPanel);
}

public JPanel getYourMenuPanel() {
    return menuPanel;
}

public void updateMenuPanel() {
    JPanel menuPanel = getYourMenuPanel(); // Replace with the actual method to get your menuPanel

        // Remove all components from the menuPanel
        menuPanel.removeAll();

        // Iterate through menus and recreate the menuPanel
        for (Menu menu : foodCourt.getMenus()) {
            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
            JLabel categoryLabel = new JLabel("<html><b>" + menu.getClass().getSimpleName() + "</b></html>");
            categoryPanel.add(categoryLabel);

            for (MenuItem item : menu.getMenuItems()) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

                // Load and display image
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(item.getImageName()));
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                JLabel imageLabel = new JLabel(resizedIcon);
                itemPanel.add(imageLabel);

                JButton itemButton = new JButton("<html><center>" + item.getName() + "<br>$" + item.getPrice() + "</center></html>");
                itemButton.addActionListener(new OrderButtonListener(item));
                itemPanel.add(itemButton);

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new RemoveButtonListener(item));
                itemPanel.add(removeButton);

                JComboBox<Integer> quantityComboBox = new JComboBox<>(getQuantityArray());
                itemPanel.add(quantityComboBox);

                categoryPanel.add(itemPanel);
            }

            menuPanel.add(categoryPanel);
        }

        // Revalidate and repaint the menuPanel
        menuPanel.revalidate();
        menuPanel.repaint();
}

private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        foodCourt.getAdmin().loginAsAdmin(username, password);
        updateOrderTextArea();
    }
}


    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            foodCourt.logoutUser();
            updateOrderTextArea();
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            foodCourt.registerUser(username, password);
            updateOrderTextArea();
        }
    }

    private class OrderButtonListener implements ActionListener {
        private MenuItem item;

        public OrderButtonListener(MenuItem item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component source = (Component) e.getSource();
            Container parent = source.getParent();
    
            if (parent instanceof JPanel) {
                JPanel itemPanel = (JPanel) parent;
                JComboBox<Integer> comboBox = findComboBox(itemPanel);
    
                if (comboBox != null) {
                    int quantity = (int) comboBox.getSelectedItem();
    
                    for (int i = 0; i < quantity; i++) {
                        foodCourt.placeOrder(foodCourt.getCategoryName(item), item.getName());
                    }
    
                    updateOrderTextArea();
                }
            }
        }

        private JComboBox<Integer> findComboBox(Container container) {
            for (Component component : container.getComponents()) {
                if (component instanceof JComboBox) {
                    return (JComboBox<Integer>) component;
                }
            }
            return null;
        }

    }

    private class ViewOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String orderMessage = foodCourt.getOrderMessage();
        JOptionPane.showMessageDialog(null, orderMessage, "Order Details", JOptionPane.INFORMATION_MESSAGE);
        updateOrderTextArea();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        private MenuItem item;
    
        public RemoveButtonListener(MenuItem item) {
            this.item = item;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            foodCourt.removeOrderItem(item);
            updateOrderTextArea();
        }
    }

    private class CheckoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double total = foodCourt.getCurrentOrder().calculateTotal();
            JOptionPane.showMessageDialog(null, "Total amount: $" + total, "Checkout", JOptionPane.INFORMATION_MESSAGE);
            // You may want to perform additional actions related to checkout here
            foodCourt.getCurrentOrder().clearOrder(); // Clear the order after checkout
            updateOrderTextArea();
        }
    }


    private class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public void updateOrderTextArea() {
        StringBuilder orderText = new StringBuilder();
        for (Menu menu : foodCourt.getMenus()) {
            for (MenuItem item : menu.getMenuItems()) {
                long itemCount = foodCourt.getCurrentOrder().getItems().stream().filter(orderItem -> orderItem.getName().equalsIgnoreCase(item.getName())).count();
                if (itemCount > 0) {
                    orderText.append(item.getName()).append(" x").append(itemCount).append(": $").append(item.getPrice() * itemCount).append("\n");
                }
            }
        }
        orderText.append("Total: $").append(foodCourt.getCurrentOrder().calculateTotal());
        orderTextArea.setText(orderText.toString());
    }

    private Integer[] getQuantityArray() {
        List<Integer> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(i);
        }
        return quantities.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FoodCourtGUI gui = new FoodCourtGUI();
            gui.setVisible(true);
        });
    }
}