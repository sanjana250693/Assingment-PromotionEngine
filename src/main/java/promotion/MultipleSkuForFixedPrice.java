package promotion;

import cart.Cart;
import lombok.AllArgsConstructor;
import product.Product;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class contains promotion rules for combined
 * promotion for multiple products.
 *
 * @author Sanjana TG
 */

@AllArgsConstructor
public class MultipleSkuForFixedPrice implements Rules {
    private ArrayList<String> listOfSku;
    private BigDecimal promotionPrice;

    /**
     * This method is used to calculate the total after applying
     * promotion to applicable products.
     * @param cart - shopping cart.
     * @return BigDecimal - Total after applying promotion.
     */
    @Override
    public BigDecimal calculateTotal(Cart cart) {
        BigDecimal total = BigDecimal.valueOf(0);
        Map<String, Integer> skuInCart = new HashMap<>();
        List<Product> cartItems = cart.getItemsInCart();
        createSkuMap(skuInCart, cartItems);
        //If promotion is applicable.
        if(isPromotionApplicable(skuInCart)){
            Integer minValueInMap = Collections.min(skuInCart.values());
            Integer totalNoOfProducts = getTotalProductsInCart(skuInCart);
            total = total.add(BigDecimal.valueOf(minValueInMap).multiply(promotionPrice));
            if((totalNoOfProducts - minValueInMap) > minValueInMap) {
                //Calculate unit price for remaining qty which do not
                total = calculateUnitPrice(cartItems, total, skuInCart, minValueInMap);
            }
        }else{//If promotion is not applicable.
            for(Product product : cartItems){
                if(skuInCart.containsKey(product.getProductID())){
                    total = product.getUnitPrice().multiply(BigDecimal.valueOf(product.getPurchaseQuantity()));
                }
            }
        }
        return total;
    }

    /**
     * This method is used to create map of sku's and purchase quantity.
     * @param skuInCart - All Sku's in shopping cart.
     * @param  cartItems - Items in cart.
     */
    private void createSkuMap(Map<String, Integer> skuInCart, List<Product> cartItems) {
        for(Product product : cartItems){
            if(product.getPurchaseQuantity()!=0 && listOfSku.contains(product.getProductID())) {
                skuInCart.put(product.getProductID(), product.getPurchaseQuantity());
            }
        }
    }

    /**
     * This method is used to fetch the total number of
     * products in cart.
     * @param skuInCart - All Sku's in shopping cart.
     * @return Integer -
     */
    private Integer getTotalProductsInCart(Map<String, Integer> skuInCart) {
        Integer totalNoOfProducts = 0;
        for (Map.Entry<String, Integer> entry : skuInCart.entrySet()) {
            totalNoOfProducts += entry.getValue();
        }
        return totalNoOfProducts;
    }

    /**
     * This method is used to fetch the total number of
     * products in cart.
     * @param cartItems - Items in shopping cart.
     * @param total - cart total.
     * @param skuInCart - All Sku's in shopping cart.
     * @param  minEntry - minimum value from sku map.
     * @return Integer - Total products in shopping cart.
     */
    private BigDecimal calculateUnitPrice(List<Product> cartItems, BigDecimal total, Map<String, Integer> skuInCart, Integer minEntry) {
        for(Map.Entry<String, Integer> availableQty : skuInCart.entrySet()){
            if(availableQty.getValue() > minEntry){
                total = computeTotalForRemainingQty(cartItems, total, minEntry, availableQty);
            }
        }
        return total;
    }

    /**
     * This method is calculate total price of remaining products
     * after promotion is added to cart.
     * @param cartItems - Items in shopping cart.
     * @param total - cart total.
     * @param  minEntry - minimum value from sku map.
     * @return BigDecimal
     */
    private BigDecimal computeTotalForRemainingQty(List<Product> cartItems, BigDecimal total, Integer minEntry, Map.Entry<String, Integer> availableQty) {
        for (Product product : cartItems) {
            if (product.getProductID().equals(availableQty.getKey())) {
                total = total.add(BigDecimal.valueOf(availableQty.getValue()- minEntry).multiply(product.getUnitPrice()));
            }
        }
        return total;
    }

    /**
     * This method is check if this particular
     * promotion is applicable to cart.
     * @param skuInCart - Product sku in cart.
     * @return boolean
     */
    private boolean isPromotionApplicable(Map<String, Integer> skuInCart) {
        return skuInCart.keySet().containsAll(listOfSku);
    }
}