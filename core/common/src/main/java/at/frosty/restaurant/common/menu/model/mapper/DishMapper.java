package at.frosty.restaurant.common.menu.model.mapper;

import at.frosty.restaurant.common.menu.model.Dish;
import at.frosty.restaurant.common.menu.model.dto.DishDto;
import at.frosty.restaurant.common.menu.model.dto.UpdateDishDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(target = "id", ignore = true)
    Dish toEntity(DishDto dto);

    DishDto toDto(Dish entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDishFromUpdateDto(UpdateDishDto dto, @MappingTarget Dish entity);
}
