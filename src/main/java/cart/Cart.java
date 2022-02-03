package cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import product.Product;
import product.PromotionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
/**
 * This class is used to get the qty details from the user
 * and validate them.
 *
 * @author Sanjana TG
 */
@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private static Log log = LogFactory.getLog(Cart.class);
    private List<Product> itemsInCart;

    /**
     * This method is used to get the qty details from the user
     * and validate quantity.
     * @param sku - product sku
     * @param br - input reader
     * @param cart - shopping cart
     */
    public void getInputFromUser(String sku, BufferedReader br, Cart cart) {
        try {
            Product product = new Product();
            log.info("Number of Product " + sku + " :");
            Integer productQty = Integer.parseInt(br.readLine());
            product.validateQuantity(sku, productQty, cart);
        } catch (IOException ioe) {
            log.info(ioe);
        } catch (NumberFormatException nfe) {
            log.info("Please re-enter valid quantity for product " + sku + ".");
            getInputFromUser(sku, br, cart);
        } catch (PromotionException stockEx){
            log.info(stockEx);
        }
    }
}