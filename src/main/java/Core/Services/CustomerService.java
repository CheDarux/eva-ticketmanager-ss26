package Core.Services;

import Core.Models.exceptions.CustomerException;
import Core.Interfaces.CustomerServiceInterface;
import java.time.LocalDate;
import java.util.*;
import Core.Models.Customer;

public class CustomerService implements CustomerServiceInterface {

    private final Map<UUID, Customer> customersById = new HashMap<>();

    public Customer createCustomer(
        String username,
        String email,
        LocalDate dateOfBirth
    ) throws IllegalArgumentException {

        boolean multipleAtSymbols = email.chars().filter(ch -> ch == '@').count() > 1;

        if (!email.contains("@") || multipleAtSymbols) {
            throw new CustomerException("Invalid email");
        }

        if(dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            throw new CustomerException("User has to be 18 years old");
        }

        Customer customer = new Customer(UUID.randomUUID(), username, email, dateOfBirth);

        return new Customer(
            customer.getId(),
            customer.getUsername(),
            customer.getEmail(),
            customer.getDateOfBirth()
        );
    }

    public Customer getCustomerById(UUID id) throws CustomerException {

        //TODO

        return null;
    }


    public void updateCustomer(Customer customer) throws CustomerException {

        //TODO

    }



    public void deleteCustomer(UUID id) throws IllegalArgumentException {

        //TODO

    }

    public List<Customer> getAllCustomers() {

        //TODO

        return null;
    }

    public void deleteAllCustomers() {

        //TODO

    }

}
