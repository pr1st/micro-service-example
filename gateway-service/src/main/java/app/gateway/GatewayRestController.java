package app.gateway;

import app.customer.CustomerDto;
import app.customer.CustomerService;
import app.order.OrderDto;
import app.order.OrderService;
import app.product.ProductDto;
import app.product.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class GatewayRestController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductService productService;

    public GatewayRestController(CustomerService customerService,
                                 OrderService orderService,
                                 ProductService productService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> apiInfo() throws ExecutionException, InterruptedException {
        var links = CollectionModel.empty();
        links.add(linkTo(GatewayRestController.class).withSelfRel());
        links.add(linkTo(methodOn(GatewayRestController.class)
                .customerOrderProductsInfo(null)).withRel("customerOrders"));
        links.add(customerService.getLinks());
        links.add(productService.getLinks());
        links.add(orderService.getLinks());
        return ResponseEntity.ok(links);
    }

    @GetMapping("/v1/orders/")
    public ResponseEntity<?> customerOrderProductsInfo(@RequestParam("client_id") String clientId) throws ExecutionException, InterruptedException {
        var customerFuture = customerService.getCustomerAsync(clientId);
        var ordersFuture = orderService.getOrdersFromCustomerAsync(clientId);

        var productsFuture = ordersFuture.thenComposeAsync(orders ->
                productService.getProductsForOrdersAsync(orders.stream().map(OrderDto::productId).toList()));

        CompletableFuture.allOf(customerFuture, ordersFuture, productsFuture).get();
        var customer = customerFuture.get();
        var orders = ordersFuture.get();
        var products = productsFuture.get();

        var orderInfoList = orders.stream()
                .map(o -> products.stream()
                        .filter(p -> p.id().equals(o.productId()))
                        .findAny()
                        .map(p -> new CustomerOrderInfoDto.OrderInfo(o.id(), p.id(), p.title()))
                        .orElse(null)
                ).filter(Objects::nonNull)
                .toList();
        var customerOrderInfoDto = new CustomerOrderInfoDto(customer.name(), orderInfoList);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerOrderInfoDto);
    }
}
