package module.service;

import module.model.Customer;
import module.model.invoice.Invoice;
import module.model.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ShopService {
    private static final Logger WARNING = LoggerFactory.getLogger("WARNING");
    private static final Logger INFO = LoggerFactory.getLogger("INFO");

    private final List<Invoice> invoices;

    private static ShopService instance;

    private static class WrongLineException extends IllegalArgumentException{
        protected WrongLineException(String str){
            super("Line: " + str);
        }
    }

    private ShopService(final List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public static ShopService getInstance(){
        if(instance == null){
            instance = new ShopService(new ArrayList<Invoice>());
        }
        return instance;
    }

    public Optional<Map<String, Object>> randomProductInfo (List<List<String>> info) throws WrongLineException{
        List<String> header = info.get(0);
        int size = info.size();
        int min = 1;
        int max = size - 1;
        int randomLine = (int) (min + (1 + (max - min)) * Math.random());
        List<String> data = info.get(randomLine);
        Map<String, Object> hashMap = new HashMap<>();

        if(data.contains("ERROR")) {
            throw new WrongLineException(data.toString());
        }

        for (int i = 0; i < data.size(); i++) {
            hashMap.put(header.get(i), data.get(i));
        }
        return Optional.of(hashMap);
    }

    public void makeOrders(String productsInfo, int invoicesQnt, int productsQnt){
        ProductParser parser = new ProductParser();
        parser.parseCsv(productsInfo);
        List<List<String>> info = parser.getInfo();

        for(int i = 0; i < invoicesQnt; i++){
            Customer customer = PersonService.generateCustomer();
            Invoice invoice = new Invoice(customer);

            for(int j = 0; j < productsQnt; j++) {
                try {
                    Optional<Map<String, Object>> optional = randomProductInfo(info);
                    if (optional.isPresent()) {
                        Map<String, Object> hashMap = optional.get();
                        Optional<Product> product = ProductFactory.createProduct(hashMap);
                        product.ifPresent(invoice::addProduct);
                    }
                } catch (WrongLineException ex) {
                    WARNING.warn("{}, customer: {}", ex.getMessage(), customer.getEmail());
                }
            }
            INFO.info("customer id: {}, products in invoice: {}, invoice type: {}, invoice sum: {}",
                    customer.getId(), invoice.getProducts().size(), invoice.getType(),
                    invoice.getProducts().stream()
                            .map(Product::getPrice)
                            .reduce(0d, Double::sum));
            invoices.add(invoice);
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
}
