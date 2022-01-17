package app.gateway;

import app.customer.CustomerService;
import app.order.OrderService;
import app.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @GetMapping("/api/v1/orders/")
    public ResponseEntity<?> customerOrderProductsInfo(@RequestParam String client_id) {
        return null;
    }
}
