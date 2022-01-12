package unittest;

import org.junit.jupiter.api.Test;
import product.Product;
import product.StockData;

import java.util.List;

public class StockDataTest {

    @Test
    public void testAvailableStockData() {
        StockData stockData = new StockData();
        List<Product> product = stockData.getProductDataFromJson();
        assert product.size() == 4;
    }

}
