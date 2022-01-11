package unittest;

import cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import promotion.MultipleSkuForFixedPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MultipleSkuForFixedPriceTest {
    ArrayList<String> combinedProducts = new ArrayList<>(Arrays.asList("C", "D"));
    BigDecimal promotionPrice = BigDecimal.valueOf(30);
    MultipleSkuForFixedPrice multipleSkuForFixedPrice ;

    @BeforeEach
    public void setup(){
        multipleSkuForFixedPrice = new MultipleSkuForFixedPrice(combinedProducts, promotionPrice);
    }


    @Test
    public void checkForNoCombinedPromotion() {
        Product product1 = createProduct("A", BigDecimal.valueOf(50), 3, 5);
        Product product2 = createProduct("B", BigDecimal.valueOf(30), 4, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(0));
    }

    @Test
    public void checkForValidCombinePromotion() {
        Product product1 = createProduct("C", BigDecimal.valueOf(20), 4, 5);
        Product product2 = createProduct("D", BigDecimal.valueOf(15), 7, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(165));
    }

    @Test
    public void checkForValidCombinePromotionWithSameProductCount() {
        Product product1 = createProduct("C", BigDecimal.valueOf(20), 4, 5);
        Product product2 = createProduct("D", BigDecimal.valueOf(15), 4, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(120));
    }

    @Test
    public void checkForValidCombinePromotionWithOneProductCount() {
        Product product1 = createProduct("C", BigDecimal.valueOf(20), 1, 5);
        Product product2 = createProduct("D", BigDecimal.valueOf(15), 1, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(30));
    }

    @Test
    public void checkForValidCombinePromotionWithZeroProducts() {
        Product product1 = createProduct("C", BigDecimal.valueOf(20), 0, 5);
        Product product2 = createProduct("D", BigDecimal.valueOf(15), 0, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(0));
    }

    @Test
    public void checkForValidCombinePromotionWithOneAndZeroProducts() {
        Product product1 = createProduct("C", BigDecimal.valueOf(20), 1, 5);
        Product product2 = createProduct("D", BigDecimal.valueOf(15), 0, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(product1, product2)));
        assert Objects.equals(multipleSkuForFixedPrice.calculateTotal(cart), BigDecimal.valueOf(20));
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
