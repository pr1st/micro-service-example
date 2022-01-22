package app.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderClient {
    private static final String resourcePath = "/api/v1/orders";
    private static final String customerIdParam = "customerId";

    private final String resourceUrl;

    public OrderClient(@Value("${services.order.host}") String resourceHost) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }
}
