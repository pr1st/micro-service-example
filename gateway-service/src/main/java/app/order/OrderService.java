package app.order;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderClient orderClient;

    public OrderService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(orderClient.getResourceUrl(), "orders"));
    }

    public List<OrderDto> getOrdersFromCustomer(String customerId) {
        return orderClient.findByCustomerId(customerId);
    }
}
