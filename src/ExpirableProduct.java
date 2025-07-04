import java.time.LocalDate;
import java.util.Date;

public class ExpirableProduct extends SimpleProduct implements Expirable {
    LocalDate expiry;

    ExpirableProduct(String name, int price, int quantity, LocalDate expiry) {
        super(name,price,quantity);
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
