package at.frosty.restaurant.common.client.menu;

import at.frosty.restaurant.common.menu.model.dto.DishOrderDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "menu-service", fallbackFactory = MenuFallback.class)
public interface MenuClient {
    @GetMapping("/internal/menu/{id}")
    DishOrderDto getDishOrderById(@PathVariable @NotNull UUID id);
}
