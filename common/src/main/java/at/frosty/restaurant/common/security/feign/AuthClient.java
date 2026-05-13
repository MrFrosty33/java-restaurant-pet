package at.frosty.restaurant.common.security.feign;

import at.frosty.restaurant.common.security.feign.fallback.AuthFallback;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", fallbackFactory = AuthFallback.class)
public interface AuthClient {
    @GetMapping("/internal/auth/{username}")
    UserDetails getUserByUsername(@PathVariable @NotNull String username);
}
