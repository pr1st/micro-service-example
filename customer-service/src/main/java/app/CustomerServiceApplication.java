package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Objects;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${redis.hostName}") String hostName,
            @Value("${redis.port}") int port
    ) {
        Objects.requireNonNull(hostName);

        var config = new RedisStandaloneConfiguration(hostName, port);
        return new LettuceConnectionFactory(config);
    }
}
