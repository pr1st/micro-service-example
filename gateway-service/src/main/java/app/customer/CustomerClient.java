package app.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerClient {
    private static final String resourcePath = "/api/v1/users";

    private final String resourceUrl;

    public CustomerClient(@Value("${services.customer.host}") String resourceHost) {
        this.resourceUrl = "http://" + resourceHost + resourcePath;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }
}
