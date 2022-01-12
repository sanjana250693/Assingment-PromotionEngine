package promotion;

import cart.Cart;
import lombok.AllArgsConstructor;
import product.Product;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
public class MultipleSkuForFixedPrice implements Rules {
    private ArrayList<String> listOfSku;
    private BigDecimal promotionPrice;

    @Override
    public BigDecimal calculateTotal(Cart cart) {
        BigDecimal total = BigDecimal.valueOf(0);
        Map<String, Integer> skuInCart = new HashMap<>();
        List<Product> cartItems = cart.getItemsInCart();
        createSkuMap(skuInCart, cartItems);

        if(isPromotionApplicable(skuInCart)){
            Integer totalNoOfProducts = 0;
            Integer minValueInMap = Collections.min(skuInCart.values());
            totalNoOfProducts = getTotalProductsInCart(skuInCart, totalNoOfProducts);
            total = total.add(BigDecimal.valueOf(minValueInMap).multiply(promotionPrice));
            if((totalNoOfProducts - minValueInMap) > minValueInMap) {
                //Calculate unit price for remaining qty which do not
                total = calculateUnitPrice(cartItems, total, skuInCart, minValueInMap);
            }
        }else{
            for(Product product : cartItems){
                if(skuInCart.containsKey(product.getProductID())){
                    total = product.getUnitPrice().multiply(BigDecimal.valueOf(product.getPurchaseQuantity()));
                }
            }
        }
        return total;
    }

    private Integer getTotalProductsInCart(Map<String, Integer> skuInCart, Integer totalNoOfProducts) {
        for (Map.Entry<String, Integer> entry : skuInCart.entrySet()) {
            totalNoOfProducts += entry.getValue();
        }
        return totalNoOfProducts;
    }

    private Map.Entry<String, Integer> fetchMinEntryFromMap (Map.Entry<String, Integer> minEntry, Map.Entry<String, Integer> entry) {
        if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
            minEntry = entry;
        }
        return minEntry;
    }

    private void createSkuMap(Map<String, Integer> skuInCart, List<Product> cartItems) {
        for(Product product : cartItems){
            if(product.getPurchaseQuantity()!=0 && listOfSku.contains(product.getProductID())) {
                skuInCart.put(product.getProductID(), product.getPurchaseQuantity());
            }
        }
    }

    private BigDecimal calculateUnitPrice(List<Product> cartItems, BigDecimal total, Map<String, Integer> skuInCart, Integer minEntry) {
        for(Map.Entry<String, Integer> availableQty : skuInCart.entrySet()){
            if(availableQty.getValue() > minEntry){
                total = computeTotalForRemainingQty(cartItems, total, minEntry, availableQty);
            }
        }
        return total;
    }

    private BigDecimal computeTotalForRemainingQty(List<Product> cartItems, BigDecimal total, Integer minEntry, Map.Entry<String, Integer> availableQty) {
        for (Product product : cartItems) {
            if (product.getProductID().equals(availableQty.getKey())) {
                total = total.add(BigDecimal.valueOf(availableQty.getValue()- minEntry).multiply(product.getUnitPrice()));
            }
        }
        return total;
    }

    private boolean isPromotionApplicable(Map<String, Integer> skuInCart) {
        return skuInCart.keySet().containsAll(listOfSku);
    }
}