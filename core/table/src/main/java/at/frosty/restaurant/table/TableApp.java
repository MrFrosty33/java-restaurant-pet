package at.frosty.restaurant.table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"at.frosty.restaurant.table", "at.frosty.restaurant.common.table",
        "at.frosty.restaurant.common.security"})
@EnableJpaRepositories(basePackages = {"at.frosty.restaurant.table.repository", "at.frosty.restaurant.common.security.repository"})
@EntityScan(basePackages = {"at.frosty.restaurant.common.table.model", "at.frosty.restaurant.common.security.model"})
public class TableApp {
    public static void main(String[] args) {
        SpringApplication.run(TableApp.class);
    }
}
