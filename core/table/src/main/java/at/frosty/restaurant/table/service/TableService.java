package at.frosty.restaurant.table.service;

import at.frosty.restaurant.common.table.model.dto.TableDto;
import at.frosty.restaurant.common.table.model.dto.UpdateTableDto;

import java.util.List;
import java.util.UUID;

public interface TableService {
    // manager methods
    TableDto createTable(TableDto tableDto);
    TableDto updateTable(UUID uuid, UpdateTableDto updateTableDto);
    void deleteTable(UUID uuid);

    // internal methods
    List<TableDto> getAllTablesWithCapacity(int capacity);

    // manager and internal methods
    TableDto getTableByUuid(UUID uuid);
    List<TableDto> getAllTables();
}
