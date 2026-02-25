package at.frosty.restaurant.menu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"at.frosty.restaurant.menu", "at.frosty.restaurant.common.model.menu"})
@EntityScan(basePackages = "at.frosty.restaurant.common.model.menu")
public class MenuApp {
    public static void main(String[] args) {
        SpringApplication.run(MenuApp.class);
    }
}
