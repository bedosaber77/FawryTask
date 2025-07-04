import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void checkout(Customer customer,Cart cart) {
        if(cart.isEmpty())
            throw new IllegalArgumentException("Can't Checkout Empty Cart");
        double totalPrice = 0;
        Map<Shippable,Integer> shippingProducts = new HashMap<Shippable,Integer>();
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()){
            totalPrice += entry.getValue()*entry.getKey().getPrice();
            if(entry.getKey() instanceof Shippable)
                shippingProducts.put((Shippable)entry.getKey(), entry.getValue());
        }

        int shippingCost = ShippingService.getShippingCost(shippingProducts);
        if(customer.getBalance() < totalPrice+shippingCost){
            throw new IllegalArgumentException("Can't Checkout Not Enough Money");
        }


        for (Map.Entry<Product,Integer> entry : cart.getProducts().entrySet()){
            if(entry.getKey().getQuantity() < entry.getValue()){
                throw new IllegalArgumentException("Can't Checkout Not Enough Stock");
            }
            if(entry.getKey() instanceof Expirable && ((Expirable)entry.getKey()).isExpired()){
                throw new IllegalArgumentException("Can't Checkout Expired Product");
            }
        }

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            entry.getKey().reduceQuantity(entry.getValue());
        }

        customer.reduceBalance(totalPrice+shippingCost);
        ShippingService.PrintShippingInfo(shippingProducts);

        System.out.println("\n** Checkout receipt **");

        for (Map.Entry<Product,Integer> entry : cart.getProducts().entrySet()){
            int quantity = entry.getValue();
            Product product = entry.getKey();

            System.out.println(quantity+"x "+product.getName()+'\t'+ product.getPrice()*quantity);

        }

        System.out.println("-----------------");
        System.out.println("Subtotal      "+totalPrice);
        System.out.println("Shipping      "+shippingCost);

        System.out.println("Amount       "+(totalPrice+shippingCost));
    }

    public static void main(String[] args) {


        Product cheese = new ShippableExpirableProduct("Cheese", 100, 5, 200, LocalDate.now().plusDays(30));
        Product tv = new ShippableProduct("TV", 500, 3000, 5000);
        Product biscuits = new ExpirableProduct("Biscuits", 75, 10, LocalDate.now().plusDays(7));
        Product scratchCard = new SimpleProduct("Mobile Scratch Card", 25, 20);

        Customer customer = new Customer(1, "John Doe", 3500);

        // Test Case 1: Successful checkout
        System.out.println("=== Test Case 1: Successful Checkout ===");
        try {
            Cart cart = new Cart();
            cart.addProduct(cheese, 2);
            cart.addProduct(tv, 1);
            cart.addProduct(scratchCard, 1);

            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 2: Empty Cart ===");
        try {
            Cart emptyCart = new Cart();
            checkout(customer, emptyCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 3: Insufficient Balance ===");
        try {
            Customer poorCustomer = new Customer(2, "Poor Customer", 50);
            Cart expensiveCart = new Cart();
            expensiveCart.addProduct(tv, 2);

            checkout(poorCustomer, expensiveCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 4: Insufficient Stock ===");
        try {
            Cart cart = new Cart();
            cart.addProduct(cheese, 10); // Only 5 available (after previous purchase)

            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 5: Expired Product ===");
        try {
            Product expiredCheese = new ShippableExpirableProduct("Expired Cheese", 100, 5, 200, LocalDate.now().minusDays(1));
            Cart cart = new Cart();
            cart.addProduct(expiredCheese, 1);

            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=== Test Case 6: Only Non-Shippable Items ===");
        try {
            Cart cart = new Cart();
            cart.addProduct(scratchCard, 3);
            cart.addProduct(biscuits, 2);

            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}