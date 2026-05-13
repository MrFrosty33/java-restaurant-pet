package at.frosty.restaurant.menu.controller;

import at.frosty.restaurant.common.client.menu.MenuClient;
import at.frosty.restaurant.common.menu.model.dto.DishOrderDto;
import at.frosty.restaurant.menu.service.MenuService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/internal/menu")
@RequiredArgsConstructor
@Validated
@Slf4j
public class InternalController implements MenuClient {
    private final String className = this.getClass().getSimpleName();
    private final MenuService service;

    @GetMapping("/{uuid}")
    public DishOrderDto getDishOrderByUuid(@PathVariable @NotNull UUID uuid) {
        log.info("{}: received getDishOrderByUuid(uuid={}) call", className, uuid);
        return service.getDishOrderByUuid(uuid);
    }
}
