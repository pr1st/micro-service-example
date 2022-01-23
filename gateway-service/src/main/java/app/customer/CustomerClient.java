package app.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerClient {
    private static final String resourcePath = "/api/v1/users";

    private final String resourceUrl;
    private final String resourceClientUrl;
    private final RestTemplate restTemplate;

    public CustomerClient(@Value("${services.customer.host}") String resourceHost,
                          @Value("${services.customer.client-link-host}") String resourceClientLinkHost,
                          RestTemplate restTemplate) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
        this.resourceClientUrl = "http://" + resourceClientLinkHost + resourcePath;
        this.restTemplate = restTemplate;
    }

    public String getResourceUrl() {
        return resourceClientUrl;
    }

    public CustomerDto findById(String customerId) {
        return restTemplate.getForObject(resourceUrl + "/{id}", CustomerDto.class, customerId);
    }
}
