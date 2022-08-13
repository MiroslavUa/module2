package module.service;

import module.model.Customer;
import module.model.invoice.Invoice;
import module.model.product.ScreenType;
import module.model.product.Telephone;
import module.model.product.Television;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ShopStatisticsTest {
    private ShopStatistics target;

    private List<Invoice> invoices;

    private static Predicate<Invoice> isWholesale;

    private Telephone ph1, ph2, ph3, ph4;
    private Television tv1, tv2, tv3, tv4;

    private Customer c16, c17, c20, c95;

    private Invoice i16, i17, i20, i95;

    @BeforeEach
    void setUp() {
        target = ShopStatistics.getInstance();
        invoices = target.getInvoices();
        invoices.clear();

        isWholesale = Mockito.mock(Predicate.class);
        try{
            Field[] fields = Invoice.class.getDeclaredFields();
            Field isWholesaleField = fields[0];
            isWholesaleField.setAccessible(true);
            isWholesaleField.set(isWholesaleField, isWholesale);
        }
        catch(Exception e){
            System.out.println("Reflection error: " + e.getMessage());
        }
        Mockito.when(isWholesale.test(any())).thenReturn(false);

        ph1 = new Telephone("Phone1", ScreenType.LED, 10d, "Model1");
        ph2 = new Telephone("Phone2", ScreenType.LED, 20d, "Model2");
        ph3 = new Telephone("Phone3", ScreenType.LED, 30d, "Model3");
        ph4 = new Telephone("Phone4", ScreenType.LED, 40d, "Model4");

        tv1 = new Television("Televison1", ScreenType.AMOLED, 100d, 42d, "Japan");
        tv2 = new Television("Televison2", ScreenType.AMOLED, 200d, 52d, "Japan");
        tv3 = new Television("Televison3", ScreenType.AMOLED, 300d, 62d, "Japan");
        tv4 = new Television("Televison4", ScreenType.AMOLED, 400d, 92d, "Japan");

        c16 = PersonService.generateCustomer();
        c16.setAge(16);
        c17 = PersonService.generateCustomer();
        c17.setAge(17);
        c20 = PersonService.generateCustomer();
        c20.setAge(20);
        c95 = PersonService.generateCustomer();
        c95.setAge(95);

        i16 = new Invoice(c16);
        i16.getProducts().addAll(asList(ph1, ph2, ph3, ph4));

        i17 = new Invoice(c17);
        i17.getProducts().addAll(asList(tv1, tv2, tv3, tv4));

        i20 = new Invoice(c20);
        i20.getProducts().addAll(asList(ph1, ph2, tv3));

        i95 = new Invoice(c95);
        i95.getProducts().addAll(asList(ph3, tv4));
    }

    @Test
    void soldTelevisionsSeveral() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(6, target.soldTelevisions());
    }

    @Test
    void soldTelevisionsNone() {
        invoices.addAll(asList(i16));
        Assertions.assertEquals(0, target.soldTelevisions());
    }

    @Test
    void soldTelephonesSeveral() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(7, target.soldTelephones());
    }

    @Test
    void soldTelephonesNone() {
        invoices.addAll(asList(i17));
        Assertions.assertEquals(0, target.soldTelephones());
    }


    @Test
    void leastInvoicePositive() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(String.format("Least invoice sum is: 100.0, customer: %s", i16.getCustomer().getEmail()),
                target.leastInvoice());
    }

    @Test
    void leastInvoiceNegative() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertNotEquals(String.format("Least invoice sum is: 100.0, customer: %s", i17.getCustomer().getEmail()),
                target.leastInvoice());
    }

    @Test
    void sumPositive() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(1860, target.sum());
    }

    @Test
    void sumNegative() {
        invoices.addAll(asList(i16, i20, i95));
        Assertions.assertNotEquals(1860, target.sum());
    }

    @Test
    void retailInvoicesAll() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(4, target.retailInvoices());
    }


    @Test
    void oneTypeProductInvoicesTwo() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(2, target.oneTypeProductInvoices());
    }

    @Test
    void oneTypeProductInvoicesNone() {
        invoices.addAll(asList(i20, i95));
        Assertions.assertEquals(0, target.oneTypeProductInvoices());
    }

    @Test
    void specifiedTypeProductInvoicesTelephone() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(1, target.specifiedTypeProductInvoices("Telephone"));
    }

    @Test
    void specifiedTypeProductInvoicesTelevision() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(1, target.specifiedTypeProductInvoices("Television"));
    }

    @Test
    void firstThreeInvoicePositive() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(List.of(i16, i17, i20), target.firstThreeInvoice());
    }

    @Test
    void firstThreeInvoiceNegative() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertNotEquals(List.of(i95, i17, i20), target.firstThreeInvoice());
    }

    @Test
    void lowAgeInvoicesPositive() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertEquals(List.of(i16, i17), target.lowAgeInvoices());
    }

    @Test
    void lowAgeInvoicesNegative() {
        invoices.addAll(asList(i16, i17, i20, i95));
        Assertions.assertNotEquals(List.of(i16, i17,i20), target.lowAgeInvoices());
    }

    @Test
    void sortInvoicesPositive() {
        invoices.addAll(asList(i16, i17, i20, i95, i16, i17, i20, i95));
        assertEquals(i95, target.sortInvoices().get(0));
        assertEquals(i95, target.sortInvoices().get(1));

    }
}