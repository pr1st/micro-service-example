package app.product;

import org.springframework.hateoas.Link;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public List<Link> getLinks() {
        return List.of(Link.of(productClient.getResourceUrl(), "products"));
    }

    @Async
    public CompletableFuture<List<ProductDto>> getProductsForOrdersAsync(List<String> productIds) {
        return CompletableFuture.completedFuture(getProductsForOrders(productIds));
    }

    public List<ProductDto> getProductsForOrders(List<String> productIds) {
        return productIds.stream()
                .map(productClient::findById)
                .toList();
    }


}
