package app.dto.converter;

import app.dto.CustomerDto;
import app.model.Customer;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;

public class CustomerToDtoConverter implements Converter<Customer, CustomerDto> {
    @Override
    public CustomerDto convert(Customer source) {
        Objects.requireNonNull(source);
        return new CustomerDto(source.id(), source.name());
    }
}
