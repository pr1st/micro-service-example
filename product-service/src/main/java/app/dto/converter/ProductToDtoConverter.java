package app.dto.converter;

import app.dto.ProductDto;
import app.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductToDtoConverter implements Converter<Product, ProductDto> {
    @Override
    @NonNull
    public ProductDto convert(Product source) {
        Objects.requireNonNull(source);
        return new ProductDto(source.id(), source.title());
    }
}
