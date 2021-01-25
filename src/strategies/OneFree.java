package strategies;

import shop.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OneFree implements strategies.Discount {

    @Override
    public BigDecimal discount(ArrayList<ShoppingCartItem> items) {

        var sum = BigDecimal.ZERO;
        int quantity = 0;
        var cheapest = items.get(0).itemCost();


        //Loopar för billigaste varan
        for (var item: items) {
            for (int i = 0; i < item.quantity(); i++) {
                quantity++;
                sum = item.itemCost().add(sum);

                if (item.itemCost().intValue() < cheapest.intValue()) {
                    cheapest = item.itemCost();
                }
            }
        }
        //Bestämmer antalet varor för att uppnå rabatten
        if (quantity > 4) {
            sum = sum.subtract(cheapest);
        }

        return sum;
    }
}
