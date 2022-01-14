package app.repository;


import app.model.Customer;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface CustomerRepository extends KeyValueRepository<Customer, Integer> {
}
