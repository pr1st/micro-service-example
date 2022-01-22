package app.gateway;

import app.customer.CustomerService;
import app.order.OrderService;
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

import java.util.List;

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
    public ResponseEntity<?> apiInfo() {
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
    public ResponseEntity<?> customerOrderProductsInfo(@RequestParam("client_id") String clientId) {
            var d = new CustomerOrderInfoDto("1",
                    List.of(new CustomerOrderInfoDto.OrderInfo("1", "2", "3"),
                            new CustomerOrderInfoDto.OrderInfo("2", "6", "4"))
            );
        var of = EntityModel.of(d);
        of.add(Link.of("123", "2"));
        return ResponseEntity.ok(d);
    }
}
