package app.order;

public record OrderDto(
        String id,
        String productId,
        String customerId
) {
}
