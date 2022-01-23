package app.order;

import app.customer.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrderClient {
    private static final String resourcePath = "/api/v1/orders";
    private static final String customerIdParam = "customerId";

    private final String resourceUrl;
    private final RestTemplate restTemplate;
    private final String resourceClientUrl;

    public OrderClient(@Value("${services.order.host}") String resourceHost,
                       @Value("${services.order.client-link-host}") String resourceClientLinkHost,
                       RestTemplate restTemplate) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
        this.resourceClientUrl = "http://" + resourceClientLinkHost + resourcePath;
        this.restTemplate = restTemplate;
    }

    public String getResourceUrl() {
        return resourceClientUrl;
    }

    public List<OrderDto> findByCustomerId(String customerId) {
        var requestEntity = RequestEntity.get(resourceUrl + "?customerId=" + customerId)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        return restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<OrderDto>>() {
        }).getBody();
    }
}
