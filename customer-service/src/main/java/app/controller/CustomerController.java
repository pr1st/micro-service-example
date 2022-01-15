package app.controller;

import app.dto.CustomerCreateDto;
import app.dto.CustomerDto;
import app.dto.converter.CustomerToDtoConverter;
import app.model.Customer;
import app.repository.CustomerRepository;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(CustomerController.PATH)
public class CustomerController {
    public static final String PATH = "/api/v1/users";

    private final CustomerRepository customerRepository;
    private final CustomerToDtoConverter customerToDtoConverter;

    public CustomerController(CustomerRepository customerRepository,
                              CustomerToDtoConverter customerToDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerToDtoConverter = customerToDtoConverter;
    }

    @GetMapping
    ResponseEntity<List<CustomerDto>> getAll() {
        var customerDtoList = IterableConverter.toList(customerRepository.findAll())
                .stream()
                .map(customerToDtoConverter::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerDtoList);
    }


    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> getOne(@PathVariable String id) {
        var customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerToDtoConverter.convert(customer.get()));
    }

    @PostMapping
    ResponseEntity<CustomerDto> create(@RequestBody CustomerCreateDto customerCreateDto, UriComponentsBuilder uriBuilder) {
        var saved = customerRepository.save(new Customer(customerCreateDto.name()));

        return ResponseEntity.created(uriBuilder.path(PATH + "/" + saved.id()).buildAndExpand().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerToDtoConverter.convert(saved));
    }
}
