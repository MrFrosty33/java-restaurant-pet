package at.frosty.restaurant.menu.repository;

import at.frosty.restaurant.common.menu.model.Category;
import at.frosty.restaurant.common.menu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {
    List<Dish> findAllByActive(boolean active);
    List<Dish> findAllByActiveAndCategory(boolean active, Category category);
    Optional<Dish> findByNameIgnoreCase(String name);
    List<Dish> findByNameIgnoreCaseContainingAndActiveTrue(String name);
}
