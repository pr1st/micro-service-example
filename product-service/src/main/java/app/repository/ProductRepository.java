package app.repository;

import app.model.Product;
import org.reactivestreams.Subscription;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Repository
public class ProductRepository {
    private final ReactiveRedisOperations<String, Product> redisOperations;

    public ProductRepository(ReactiveRedisOperations<String, Product> redisOperations) {
        this.redisOperations = redisOperations;
    }

    public Mono<Product> create(String title) {
        // generating {id} until product will be saved to redis (until setIfAbsent will return true)
        // and returning mono with saved product
        // not straightforward code done because of manual generation of {id} and possibility of key duplicate in database
        return Flux.<String>generate(sink -> sink.next(UUID.randomUUID().toString()))
                .flatMap(id -> {
                    var product = new Product(id, title);
                    return Mono.zip(redisOperations.opsForValue().setIfAbsent(id, product), Mono.just(product));
                })
                .skipUntil(tuple -> TRUE.equals(tuple.getT1()))
                .map(Tuple2::getT2)
                .next();
    }

    public Mono<Product> findById(String id) {
        return redisOperations.opsForValue().get(id);
    }

    public Flux<Product> findAll() {
        return redisOperations.scan(ScanOptions.NONE)
                .flatMap(id -> redisOperations.opsForValue().get(id));
    }
}