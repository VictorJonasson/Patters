import command.CommandManager;
import shop.Product;
import shop.ShoppingCart;
import shop.ShoppingCartItem;
import strategies.*;

public class main {

    public static void main(String[] args) {
        CommandManager manager = new CommandManager();
        ShoppingCart shoppingCart = new ShoppingCart(manager);

        //Skapa produkter
        ShoppingCartItem item1 = new ShoppingCartItem(new Product("Geforce  3080"), 100, 3, manager);
        ShoppingCartItem item2 = new ShoppingCartItem(new Product("Fet skärm"), 100, 1, manager);
        ShoppingCartItem item3 = new ShoppingCartItem(new Product("Laxkotletter"), 100, 1, manager);

        //Lägg till i korg
        shoppingCart.addCartItem(item1);
        shoppingCart.addCartItem(item2);
        shoppingCart.addCartItem(item3);



        //Aktivera vilka rabatter du vill test

            shoppingCart.addDiscount(new OneFree());

            //Måste vara minst 4 produkter för "tre för två"
            shoppingCart.addDiscount(new ThreeForTwo());
            shoppingCart.addDiscount(new Sum());



        //Printar Kvitto
        System.out.println(shoppingCart.receipt());
    }
}
