public class MenuItem {
    private String name;
    private double price;
    private String imageName; // New field for image file name

    public MenuItem(String name, double price, String imageName) {
        this.name = name;
        this.price = price;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

}
