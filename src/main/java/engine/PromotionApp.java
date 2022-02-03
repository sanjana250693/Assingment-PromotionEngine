package engine;

import cart.Cart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import product.Product;
import product.PromotionUtil;
import promotion.ApplyPromotion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The PromotionApp program implements an application that implement a simple
 * promotion engine for a checkout process and outputs the grand total of the cart.
 *
 * @author  Sanjana TG
 */
public class PromotionApp {
    private static Log log = LogFactory.getLog(PromotionApp.class);
    public static void main (String[] args) {
        PromotionUtil property = new PromotionUtil();
        log.info("Available promotions in the system:");
        String promotionsAvailable = property.getPromotionUtil().getProperty("promotions.in.system");
        log.info(promotionsAvailable+"\n");

        String productSkus = property.getPromotionUtil().getProperty("products.in.system");
        String[] productsInSystem = productSkus.split(",");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Product> itemsInCart = new ArrayList<>();
        Cart cart = new Cart(itemsInCart);
        for (String sku : productsInSystem) {
            cart.getInputFromUser(sku.trim(), br, cart);
        }
            //Apply promotion to product in cart.
            ApplyPromotion apply = new ApplyPromotion();
            BigDecimal grandTotal = apply.applyPromo(cart);
            log.info("Grand total = " + grandTotal);
    }
}