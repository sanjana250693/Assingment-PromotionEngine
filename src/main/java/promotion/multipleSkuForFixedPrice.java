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
public class multipleSkuForFixedPrice implements Rules {
    private ArrayList<String> listOfSku;
    private BigDecimal promotionPrice;

    @Override
    public BigDecimal calculateTotal(Cart cart) {
        BigDecimal total = BigDecimal.valueOf(0);
        Map<String, Integer> skuInCart = new HashMap<>();
        List<Product> cartItems = cart.getItemsInCart();
        createSkuMap(skuInCart, cartItems);

        if(isPromotionApplicable(cart, skuInCart)){
            //If promotion is applicable to the items in cart, calculate total based on total.
            Integer totalNoOfProducts = 0;
            Map.Entry<String, Integer> minEntry = null;
            for (Map.Entry<String, Integer> entry : skuInCart.entrySet()) {
                minEntry = fetchMinEntryFromMap(minEntry, entry);
                totalNoOfProducts += entry.getValue();
            }

            total = computeTotal(total, BigDecimal.valueOf(minEntry.getValue()), promotionPrice);
            if(totalNoOfProducts - minEntry.getValue() > minEntry.getValue()) {
                //Calculate unit price for remaining qty which do not
                total = computeTotalForRemainingQty(cartItems, total, skuInCart, minEntry);
            }

        }else{
            //If promotion is not applicable, calculate total as qty*unitPrice.
            for(Product product : cartItems){
                if(skuInCart.containsKey(product.getProductID())){
                    total = computeTotal(total, product.getUnitPrice(), BigDecimal.valueOf(product.getPurchaseQuantity()));
                }
            }
        }
        return total;
    }

    private Map.Entry<String, Integer> fetchMinEntryFromMap(Map.Entry<String, Integer> minEntry, Map.Entry<String, Integer> entry) {
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

    private BigDecimal computeTotal(BigDecimal total, BigDecimal itemQty, BigDecimal price) {
        total = total.add(itemQty).multiply(price);
        return total;
    }

    private BigDecimal computeTotalForRemainingQty(List<Product> cartItems, BigDecimal total, Map<String, Integer> skuInCart, Map.Entry<String, Integer> minEntry) {
        for(Map.Entry<String, Integer> availableQty : skuInCart.entrySet()){
            Integer minValueFromMap = minEntry.getValue();
            if(availableQty.getValue() > minValueFromMap){
                total = computeTotalForUnitPrice(cartItems, total, availableQty, minValueFromMap);
            }
        }
        return total;
    }

    private BigDecimal computeTotalForUnitPrice(List<Product> cartItems, BigDecimal total, Map.Entry<String, Integer> availableQty, Integer minValueFromMap) {
        for (Product product : cartItems) {
            if (product.getProductID().equals(availableQty.getKey())) {
                total = computeTotal(total, BigDecimal.valueOf(availableQty.getValue()- minValueFromMap), product.getUnitPrice());
            }
        }
        return total;
    }

    private boolean isPromotionApplicable(Cart cart, Map<String, Integer> skuInCart) {
        return skuInCart.keySet().containsAll(listOfSku);
    }
}
