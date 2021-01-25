package strategies;

import shop.ShoppingCartItem;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ThreeForTwo implements strategies.Discount {

    @Override
    public BigDecimal discount(ArrayList<ShoppingCartItem> items) {
        var sum = BigDecimal.ZERO;
        int quantity = 0;
        BigDecimal cheapest = items.get(0).itemCost();

        for (var item: items) {
            for (int i = 0; i < item.quantity(); i++) {
                quantity++;
                // sum = item.itemCost().multiply(BigDecimal.valueOf(item.quantity())).add(sum);
                sum = item.itemCost().add(sum);

                if (item.itemCost().intValue() < cheapest.intValue()) {
                    cheapest = item.itemCost();
                }
            }
        }
        if (quantity > 2) {
            sum = sum.subtract(cheapest);
        }
        System.out.println(sum);
        return sum;
    }
}
