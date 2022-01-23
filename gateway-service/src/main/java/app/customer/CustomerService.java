package app.customer;

import org.springframework.hateoas.Link;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {

    private final CustomerClient customerClient;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(customerClient.getResourceUrl(), "customers"));
    }

    @Async
    public CompletableFuture<CustomerDto> getCustomerAsync(String customerId) {
        return CompletableFuture.completedFuture(customerClient.findById(customerId));
    }

    public CustomerDto getCustomer(String customerId) {
        return customerClient.findById(customerId);
    }
}
