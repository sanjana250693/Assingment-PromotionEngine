package unittest;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import product.Product;
import product.StockData;

import java.io.IOException;
import java.util.List;

public class StockDataTest {

    @Test
    public void testAvailableStockData() {
        StockData stockData = new StockData();
        List<Product> product = stockData.getAvailableStockData();
        assert product.size() == 4;
    }

}
