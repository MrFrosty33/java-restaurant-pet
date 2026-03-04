package at.frosty.restaurant.common.menu.model.mapper;

import at.frosty.restaurant.common.menu.model.Dish;
import at.frosty.restaurant.common.menu.model.dto.DishDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(target = "id", ignore = true)
    Dish toEntity(DishDto dto);

    DishDto toDto(Dish entity);
}
