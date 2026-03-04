package at.frosty.restaurant.menu.service;

import at.frosty.restaurant.common.model.menu.Category;
import at.frosty.restaurant.common.model.menu.dto.DishDto;
import at.frosty.restaurant.common.model.menu.dto.UpdateDishDto;
import at.frosty.restaurant.menu.enums.DishSearchType;
import at.frosty.restaurant.menu.enums.DishSortType;

import java.util.List;
import java.util.UUID;

public interface MenuService {
    // public methods
    DishDto getByUuid(UUID uuid);
    List<DishDto> getAllSorted(DishSortType sortBy);
    List<DishDto> getByCategory(Category category, DishSortType sortBy);
    List<DishDto> search(String query, DishSearchType type);
    // admin methods
    List<DishDto> getDeactivatedDishes();
    DishDto createDish(DishDto dishDto);
    DishDto updateDish(UUID uuid, UpdateDishDto updateDishDto);
    void softDeleteDish(UUID uuid);
}
