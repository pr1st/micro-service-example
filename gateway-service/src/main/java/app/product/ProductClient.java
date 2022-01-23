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

    public ProductClient(@Value("${services.product.host}") String resourceHost, RestTemplate restTemplate) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
        this.restTemplate = restTemplate;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public ProductDto findById(String productId) {
        return restTemplate.getForObject(resourceUrl + "/{id}", ProductDto.class, productId);
    }
}
