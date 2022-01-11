package unittest;

import cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import product.Product;
import org.junit.jupiter.api.Test;
import promotion.NSkuForFixedPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NSkuForFixedPriceTest {
    NSkuForFixedPrice nSkuForFixedPrice;

    @BeforeEach
    public void setup(){
        nSkuForFixedPrice = new NSkuForFixedPrice("A", 3, BigDecimal.valueOf(130));
    }

    @Test
    public void testNSkuPromotionWithQty3(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 3, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA)));
        assert Objects.equals(nSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(130));
    }

    @Test
    public void testNSkuPromotionWithQtyLessThan3(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 2, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA)));
        assert Objects.equals(nSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(100));
    }

    @Test
    public void testNSkuPromotionWithQty0(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 0, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA)));
        assert Objects.equals(nSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(0));
    }

    private Product createProduct(String productID, BigDecimal unitPrice, Integer quantity, Integer inStock) {
        Product product = new Product();
        product.setProductID(productID);
        product.setUnitPrice(unitPrice);
        product.setPurchaseQuantity(quantity);
        product.setInStock(inStock);
        return product;
    }

    public Cart createCart(List<Product> products) {
        return new Cart(products);
    }
}
