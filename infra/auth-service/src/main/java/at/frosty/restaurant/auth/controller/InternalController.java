package at.frosty.restaurant.auth.controller;

import at.frosty.restaurant.auth.service.AuthService;
import at.frosty.restaurant.common.feign.AuthClient;
import at.frosty.restaurant.common.security.model.RestaurantUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class InternalController implements AuthClient {
    private final String className = this.getClass().getSimpleName();
    private final AuthService service;

    @GetMapping("/{username}")
    public RestaurantUserDto getUserByUsername(@PathVariable @NotNull String username) {
        log.info("{}: received getUserByUsername(username={}) call", className, username);
        return service.getUserByUsername(username);
    }
}
