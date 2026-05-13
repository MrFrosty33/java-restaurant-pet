package at.frosty.restaurant.menu.service;

import at.frosty.restaurant.common.exception.ConflictException;
import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.ForbiddenException;
import at.frosty.restaurant.common.exception.InternalServerException;
import at.frosty.restaurant.common.exception.NotFoundException;
import at.frosty.restaurant.common.menu.model.Category;
import at.frosty.restaurant.common.menu.model.Dish;
import at.frosty.restaurant.common.menu.model.dto.DishDto;
import at.frosty.restaurant.common.menu.model.dto.DishOrderDto;
import at.frosty.restaurant.common.menu.model.dto.UpdateDishDto;
import at.frosty.restaurant.common.menu.model.mapper.DishMapper;
import at.frosty.restaurant.menu.enums.DishSearchType;
import at.frosty.restaurant.menu.enums.DishSortType;
import at.frosty.restaurant.menu.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Dish getDishEntity(UUID uuid) {
        return dishRepository.findById(uuid).orElseThrow(() -> {
            log.warn("{}: unable to find dish with uuid={}", className, uuid);
            return new NotFoundException("dish with uuid=" + uuid + " not found", ErrorType.DISH_NOT_FOUND);
        });
    }

    @Override
    public DishDto getDishByUuid(UUID uuid) {
        DishDto result = dishMapper.toDto(getDishEntity(uuid));

        if (!result.isActive()) {
            log.warn("{}: getByUuid() attempt to receive disabled dish", className);
            throw new ForbiddenException("dish with uuid=" + uuid + " is deactivated", ErrorType.DISH_DEACTIVATED);
        }

        log.info("{}: getByUuid() result={}", className, result);
        return result;
    }

    @Override
    public List<DishDto> getAllDishesSorted(DishSortType sortBy) {
        boolean active = true;
        List<DishDto> result = getDishes(active, null, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getAllDishesSorted() unable to find any active dish", className);
            throw new NotFoundException("there are no active dishes in DB", ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getAllDishesSorted() resultSize={}", className, result.size());
        log.debug("{}: getAllDishesSorted() result={}", className, result);
        return result;
    }

    @Override
    public List<DishDto> getDeactivatedDishesSorted(DishSortType sortBy) {
        boolean active = false;
        List<DishDto> result = getDishes(active, null, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDeactivatedDishesSorted() unable to find any deactivated dish", className);
            throw new NotFoundException("there are no deactivated dishes in DB", ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDeactivatedDishesSorted() resultSize={}", className, result.size());
        log.debug("{}: getDeactivatedDishesSorted() result={}", className, result);
        return result;
    }

    @Override
    public List<DishDto> getDishesByCategory(Category category, DishSortType sortBy) {
        boolean active = true;
        List<DishDto> result = getDishes(active, category, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDishesByCategory() unable to find any active dish", className);
            throw new NotFoundException("there are no active dishes in DB with category=" + category, ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDishesByCategory() resultSize={}", className, result.size());
        log.debug("{}: getDishesByCategory() result={}", className, result);
        return result;
    }

    @Override
    public List<DishDto> getDeactivatedDishesByCategory(Category category, DishSortType sortBy) {
        boolean active = false;
        List<DishDto> result = getDishes(active, category, sortBy);

        if (result.isEmpty()) {
            log.warn("{}: getDeactivatedDishesByCategory() unable to find any deactivated dish", className);
            throw new NotFoundException("there are no deactivated dishes in DB with category=" + category, ErrorType.DISH_NOT_FOUND);
        }

        log.info("{}: getDeactivatedDishesByCategory() resultSize={}", className, result.size());
        log.debug("{}: getDeactivatedDishesByCategory() result={}", className, result);
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
        if(type.equals(DishSearchType.NAME)) {
            List<DishDto> result = dishRepository.findByNameIgnoreCaseContainingAndActiveTrue(query).stream()
                    .map(dishMapper::toDto)
                    .toList();

            log.info("{}: searchDishes() resultSize={}", className, result.size());
            log.debug("{}: searchDishes() result={}", className, result);
            return result;
        }

        log.warn("{}: searchDishes() type not supported", className);
        throw new InternalServerException("dish search type=" + type + " not supported", ErrorType.INTERNAL_ERROR);
    }

    @Override
    public DishOrderDto getDishOrderByUuid(UUID uuid) {
        Dish entity = getDishEntity(uuid);

        if(!entity.isActive()) {
            log.warn("{}: getDishOrderByUuid() unable to find active dish with uuid={}", className, uuid);
            throw new NotFoundException("dish with uuid=" + uuid + " either not exists or is deactivated", ErrorType.DISH_NOT_FOUND);
        }

        return dishMapper.toOrderDto(entity);
    }

    @Override
    @Transactional
    public DishDto createDish(DishDto dishDto) {
        if (dishRepository.findByNameIgnoreCase(dishDto.getName()).isPresent()) {
            log.warn("{}: createDish() refused, dish with name={} already exists",
                    className, dishDto.getName());
            throw new ConflictException("dish with name=" + dishDto.getName() + " already exists", ErrorType.DISH_ALREADY_EXISTS);
        }

        Dish entity = dishMapper.toEntity(dishDto);
        entity = dishRepository.save(entity);
        DishDto result = dishMapper.toDto(entity);

        log.info("{}: createDish() result={}", className, result);
        return result;
    }

    @Override
    @Transactional
    public DishDto updateDish(UUID uuid, UpdateDishDto updateDishDto) {
        Dish entity = getDishEntity(uuid);

        if (updateDishDto.getName() != null) {
            dishRepository.findByNameIgnoreCase(updateDishDto.getName())
                    .filter(d -> !d.getId().equals(uuid))
                    .ifPresent(d -> {
                        log.warn("{}: updateDish(uuid={}, updateDishDto={}) refused, dish with name={} already exists",
                                className, uuid, updateDishDto, updateDishDto.getName());
                        throw new ConflictException("dish with name=" + updateDishDto.getName() + " already exists", ErrorType.DISH_ALREADY_EXISTS);
                    });
        }

        dishMapper.updateDishFromUpdateDto(updateDishDto, entity);

        DishDto result = dishMapper.toDto(entity);
        log.info("{}: updateDish() result={}", className, result);
        return result;
    }

    @Override
    @Transactional
    public DishDto activateDish(UUID uuid) {
        Dish entity = getDishEntity(uuid);

        if(entity.isActive()) {
            log.warn("{}: activateDish() refused, dish already activated",
                    className);
            throw new ConflictException("dish with uuid=" + uuid + " already activated", ErrorType.DISH_ALREADY_ACTIVATED);
        }

        entity.setActive(true);

        DishDto result = dishMapper.toDto(entity);
        log.info("{}: activateDish() result={}", className, result);
        return result;
    }

    @Override
    @Transactional
    public void softDeleteDish(UUID uuid) {
        Dish entity = getDishEntity(uuid);

        if(!entity.isActive()) {
            log.warn("{}: softDeleteDish() refused, dish already deactivated",
                    className);
            throw new ConflictException("dish with uuid=" + uuid + " already deactivated", ErrorType.DISH_ALREADY_DEACTIVATED);
        }

        entity.setActive(false);
        log.info("{}: softDeleteDish() success", className);
    }
}
