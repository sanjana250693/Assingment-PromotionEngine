package product;

public class ProductStockException extends Exception{
    public ProductStockException(String errorMessage){
        /* Calling the constructor of parent class */
        super(errorMessage);
    }
}
