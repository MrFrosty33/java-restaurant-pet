package at.frosty.restaurant.table;

import at.frosty.restaurant.common.client.auth.AuthClient;
import at.frosty.restaurant.common.client.auth.ServiceAuthCredentials;
import at.frosty.restaurant.common.security.service.FeignUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "at.frosty.restaurant.table", "at.frosty.restaurant.common.table.model.mapper"
})
@EnableJpaRepositories(basePackages = {
        "at.frosty.restaurant.table.repository"
})
@EntityScan(basePackages = {
        "at.frosty.restaurant.common.table.model"
})
@EnableFeignClients(basePackageClasses = {
        AuthClient.class
})
@EnableConfigurationProperties(ServiceAuthCredentials.class)
@Import(FeignUserDetailsService.class)
public class TableApp {
    public static void main(String[] args) {
        SpringApplication.run(TableApp.class);
    }
}
