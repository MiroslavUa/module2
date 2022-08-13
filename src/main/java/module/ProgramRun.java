package module;

import module.model.invoice.Invoice;
import module.model.product.Product;
import module.service.ShopService;
import module.service.ShopStatistics;

import java.util.List;

public class ProgramRun {
    public static void run() {
        Invoice.setSalePredicate();
        ShopService service = ShopService.getInstance();
        service.makeOrders("modelsShuffled.csv", 15, 5);


        ShopStatistics statistics = ShopStatistics.getInstance();

        System.out.println("Number of sold Telephones: " + statistics.soldTelephones());
        System.out.println("Number of sold Televisions: " + statistics.soldTelevisions());

        System.out.println(statistics.leastInvoice());

        System.out.println("Total Sum of all invoices: " + statistics.sum());

        System.out.println("Number of Retail invoices: " + statistics.retailInvoices());

        System.out.println("Number of invoices with one type product: " + statistics.oneTypeProductInvoices());
        System.out.println("Number of invoices with only Televisions: " + statistics.specifiedTypeProductInvoices("Television"));
        System.out.println("Number of invoices with only Telephones: " + statistics.specifiedTypeProductInvoices("Telephone"));

        List<Invoice> invoices = statistics.firstThreeInvoice();
        System.out.println("\nFirst three invoice are: ");
        for (int i = 0; i < invoices.size(); i++) {
            System.out.println((i + 1) + ". " + invoices.get(i).toString());
        }

        invoices = statistics.lowAgeInvoices();
        if (invoices.size() == 0) {
            System.out.println("\nThere are no low age invoices. ");
        } else {
            System.out.println("\nLow age invoices are: ");
            for (int i = 0; i < invoices.size(); i++) {
                System.out.println((i + 1) + ". " + invoices.get(i).toString());
            }
        }

        invoices = statistics.sortInvoices();
        System.out.println("\nSorted invoices: ");
        for (int i = 0; i < invoices.size(); i++) {
            System.out.println((i + 1) + ". customer email: " + invoices.get(i).getCustomer().getEmail()
                    + ", age: " + invoices.get(i).getCustomer().getAge()
                    + ", qnt.: " + invoices.get(i).getProducts().size()
                    + ", total sum of invoice: " + invoices.get(i).getProducts().stream()
                    .map(Product::getPrice)
                    .reduce(0d, Double::sum));
        }
    }
}
