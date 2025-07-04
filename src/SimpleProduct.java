public class SimpleProduct implements Product {
    String name;
    double price;
    int quantity;
    public SimpleProduct(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public int getQuantity() {
        return quantity;
    }
    @Override
    public void reduceQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
