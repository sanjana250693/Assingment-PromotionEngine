package promotion;

import cart.Cart;
import java.math.BigDecimal;

/**
 * This interface is implemented by all the promotion rules available in the system.
 *
 * @author Sanjana TG
 */
public interface Rules {
    public BigDecimal calculateTotal(Cart cart);
}
