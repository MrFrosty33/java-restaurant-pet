package at.frosty.restaurant.common.table.model.mapper;

import at.frosty.restaurant.common.table.model.Table;
import at.frosty.restaurant.common.table.model.dto.TableDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TableMapper {

    TableDto toDto(Table entity);

    @Mapping(target = "id", ignore = true)
    Table toEntity(TableDto dto);
}
