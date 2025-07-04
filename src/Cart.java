import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> products;
    public Cart() {
        this.products = new HashMap<Product,Integer>();
    }
    public void addProduct(Product product,Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if(product.getQuantity() >= quantity)
        {
            int currentQuantity = products.getOrDefault(product, 0);
            int newTotalQuantity = currentQuantity + quantity;

            if (product.getQuantity() >= newTotalQuantity) {
                products.put(product, newTotalQuantity);
            }
            else {
                throw new IllegalArgumentException("Total quantity exceeds available stock");
            }
        }
        else
            throw new IllegalArgumentException("Quantity exceeds the maximum quantity");
    }
    public Map<Product, Integer> getProducts() {
        return products;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
