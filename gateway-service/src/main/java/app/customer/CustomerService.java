package app.customer;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerClient customerClient;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(customerClient.getResourceUrl(), "customers"));
    }

    public CustomerDto getCustomer(String customerId) {
        return customerClient.findById(customerId);
    }
}
