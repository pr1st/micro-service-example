package app.dto;

public record OrderCreateDto(
        String productId,
        String customerId
) {
}
