package app.controller;

import app.dto.OrderCreateDto;
import app.dto.converter.OrderToDtoConverter;
import app.model.Order;
import app.repository.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderToDtoConverter converter;

    public OrderController(OrderRepository orderRepository, OrderToDtoConverter converter) {
        this.orderRepository = orderRepository;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String customerId) {
        if (customerId != null) return getByCustomerId(customerId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                        .map(converter::convert)
                        .collect(Collectors.toList()));
    }

    private ResponseEntity<?> getByCustomerId(String customerId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderRepository.findOrderByCustomerId(customerId)
                        .stream()
                        .map(converter::convert)
                        .collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        var orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(converter.convert(orderOptional.get()));
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderCreateDto createDto, UriComponentsBuilder uriBuilder) {
        var saved = orderRepository.save(new Order(null, createDto.customerId(), createDto.productId()));
        return ResponseEntity.created(uriBuilder.path("/{id}").buildAndExpand(saved.getId()).toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(converter.convert(saved));
    }
}
