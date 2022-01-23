package app.product;

import app.customer.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {
    private static final String resourcePath = "/api/v1/products";

    private final String resourceUrl;
    private final RestTemplate restTemplate;
    private final String resourceClientUrl;

    public ProductClient(@Value("${services.product.host}") String resourceHost,
                         @Value("${services.product.client-link-host}") String resourceClientLinkHost,
                         RestTemplate restTemplate) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
        this.resourceClientUrl = "http://" + resourceClientLinkHost + resourcePath;
        this.restTemplate = restTemplate;
    }

    public String getResourceUrl() {
        return resourceClientUrl;
    }

    public ProductDto findById(String productId) {
        return restTemplate.getForObject(resourceUrl + "/{id}", ProductDto.class, productId);
    }
}
