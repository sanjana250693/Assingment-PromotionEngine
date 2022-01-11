package engine;

import cart.Cart;
import product.Product;
import promotion.ApplyPromotion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PromotionApp {

    public static void main (String[] args) {
        System.out.println("Available promotions in the system:");
        System.out.println("3 of A's for 130");
        System.out.println("2 of B's for 45");
        System.out.println("C & D for 30\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer productQty = 0;
        String[] productsInSystem = {"A", "B", "C", "D"};
        List<Product> itemsInCart = new ArrayList<>();
        Cart cart = new Cart(itemsInCart);
        for (String sku : productsInSystem) {
            cart.getInputFromUser(sku, br, cart);
        }
            //Apply promotion to product in cart.
            ApplyPromotion apply = new ApplyPromotion();
            System.out.println("Grand total = " + apply.applyPromo(cart));
            System.out.println("===============================");
    }
}
