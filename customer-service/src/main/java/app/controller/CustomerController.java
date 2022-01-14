package app.controller;

import app.dto.CustomerCreateDto;
import app.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping
    ResponseEntity<CustomerDto> getAll() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); // TODO
    }


    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> getOne(Integer id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); // TODO
    }

    @PostMapping
    ResponseEntity<CustomerDto> create(@RequestBody CustomerCreateDto customerCreateDto) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build(); // TODO
    }
}
