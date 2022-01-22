package app.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductClient {
    private static final String resourcePath = "/api/v1/products";

    private final String resourceUrl;

    public ProductClient(@Value("${services.product.host}") String resourceHost) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }
}
