package promotion;

import cart.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplyPromotion {

    public BigDecimal applyPromo(Cart cart){
        BigDecimal cartTotal = BigDecimal.valueOf(0);
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
