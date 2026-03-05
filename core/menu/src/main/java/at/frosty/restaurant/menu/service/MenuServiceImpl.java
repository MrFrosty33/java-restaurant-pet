package at.frosty.restaurant.menu.service;

import at.frosty.restaurant.common.menu.exception.ErrorType;
import at.frosty.restaurant.common.menu.exception.ForbiddenException;
import at.frosty.restaurant.common.menu.exception.NotFoundException;
import at.frosty.restaurant.common.menu.model.Category;
import at.frosty.restaurant.common.menu.model.Dish;
import at.frosty.restaurant.common.menu.model.dto.DishDto;
import at.frosty.restaurant.common.menu.model.dto.UpdateDishDto;
import at.frosty.restaurant.common.menu.model.mapper.DishMapper;
import at.frosty.restaurant.menu.enums.DishSearchType;
import at.frosty.restaurant.menu.enums.DishSortType;
import at.frosty.restaurant.menu.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final String className = this.getClass().getSimpleName();
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    private final Map<DishSortType, Comparator<DishDto>> sortsMap = Map.of(
            DishSortType.PRICE, Comparator.comparing(DishDto::getPrice),
            DishSortType.NAME, Comparator.comparing(DishDto::getName)
    );

    @Override
    public DishDto getDishByUuid(UUID uuid) {
        DishDto result = dishMapper.toDto(dishRepository.findById(uuid).orElseThrow(() -> {
            log.warn("{}: unable to find dish with uuid={}", className, uuid);
            return new NotFoundException("dish with uuid=" + uuid + " not found", ErrorType.DISH_NOT_FOUND);
        }));

        if (!result.isActive()) {
            log.warn("{}: getByUuid(uuid={}) attempt to receive disabled dish", className, uuid);
            throw new ForbiddenException("dish with uuid=" + uuid + " is deactivated", ErrorType.DISH_DEACTIVATED);
        }

        log.info("{}: getByUuid(uuid={}) result={}", className, uuid, result);
        return result;
    }

    @Override
    public List<DishDto> getAllDishesSorted(DishSortType sortBy) {
        boolean active = true;
        List<DishDto> result = getDishes(active, null, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getAllDishesSorted(sortBy={}) unable to find any active dish", className, sortBy);
            throw new NotFoundException("there are no active dishes in DB", ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getAllDishesSorted(sortBy={}) result={}", className, sortBy, result);
        return result;
    }

    @Override
    public List<DishDto> getDeactivatedDishesSorted(DishSortType sortBy) {
        boolean active = false;
        List<DishDto> result = getDishes(active, null, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDeactivatedDishesSorted(sortBy={}) unable to find any deactivated dish", className, sortBy);
            throw new NotFoundException("there are no deactivated dishes in DB", ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDeactivatedDishesSorted(sortBy={}) result={}", className, sortBy, result);
        return result;
    }

    @Override
    public List<DishDto> getDishesByCategory(Category category, DishSortType sortBy) {
        boolean active = true;
        List<DishDto> result = getDishes(active, category, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDishesByCategory(category={}, sortBy={}) unable to find any active dish", className, category, sortBy);
            throw new NotFoundException("there are no active dishes in DB with category=" + category, ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDishesByCategory(category={}, sortBy={}) result={}", className, category, sortBy, result);
        return result;
    }

    @Override
    public List<DishDto> getDeactivatedDishesByCategory(Category category, DishSortType sortBy) {
        boolean active = false;
        List<DishDto> result = getDishes(active, category, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDeactivatedDishesByCategory(sortBy={}) unable to find any deactivated dish", className, sortBy);
            throw new NotFoundException("there are no deactivated dishes in DB with category=" + category, ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDeactivatedDishesByCategory(sortBy={}) result={}", className, sortBy, result);
        return result;
    }

    private List<DishDto> getDishes(boolean active, Category category, DishSortType sortBy) {
        if (category != null) {
            return dishRepository.findAllByActiveAndCategory(active, category).stream()
                    .map(dishMapper::toDto)
                    .sorted(sortsMap.get(sortBy))
                    .toList();
        } else {
            return dishRepository.findAllByActive(active).stream()
                    .map(dishMapper::toDto)
                    .sorted(sortsMap.get(sortBy))
                    .toList();
        }
    }

    @Override
    public List<DishDto> searchDishes(String query, DishSearchType type) {
        return List.of();
    }


    @Override
    public DishDto createDish(DishDto dishDto) {
        return null;
    }

    @Override
    public DishDto updateDish(UUID uuid, UpdateDishDto updateDishDto) {
        return null;
    }

    @Override
    public void softDeleteDish(UUID uuid) {

    }
}
