package module.service;

import module.model.invoice.Invoice;
import module.model.product.ScreenType;
import module.model.product.Telephone;
import module.model.product.Television;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;

class ShopServiceTest {
    private ShopService target;

    private List<Invoice> invoices;

    private static Predicate<Invoice> isWholesale;

    private Invoice invoice;

    Telephone telephone;
    Television television;

    @BeforeEach
    void setUp() {
        target = ShopService.getInstance();
        invoices = target.getInvoices();
        invoices.clear();

        invoice = Mockito.mock(Invoice.class);

        isWholesale = Mockito.mock(Predicate.class);

        Mockito.when(isWholesale.test(any())).thenReturn(true);
        Invoice.setSalePredicate();

        telephone = new Telephone("PhoneA", ScreenType.LED, 10d, "ModelA");
        television = new Television("TelevisonA", ScreenType.AMOLED, 20d, 42d, "Japan");
    }

    @AfterEach
    void tearDown() {
        Field instance = null;
        try {
            instance = ShopService.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void makeOrdersPositive(){
        target.makeOrders("correctLines.csv", 10, 5);
        Assertions.assertEquals(10, target.getInvoices().size());
    }
}