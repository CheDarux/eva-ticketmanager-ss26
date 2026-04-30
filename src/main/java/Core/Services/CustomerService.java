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

        customersById.put(customer.getId(),customer);

        return new Customer(
            customer.getId(),
            customer.getUsername(),
            customer.getEmail(),
            customer.getDateOfBirth()
        );
    }

    public Customer getCustomerById(UUID id) throws CustomerException {


        Customer customer = customersById.get(id);
        if (customer != null) {
            return new Customer(
                    id,
                    customer.getUsername(),
                    customer.getEmail(),
                    customer.getDateOfBirth()
            );
        } else {
            throw new CustomerException("Customer not found.");
        }

    }


    public void updateCustomer(Customer customer) throws CustomerException {

        UUID id = customer.getId();
        String email = customer.getEmail();
        LocalDate dateOfBirth = customer.getDateOfBirth();

        boolean multipleAtSymbols = email.chars().filter(ch -> ch == '@').count() > 1;

        if (!email.contains("@") || multipleAtSymbols) {
            throw new CustomerException("Invalid email");
        }
        if (customersById.get(customer.getId()) == null) {
            throw new CustomerException("Customer does not exist");
        }

        if(dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            throw new CustomerException("User has to be 18 years old");
        }

        customersById.put(customer.getId(), customer);


    }



    public void deleteCustomer(UUID id) throws IllegalArgumentException {

        customersById.remove(id);

        return;

    }

    public List<Customer> getAllCustomers() {

        List<Customer> allCustomers = new ArrayList<>();

        for (Customer customer : customersById.values()) {
            allCustomers.add(new Customer(customer.getId(),
                    customer.getUsername(),
                    customer.getEmail(),
                    customer.getDateOfBirth()));
        }

        return allCustomers;


    }

    public void deleteAllCustomers() {

        customersById.clear();
        return;

    }

}
