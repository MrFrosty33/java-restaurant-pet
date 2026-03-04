package at.frosty.restaurant.menu.controller;

import at.frosty.restaurant.common.menu.model.dto.DishDto;
import at.frosty.restaurant.common.menu.model.dto.UpdateDishDto;
import at.frosty.restaurant.menu.service.MenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/menu")
@PreAuthorize("hasRole('MANAGER')")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ManagerController {
    private final String className = this.getClass().getSimpleName();
    private final MenuService service;

    @GetMapping("/deactivated")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDto> getDeactivatedDishes(){
        log.info("{}: received getDeactivatedDishes() call", className);
        return service.getDeactivatedDishes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishDto createDish(@RequestBody @Valid @NotNull DishDto dishDto) {
        log.info("{}: received createDish(dishDto={}) call", className, dishDto);
        return service.createDish(dishDto);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto updateDish(@PathVariable @NotNull UUID uuid,
                              @RequestBody @Valid @NotNull UpdateDishDto updateDishDto) {
        log.info("{}: received updateDish(uuid={}, updateDishDto={}) call", className, uuid, updateDishDto);
        return service.updateDish(uuid, updateDishDto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteDish(@PathVariable @NotNull UUID uuid) {
        log.info("{}: received softDeleteDish(uuid={}) call", className, uuid);
        service.softDeleteDish(uuid);
    }
}
