package app.product;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(productClient.getResourceUrl(), "products"));
    }

    public List<ProductDto> getProductsForOrders(List<String> productIds) {
        return productIds.stream()
                .map(productClient::findById)
                .toList();
    }
}
