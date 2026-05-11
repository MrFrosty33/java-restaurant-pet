package at.frosty.restaurant.menu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"at.frosty.restaurant.menu", "at.frosty.restaurant.common.menu"})
@EntityScan(basePackages = "at.frosty.restaurant.common.menu.model")
public class MenuApp {
    public static void main(String[] args) {
        SpringApplication.run(MenuApp.class);
    }
}
