package at.frosty.restaurant.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"at.frosty.restaurant.auth", "at.frosty.restaurant.common.feign"})
@EntityScan(basePackages = {"at.frosty.restaurant.common.security.model"})
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class);
    }
}
