package promotion;

import cart.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class ApplyPromotion {

    public BigDecimal applyPromo(Cart cart){
        BigDecimal cartTotal = BigDecimal.valueOf(0);
        Rules applyPromotionRulesForA = new nSkuForFixedPrice("A", 3, BigDecimal.valueOf(130));
        Rules applyPromotionRulesForB = new nSkuForFixedPrice("B", 2, BigDecimal.valueOf(45));
        Rules applyPromotionRulesForCAndD = new multipleSkuForFixedPrice(new ArrayList<String>(Arrays.asList("C", "D")), BigDecimal.valueOf(30));

        //calculate grand total of cart.
        if(cartTotal.equals(0)){
            cartTotal = cartTotal.add(applyPromotionRulesForA.calculateTotal(cart));
        }else

        cartTotal = cartTotal.add(applyPromotionRulesForB.calculateTotal(cart));
        cartTotal = cartTotal.add(applyPromotionRulesForCAndD.calculateTotal(cart));
        return cartTotal;
    }
}
