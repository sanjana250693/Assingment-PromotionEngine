package cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import product.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private List<Product> itemsInCart;

    public void getInputFromUser(String sku, BufferedReader br, Cart cart) {
        try {
            Product product = new Product();
            System.out.println("Number of Product " + sku + " :");
            Integer productQty = Integer.parseInt(br.readLine());
            product.validateQuantity(sku, productQty, cart);
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nfe) {
            System.out.println("Please re-enter valid quantity for product " + sku + ".");
            getInputFromUser(sku, br, cart);
        }
    }
}
