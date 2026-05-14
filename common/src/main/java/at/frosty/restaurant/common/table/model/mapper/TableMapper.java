package at.frosty.restaurant.common.table.model.mapper;

import at.frosty.restaurant.common.table.model.Table;
import at.frosty.restaurant.common.table.model.dto.TableDto;
import at.frosty.restaurant.common.table.model.dto.UpdateTableDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDto toDto(Table entity);

    @Mapping(target = "id", ignore = true)
    Table toEntity(TableDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTableFromUpdateDto(UpdateTableDto dto, @MappingTarget Table entity);
}
