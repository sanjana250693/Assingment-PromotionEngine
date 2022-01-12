package product;

import cart.Cart;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

/**
 * This class is used to fetch the product details
 * from Json file and set the product to cart.
 *
 * @author Sanjana TG
 */
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

    /**
     * This method used to check if the quantity is instock and
     * set the product to the cart.
     * @param productID - product Sku.
     * @param qty - quantity of the product to be added to cart.
     * @param  cart - shopping cart.
     * @return Nothing.
     */
    public void validateQuantity(String productID, Integer qty, Cart cart) {
        Product item = new Product();
        List<Product> cartItems = cart.getItemsInCart();
        Product currentProduct = getCurrentProduct(productID, new Product());
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

    /**
     * This method used to get the products from Json file and return current product.
     * @param productID - product Sku.
     * @param currentProduct - current product from cart.
     * @return Product.
     */
    private Product getCurrentProduct(String productID, Product currentProduct) {
        for (Product product : stockData.getProductDataFromJson()) {
            if (product.getProductID().equals(productID)) {
                currentProduct = product;
            }
        }
        return currentProduct;
    }
}
