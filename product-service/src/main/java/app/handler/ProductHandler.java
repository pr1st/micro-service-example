package app.handler;

import app.dto.ProductCreateDto;
import app.dto.ProductDto;
import app.dto.converter.ProductToDtoConverter;
import app.model.Product;
import app.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@Component
public class ProductHandler {
    private final ProductRepository productRepository;
    private final ProductToDtoConverter converterToDto;

    public ProductHandler(ProductRepository orderRepository, ProductToDtoConverter productToDtoConverter) {
        this.productRepository = orderRepository;
        this.converterToDto = productToDtoConverter;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productRepository.findAll().map(converterToDto::convert), ProductDto.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        var id = request.pathVariable("id");

        Function<Product, Mono<ServerResponse>> serverMonoResponse = (Product p) -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(converterToDto.convert(p));

        return productRepository.findById(id)
                .single()
                .flatMap(serverMonoResponse)
                .onErrorResume(t -> {
                    System.err.println(t.getMessage());
                    return ServerResponse.notFound().build();
                });
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        // sadge code :( (hardly readable, as do not found anything better)
        var body = request.body(BodyExtractors.toMono(ProductCreateDto.class)).single();
        var created = body.flatMap(dto -> productRepository.create(dto.title()));

        Function<Product, Mono<ServerResponse>> serverMonoResponse = (Product p) ->
                ServerResponse.created(
                                request.uriBuilder()
                                        .path("/{id}")
                                        .build(p.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(converterToDto.convert(p));

        return created.flatMap(serverMonoResponse);
    }
}
