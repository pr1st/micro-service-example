package app.dto.converter;

import app.dto.OrderWithoutCustomerDto;
import app.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToDtoWithoutCustomerConverter implements Converter<Order, OrderWithoutCustomerDto> {
    @Override
    public OrderWithoutCustomerDto convert(Order source) {
        return new OrderWithoutCustomerDto(source.getId(),source.getProductId());
    }
}
