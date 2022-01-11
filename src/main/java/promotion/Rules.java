package promotion;

import cart.Cart;

import java.math.BigDecimal;

public interface Rules {
    public BigDecimal calculateTotal(Cart cart);
}
