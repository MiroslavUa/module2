import model.Customer;
import model.invoice.Invoice;
import model.product.Product;
import model.product.ScreenType;
import model.product.Telephone;
import service.PersonService;
import service.ProductParser;
import service.ShopService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
       ShopService service = ShopService.getInstance();
       service.makeOrders("modelsShuffled.csv");




    }
}
