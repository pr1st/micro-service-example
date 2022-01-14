package app.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("customer")
public record Customer(
        Integer id,
        String name
) {
}
