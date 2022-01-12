package promotion;

import cart.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to apply available promotion in the system and
 * calculate the grand total of the shopping cart.
 *
 * @author Sanjana TG
 */
public class ApplyPromotion {
    public BigDecimal applyPromo(Cart cart){
        BigDecimal cartTotal = BigDecimal.valueOf(0);
        //Apply available promotion.
        Rules applyPromotionRulesForA = new NSkuForFixedPrice("A", 3, BigDecimal.valueOf(130));
        Rules applyPromotionRulesForB = new NSkuForFixedPrice("B", 2, BigDecimal.valueOf(45));
        Rules applyPromotionRulesForCAndD = new MultipleSkuForFixedPrice(new ArrayList<String>(Arrays.asList("C", "D")), BigDecimal.valueOf(30));

        //calculate grand total of cart.
        cartTotal = cartTotal.add(applyPromotionRulesForA.calculateTotal(cart));
        cartTotal = cartTotal.add(applyPromotionRulesForB.calculateTotal(cart));
        cartTotal = cartTotal.add(applyPromotionRulesForCAndD.calculateTotal(cart));
        return cartTotal;
    }
}
