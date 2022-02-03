package unittest;

import cart.Cart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import promotion.MultipleSkuForFixedPrice;
import promotion.NSkuForFixedPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SampleDataTest {
    private static Log log = LogFactory.getLog(SampleDataTest.class);
    NSkuForFixedPrice nSkuForFixedPriceForA;
    NSkuForFixedPrice nSkuForFixedPriceForB;
    MultipleSkuForFixedPrice multipleSkuForFixedPrice;

    @BeforeAll
    public static void displayPromotion(){
        log.info("Available promotions in the system:");
        log.info("3 of A's for 130");
        log.info("2 of B's for 45");
        log.info("C & D for 30\n");
    }

    @BeforeEach
    public void setup(){
        nSkuForFixedPriceForA = new NSkuForFixedPrice("A", 3, BigDecimal.valueOf(130));
        nSkuForFixedPriceForB = new NSkuForFixedPrice("B", 2, BigDecimal.valueOf(45));
        multipleSkuForFixedPrice = new MultipleSkuForFixedPrice(new ArrayList<>(Arrays.asList("C", "D")), BigDecimal.valueOf(30));
    }

    @Test
    public void scenarioA(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 1, 5);
        Product productB = createProduct("B", BigDecimal.valueOf(30), 1, 10);
        Product productC = createProduct("C", BigDecimal.valueOf(20), 1, 10);
        Product productD = createProduct("D", BigDecimal.valueOf(15), 0, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA,productB, productC, productD)));
        BigDecimal grandTotal = nSkuForFixedPriceForA.calculateTotal(cart).add(nSkuForFixedPriceForB.calculateTotal(cart)).add(multipleSkuForFixedPrice.calculateTotal(cart));
        log.info("Products in cart: A=1, B=1, C=1, D=1");
        log.info("GrandTotal: " + grandTotal +"\n");
        assert Objects.equals(grandTotal, BigDecimal.valueOf(100));
    }

    @Test
    public void scenarioB(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 5, 5);
        Product productB = createProduct("B", BigDecimal.valueOf(30), 5, 10);
        Product productC = createProduct("C", BigDecimal.valueOf(20), 1, 10);
        Product productD = createProduct("D", BigDecimal.valueOf(15), 0, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA,productB, productC, productD)));
        BigDecimal grandTotal = nSkuForFixedPriceForA.calculateTotal(cart).add(nSkuForFixedPriceForB.calculateTotal(cart)).add(multipleSkuForFixedPrice.calculateTotal(cart));
        log.info("Products in cart: A=5, B=5, C=1, D=0");
        log.info("GrandTotal: " + grandTotal+"\n");
        assert Objects.equals(grandTotal, BigDecimal.valueOf(370));
    }

    @Test
    public void scenarioC(){
        Product productA = createProduct("A", BigDecimal.valueOf(50), 3, 5);
        Product productB = createProduct("B", BigDecimal.valueOf(30), 5, 10);
        Product productC = createProduct("C", BigDecimal.valueOf(20), 1, 10);
        Product productD = createProduct("D", BigDecimal.valueOf(15), 1, 10);
        Cart cart = createCart(new ArrayList<>(Arrays.asList(productA,productB, productC, productD)));
        BigDecimal grandTotal = nSkuForFixedPriceForA.calculateTotal(cart).add(nSkuForFixedPriceForB.calculateTotal(cart)).add(multipleSkuForFixedPrice.calculateTotal(cart));
        log.info("Products in cart: A=3, B=5, C=1, D=1");
        log.info("GrandTotal: " + grandTotal+"\n");
        assert Objects.equals(grandTotal, BigDecimal.valueOf(280));
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
