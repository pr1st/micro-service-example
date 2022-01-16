package app;

import app.handler.ProductHandler;
import app.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	@Bean
	public RouterFunction<ServerResponse> orderRouter(ProductHandler productHandler) {
		return route()
				.nest(path("/api/v1/products").and(accept(APPLICATION_JSON)), builder -> builder
						.GET("/{id}", productHandler::getProductById)
						.GET("", productHandler::getAllProducts)
						.POST("", contentType(APPLICATION_JSON), productHandler::createProduct)
				).build();
	}

	@Bean
	public ReactiveRedisConnectionFactory redisConnectionFactory(
			@Value("${redis.hostName}") String hostName,
			@Value("${redis.port}") int port
	) {
		return new LettuceConnectionFactory(hostName, port);
	}

	@Bean
	ReactiveRedisTemplate<String, Product> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(Product.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Product> builder =
				RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Product> context = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}
}
