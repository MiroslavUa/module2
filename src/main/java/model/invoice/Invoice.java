package model.invoice;

import model.Customer;
import model.product.Product;
import util.UserInputUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Invoice {
    private static Predicate<Invoice> isWholesale;
    private List<Product> products;
    private Customer customer;
    InvoiceType type;
    LocalDateTime date;

    public Invoice(Customer customer) {
        this.products = new ArrayList<Product>();
        this.customer = customer;
        this.type = InvoiceType.RETAIL;
        this.date = LocalDateTime.now();
    }

    public static Predicate<Invoice> getSalePredicate() {
        return isWholesale;
    }

    public static void setSalePredicate() {
        final double limit;
        try {
            System.out.println("Enter limit for wholesale invoice: ");
            limit = UserInputUtil.doubleValue();
            isWholesale = invoice -> invoice.getProducts().stream()
                    .map(Product::getPrice)
                    .reduce(0d, Double::sum) > limit;
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage()); //TODO send this message to log file
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void addProduct(Product product){
        products.add(product);
        boolean wholesale = isWholesale.test(this);
        if (wholesale) {
            this.setType(InvoiceType.WHOLESALE);
        }
    }

    public void removeProduct(Product product){
        products.remove(product);
        boolean wholesale = isWholesale.test(this);
        if(!wholesale){
            this.setType(InvoiceType.RETAIL);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;
        return getProducts().equals(invoice.getProducts())
                && getCustomer().equals(invoice.getCustomer())
                && getType() == invoice.getType()
                && getDate().equals(invoice.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProducts(), getCustomer(), getType(), getDate());
    }
}
