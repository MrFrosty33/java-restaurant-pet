package at.frosty.restaurant.menu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"at.frosty.restaurant.menu", "at.frosty.restaurant.common.menu",
        "at.frosty.restaurant.common.feign", "at.frosty.restaurant.common.security"})
@EnableJpaRepositories(basePackages = {"at.frosty.restaurant.menu.repository", "at.frosty.restaurant.common.security.repository"})
@EntityScan(basePackages = {"at.frosty.restaurant.common.menu.model", "at.frosty.restaurant.common.security.model"})
@EnableFeignClients(basePackages = {"at.frosty.restaurant.common.feign"})
@ConfigurationPropertiesScan(basePackages = {"at.frosty.restaurant.common.feign.config"})
public class MenuApp {
    public static void main(String[] args) {
        SpringApplication.run(MenuApp.class);
    }
}
