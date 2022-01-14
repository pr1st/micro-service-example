package app.dto.converter;

import app.dto.CustomerDto;
import app.model.Customer;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class CustomerToDtoConverter implements Converter<CustomerDto, Customer> {
    @Override
    public Customer convert(CustomerDto source) {
        Objects.requireNonNull(source);
        return new Customer(source.id(), source.name());
    }
}
