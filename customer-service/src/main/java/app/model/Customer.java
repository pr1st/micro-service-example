package app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("customers")
public record Customer(
        @Id String id,
        String name
) {
    public Customer(String name) {
        this(null, name);
    }
}
