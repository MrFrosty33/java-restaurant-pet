package at.frosty.restaurant.common.model.menu.mapper;

import at.frosty.restaurant.common.model.menu.Dish;
import at.frosty.restaurant.common.model.menu.dto.DishDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(target = "id", ignore = true)
    Dish toEntity(DishDto dto);

    DishDto toDto(Dish entity);
}
