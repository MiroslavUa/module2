package module.service;

import module.model.invoice.Invoice;
import module.model.invoice.InvoiceType;
import module.model.product.Product;

import java.util.*;
import java.util.function.Predicate;

public class ShopStatistics {
    private final List<Invoice> invoices;

    private static ShopStatistics instance;

    private ShopStatistics() {
        this.invoices = ShopService.getInstance().getInvoices();
    }

    public static ShopStatistics getInstance() {
        if (instance == null) {
            instance = new ShopStatistics();
        }
        return instance;
    }

    public long soldTelevisions() {
        return this.invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(product -> product.getClass().getSimpleName().equals("Television")).count();
    }

    public long soldTelephones() {
        return this.invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(product -> product.getClass().getSimpleName().equals("Telephone")).count();
    }

    public String leastInvoice() {

        OptionalDouble doubleOptional = this.invoices.stream()
                .map(Invoice::getProducts)
                .mapToDouble(list -> list.stream().map(Product::getPrice).reduce(0d, Double::sum))
                .min();

        double sum;
        if (doubleOptional.isPresent()) {
            sum = doubleOptional.getAsDouble();
        } else {
            sum = 0;
        }

        Predicate<Invoice> leastSum = invoice -> invoice.getProducts().stream()
                .map(Product::getPrice)
                .reduce(0d, Double::sum) == sum;

        Optional<String> stringOptional = this.invoices.stream()
                .filter(leastSum).map(invoice -> invoice.getCustomer().getEmail()).findAny();

        String customer;
        customer = stringOptional.map(String::toString).orElse("");
        return "Least invoice sum is: " + sum + ", customer: " + customer;
    }

    public double sum() {
        return this.invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .map(Product::getPrice)
                .reduce(0d, Double::sum);
    }

    public long retailInvoices() {
        return this.invoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.RETAIL))
                .count();
    }

    public long oneTypeProductInvoices() {
        long televisions = this.invoices.stream()
                .filter(invoice -> invoice.getProducts().stream()
                        .map(product -> product.getClass().getSimpleName())
                        .allMatch(str -> str.equals("Television")))
                .count();

        long telephones = this.invoices.stream()
                .filter(invoice -> invoice.getProducts().stream()
                        .map(product -> product.getClass().getSimpleName())
                        .allMatch(str -> str.equals("Telephone")))
                .count();

        return televisions + telephones;
    }

    public long specifiedTypeProductInvoices(String type) {
        return this.invoices.stream()
                .filter(invoice -> invoice.getProducts().stream()
                        .map(product -> product.getClass().getSimpleName())
                        .allMatch(str -> str.equals(type)))
                .count();
    }

    public List<Invoice> firstThreeInvoice() {
        return this.invoices.stream()
                .sorted(Comparator.comparing(Invoice::getDate))
                .limit(3)
                .toList();
    }

    public List<Invoice> lowAgeInvoices() {
        ArrayList<Invoice> lowAgeInvoices = this.invoices.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        lowAgeInvoices.forEach(invoice -> invoice.setType(InvoiceType.LOW_AGE));
        return lowAgeInvoices;
    }

    public List<Invoice> sortInvoices() {
        return this.invoices.stream()
                .sorted(Comparator.comparing((Invoice invoice) -> invoice.getCustomer().getAge()).reversed()
                        .thenComparing(invoice -> invoice.getProducts().size())
                        .thenComparing(invoice -> invoice.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(0d, Double::sum))).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    }
}
