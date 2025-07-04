public class ShippableProduct extends SimpleProduct implements Shippable {
    int weight;
    ShippableProduct(String name, int quantity, double price, int weight) {
        super(name,price,quantity);
        this.weight = weight;
    }
    @Override
    public double getWeight() {
        return this.weight;
    }
}
