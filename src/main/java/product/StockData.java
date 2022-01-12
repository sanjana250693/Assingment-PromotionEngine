package product;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StockData {
    private String path;

    public StockData() {
        this.path = "./src/main/resources/ProductData.json";
    }

    /**
     *
     * @return
     */
    public List<Product> getAvailableStockData() {
        List<Product> productStock = new ArrayList<>();
        Object obj = null;
        try {
            obj = readFromFile(path);
        } catch (IOException | ParseException exception ) {
            exception.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObject.get("products");
        for (Object object : jsonArray) {
            productStock.add(parseProduct((JSONObject) object));
        }
        return productStock;
    }

    private Object readFromFile(String path) throws IOException, ParseException {
        return new JSONParser().parse((new FileReader(path)));
    }

    private Product parseProduct(JSONObject jsonObject) {
        String itemName = (String) jsonObject.get("name");
        Integer itemInStock = Integer.parseInt(jsonObject.get("inStock").toString());
        BigDecimal unitPrice = BigDecimal.valueOf(Double.parseDouble(jsonObject.get("unitPrice").toString()));
        return new Product(itemName, itemInStock, unitPrice);
    }

}

