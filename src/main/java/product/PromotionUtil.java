package product;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PromotionUtil {
    /**
     * This method used to get the available products in the system.
     * @return Properties.
     */
    public Properties getPromotionUtil() {
        Properties property = new Properties();
        try{
            FileReader reader = new FileReader("./src/main/resources/productSku.properties");
            property.load(reader);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return property;
    }
}
