import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    private FoodCourt foodCourt;
    private FoodCourtGUI foodCourtGUI;

    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemCategoryField;
    private JTextField itemImageField;

    public AdminFrame(FoodCourt foodCourt, FoodCourtGUI foodCourtGUI) {
        this.foodCourt = foodCourt;
        this.foodCourtGUI = foodCourtGUI;

        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createComponents();
    }

    private void createComponents() {
        itemNameField = new JTextField();
        itemPriceField = new JTextField();
        itemCategoryField = new JTextField();
        itemImageField = new JTextField();

        JButton editButton = new JButton("Edit Item");
        editButton.addActionListener(new EditButtonListener());
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new AddButtonListener());
        JButton removeButton = new JButton("Remove Item");
        removeButton.addActionListener(new RemoveButtonListener());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Item Name:"));
        panel.add(itemNameField);
        panel.add(new JLabel("Item Price:"));
        panel.add(itemPriceField);
        panel.add(new JLabel("Item Category:"));
        panel.add(itemCategoryField);
        panel.add(new JLabel("Image Name:"));
        panel.add(itemImageField);
        panel.add(editButton);
        panel.add(addButton);
        panel.add(removeButton);

        add(panel);
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            double newPrice = Double.parseDouble(itemPriceField.getText());
            String category = itemCategoryField.getText();
            
            foodCourt.getAdmin().editMenuItem(category, itemName, newPrice);
            if (foodCourtGUI != null) {
                foodCourtGUI.updateMenuPanel();
                foodCourtGUI.updateOrderTextArea();
            } else {
                System.out.println("FoodCourtGUI is not properly initialized.");
            }
            //foodCourtGUI.updateOrderTextArea();
            //foodCourtGUI.updateMenuPanel();
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            double price = Double.parseDouble(itemPriceField.getText());
            String category = itemCategoryField.getText();

            foodCourt.getAdmin().addMenuItem(category, itemName, price, "default.jpg");
            foodCourtGUI.updateMenuPanel();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            String category = itemCategoryField.getText();

            foodCourt.removeMenuItem(category, itemName);
            foodCourtGUI.updateMenuPanel();
        }
    }
}
