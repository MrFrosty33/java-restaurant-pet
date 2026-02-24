package at.frosty.restaurant.menu.controller;

import at.frosty.restaurant.common.model.menu.Category;
import at.frosty.restaurant.common.model.menu.dto.DishDto;
import at.frosty.restaurant.menu.enums.DishSearchType;
import at.frosty.restaurant.menu.enums.DishSortType;
import at.frosty.restaurant.menu.service.MenuService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicController {
    private final String className = this.getClass().getSimpleName();
    private final MenuService service;

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto getByUuid(@PathVariable @NotNull UUID uuid) {
        log.info("{}: received getByUuid(uuid={}) call", className, uuid);
        return service.getByUuid(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DishDto> getAll(@RequestParam(required = false) Category category,
                                @RequestParam(required = false) DishSortType sortBy) {
        log.info("{}: received getAll(category={}, sortBy={}) call", className, category, sortBy);

        if (category != null) {
            log.trace("{}: redirecting to service.getByCategory()", className);
            return service.getByCategory(category, sortBy);
        }

        log.trace("{}: redirecting to service.getAllSorted()", className);
        return service.getAllSorted(sortBy);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDto> search(@RequestParam @NotNull @Length(min = 1) String query,
                                @RequestParam(defaultValue = "NAME") DishSearchType type) {
        log.info("{}: received search(type={}) call", className, type);
        return service.search(query, type);
    }
}
