package service;

import model.Customer;
import model.invoice.Invoice;
import model.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    private final List<Invoice> invoices;

    private static ShopService instance;

    private ShopService(final List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public static ShopService getInstance(){
        if(instance == null){
            instance = new ShopService(new ArrayList<Invoice>());
        }
        return instance;
    }

    //TODO generate exception if line has ERROR
    public Optional<Map<String, Object>> randomProductInfo (List<List<String>> info){
        List<String> header = info.get(0);
        int size = info.size();
        int min = 1;
        int max = size - 1;
        int randomLine = (int) (Math.floor(min + (1 + (max - min)) * Math.random()));
        List<String> data = info.get(randomLine);
        Map<String, Object> hashMap = new HashMap<>();

        try {
            if(data.contains("ERROR")) {
                throw new IllegalArgumentException("Bad Line"); //TODO replace with custom exception
            }

            for (int i = 0; i < data.size(); i++) {
                hashMap.put(header.get(i), info.get(i));
            }
            return Optional.of(hashMap);
        } catch (IllegalArgumentException ex) { //TODO replace with custom exception
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    //TODO add created invoice to log file (9?)
    public void makeOrders(String productsInfo){
        ProductParser parser = new ProductParser();
        parser.parseCsv(productsInfo);
        List<List<String>> info = parser.getInfo();

        for(int i = 0; i < 15; i++){
            Customer customer = PersonService.generateCustomer();
            Invoice invoice = new Invoice(customer);

            for(int j = 0; j < 5; j++) {
                Optional<Map<String, Object>> optional = randomProductInfo(info);
                if(optional.isPresent()) {
                    Map<String, Object> hashMap = optional.get();
                    Optional<Product> product = ProductFactory.createProduct(hashMap);
                    product.ifPresent(invoice::addProduct);
                }
            }
            invoices.add(invoice);
        }
    }
}
