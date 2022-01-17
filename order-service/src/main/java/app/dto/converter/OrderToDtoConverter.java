package app.dto.converter;

import app.dto.OrderDto;
import app.model.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToDtoConverter implements Converter<Order, OrderDto> {
    @Override
    public OrderDto convert(Order source) {
        return new OrderDto(source.getId(), source.getProductId(), source.getCustomerId());
    }
}
