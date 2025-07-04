import java.time.LocalDate;
import java.util.Date;

public class ShippableExpirableProduct extends ShippableProduct implements Shippable, Expirable{
    private LocalDate expiry;

    ShippableExpirableProduct(String name, double price, int quantity, int weight, LocalDate expiry) {
        super(name,quantity,price,weight);
        this.expiry = expiry;
    }

    @Override
    public boolean isExpired() {
        return this.expiry.isBefore(LocalDate.now());
    }

    @Override
    public LocalDate getExpiryDate() {
        return this.expiry;
    }
}
