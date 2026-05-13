package at.frosty.restaurant.common.client.auth;

import at.frosty.restaurant.common.security.model.dto.RestaurantUserDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", fallbackFactory = AuthFallback.class, configuration = FeignAuthConfig.class)
public interface AuthClient {
    @GetMapping("/internal/auth/{username}")
    RestaurantUserDto getUserByUsername(@PathVariable @NotNull String username);
}
