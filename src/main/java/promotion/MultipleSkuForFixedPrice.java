package promotion;

import cart.Cart;
import lombok.AllArgsConstructor;
import product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map.Entry<String, Integer> minEntry = null;
            for (Map.Entry<String, Integer> entry : skuInCart.entrySet()) {
                minEntry = fetchMinEntryFromMap(minEntry, entry);
                totalNoOfProducts += entry.getValue();
            }

            total = total.add(BigDecimal.valueOf(minEntry.getValue()).multiply(promotionPrice));
            if(totalNoOfProducts - minEntry.getValue() > minEntry.getValue()) {
                //Calculate unit price for remaining qty which do not
                total = calculateUnitPrice(cartItems, total, skuInCart, minEntry);
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

    private BigDecimal calculateUnitPrice(List<Product> cartItems, BigDecimal total, Map<String, Integer> skuInCart, Map.Entry<String, Integer> minEntry) {
        for(Map.Entry<String, Integer> availableQty : skuInCart.entrySet()){
            if(availableQty.getValue() > minEntry.getValue()){
                total = computeTotalForRemainingQty(cartItems, total, minEntry, availableQty);
            }
        }
        return total;
    }

    private BigDecimal computeTotalForRemainingQty(List<Product> cartItems, BigDecimal total, Map.Entry<String, Integer> minEntry, Map.Entry<String, Integer> availableQty) {
        for (Product product : cartItems) {
            if (product.getProductID().equals(availableQty.getKey())) {
                total = total.add(BigDecimal.valueOf(availableQty.getValue()- minEntry.getValue()).multiply(product.getUnitPrice()));
            }
        }
        return total;
    }

    private boolean isPromotionApplicable(Map<String, Integer> skuInCart) {
        return skuInCart.keySet().containsAll(listOfSku);
    }
}
