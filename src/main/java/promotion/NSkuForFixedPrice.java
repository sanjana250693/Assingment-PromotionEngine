package promotion;

import cart.Cart;
import lombok.AllArgsConstructor;
import product.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class contains promotion rules for 'n' items
 * of a SKU for fixed price.
 *
 * @author Sanjana TG
 */
@AllArgsConstructor
public class NSkuForFixedPrice implements Rules {
    private String productName;
    private Integer numberOfProducts;
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
        List<Product> productPurchaseList = cart.getItemsInCart();
            for (Product product : productPurchaseList) {
                String purchaseProductID = product.getProductID();
                int purChaseProductQuantity = product.getPurchaseQuantity();
                if (purchaseProductID.equals(productName)) {
                    if(isPromotionApplicable(cart)) {
                        int promotionCount = Math.floorDiv(purChaseProductQuantity, numberOfProducts);
                        int unitPriceCount = purChaseProductQuantity % numberOfProducts;
                        total = total.add(BigDecimal.valueOf(promotionCount).multiply(promotionPrice));
                        total = total.add(BigDecimal.valueOf(unitPriceCount).multiply(product.getUnitPrice()));
                    }else {
                        total = total.add(product.getUnitPrice().multiply(BigDecimal.valueOf(purChaseProductQuantity)));
                    }
                }
            }
        return total;
    }

    /**
     * This method is check if this particular
     * promotion is applicable to cart.
     * @param cart - Shopping cart.
     * @return boolean
     */
    private boolean isPromotionApplicable(Cart cart) {
        boolean applyPromotion = false;
        for(Product productInCart : cart.getItemsInCart()){
            if(productInCart.getPurchaseQuantity()>=numberOfProducts) {
                applyPromotion = true;
                break;
            }
        }
        return applyPromotion;
    }
}
