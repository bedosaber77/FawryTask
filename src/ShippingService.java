import java.util.Map;

public class ShippingService {
    public static void PrintShippingInfo(Map<Shippable,Integer> shippingProducts) {
        if(shippingProducts.size() == 0)
                return;
        double totalWeight = 0;
        System.out.println("** Shipment notice **");
        for (Map.Entry<Shippable,Integer> entry : shippingProducts.entrySet()) {
            Shippable shippable = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println(quantity+"x "+shippable.getName()+"\t"+shippable.getWeight()*quantity);
            totalWeight += shippable.getWeight()*quantity;
        }
        System.out.println("Total package weight "+totalWeight/1000.0+"kg");
    }

    public static int getShippingCost(Map<Shippable,Integer> shippingProducts) {
        int totalQuantity = 0;
        for (Map.Entry<Shippable,Integer> entry : shippingProducts.entrySet()) {
            Integer quantity = entry.getValue();
            totalQuantity += quantity;
        }
        return totalQuantity*10;
    }

}
