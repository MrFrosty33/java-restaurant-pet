package at.frosty.restaurant.menu.service;

import at.frosty.restaurant.common.menu.model.Category;
import at.frosty.restaurant.common.menu.model.dto.DishDto;
import at.frosty.restaurant.common.menu.model.dto.DishOrderDto;
import at.frosty.restaurant.common.menu.model.dto.UpdateDishDto;
import at.frosty.restaurant.menu.enums.DishSearchType;
import at.frosty.restaurant.menu.enums.DishSortType;

import java.util.List;
import java.util.UUID;

public interface MenuService {
    // public methods
    DishDto getDishByUuid(UUID uuid);
    List<DishDto> getAllDishesSorted(DishSortType sortBy);
    List<DishDto> getDishesByCategory(Category category, DishSortType sortBy);
    List<DishDto> searchDishes(String query, DishSearchType type);

    // manager methods
    List<DishDto> getDeactivatedDishesSorted(DishSortType sortBy);
    List<DishDto> getDeactivatedDishesByCategory(Category category, DishSortType sortBy);
    DishDto createDish(DishDto dishDto);
    DishDto updateDish(UUID uuid, UpdateDishDto updateDishDto);
    DishDto activateDish(UUID uuid);
    void softDeleteDish(UUID uuid);

    //internal methods
    DishOrderDto getDishOrderByUuid(UUID uuid);
}
