package app.repository;

import app.model.Product;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProductRepository {
    private final ReactiveRedisOperations<String, Product> redisOperations;

    public ProductRepository(ReactiveRedisOperations<String, Product> redisOperations) {
        this.redisOperations = redisOperations;
    }

    public Mono<Product> create(String title) {
        var id = UUID.randomUUID().toString();
        return redisOperations.opsForValue().set(id, new Product(id, title)).map(ignored -> new Product(id, title));
        // TODO
        // generating {id} until product will be saved to redis (until setIfAbsent will return true)
        // and returning mono with saved product
        // because of manual generation of {id} and possibility of key duplicate in database
//        return Flux.<String>generate(sink -> sink.next(UUID.randomUUID().toString()))
//                .log()
//                .flatMap(id -> {
//                    var product = new Product(id, title);
//                    return Mono.zip(redisOperations.opsForValue().set(id, product), Mono.just(product));
//                })
//                .skipUntil(tuple -> TRUE.equals(tuple.getT1()))
//                .map(Tuple2::getT2)
//                .next();
    }

    public Mono<Product> findById(String id) {

//        var block = redisOperations.opsForValue().get(id).block();
        return redisOperations.opsForValue().get(id);
    }

    public Flux<Product> findAll() {
        return redisOperations.scan(ScanOptions.NONE)
                .flatMap(id -> redisOperations.opsForValue().get(id));
    }
}