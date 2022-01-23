package app.order;

import org.springframework.hateoas.Link;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    private final OrderClient orderClient;

    public OrderService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(orderClient.getResourceUrl(), "orders"));
    }

    @Async
    public CompletableFuture<List<OrderDto>> getOrdersFromCustomerAsync(String productId) {
        return CompletableFuture.completedFuture(getOrdersFromCustomer(productId));
    }

    public List<OrderDto> getOrdersFromCustomer(String customerId) {
        return orderClient.findByCustomerId(customerId);
    }
}
