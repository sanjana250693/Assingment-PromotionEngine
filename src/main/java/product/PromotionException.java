package product;

/**
 * This class is used to handle customised
 * exceptions related to promotions.
 *
 * @author Sanjana TG
 */
public class PromotionException extends Exception{
    public PromotionException(String errorMessage){
        /* Calling the constructor of parent class */
        super(errorMessage);
    }
}
