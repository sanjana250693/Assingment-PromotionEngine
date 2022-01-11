package product;

import cart.Cart;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Product {
    private String productID;
    private Integer inStock;
    private BigDecimal unitPrice;
    private Integer purchaseQuantity;

    private StockData stockData = new StockData();

    public Product(String productID, Integer inStock, BigDecimal unitPrice) {
        this.productID = productID;
        this.inStock = inStock;
        this.unitPrice = unitPrice;
    }

    public Product() {}

    public void validateQuantity(String productID, Integer qty, Cart cart) {
        Product item = new Product();
        List<Product> cartItems = cart.getItemsInCart();
        Product currentProduct = getProductFromJson(productID, new Product());
        if (currentProduct != null && qty != null && qty <= currentProduct.getInStock()) {
            item.setProductID(productID);
            item.setPurchaseQuantity(qty);
            //set unit price
            item.setUnitPrice(currentProduct.getUnitPrice());
            cartItems.add(item);
            cart.setItemsInCart(cartItems);
        } else {
            System.out.println("Warning: Available stock is " + currentProduct.getInStock() + ". Please enter quantity within this limit.");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            cart.getInputFromUser(currentProduct.getProductID(), br, cart);
        }
    }

    private Product getProductFromJson(String productID, Product current) {
        for (Product product : stockData.getAvailableStockData()) {
            if (product.getProductID().equals(productID)) {
                current = product;
            }
        }
        return current;
    }
}
