package promotion;

import cart.Cart;
import lombok.AllArgsConstructor;
import product.Product;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class nSkuForFixedPrice implements Rules {
    private String productName;
    private Integer numberOfProducts;
    private BigDecimal promotionPrice;
    
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
