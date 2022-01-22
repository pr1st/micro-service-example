package app.gateway;

import java.util.List;

public record CustomerOrderInfoDto(
        String customerName,
        List<OrderInfo> orders
) {
    public static record OrderInfo(
        String id,
        String productId,
        String productTitle
    ) {
    }
}
