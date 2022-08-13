package module.service;

import module.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    public static Customer generateCustomer(){
        Customer customer = new Customer();
        String email = customer.getId().substring(0, 5).toLowerCase() + "@gmail.com";
        customer.setEmail(email);
        int min = 14;
        int max = 99;
        int age = (int) Math.floor(min + (1 + (max - min))*Math.random());
        customer.setAge(age);
        return customer;
    }
}
