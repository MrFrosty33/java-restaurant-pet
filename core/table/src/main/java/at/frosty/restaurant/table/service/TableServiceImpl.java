package at.frosty.restaurant.table.service;

import at.frosty.restaurant.common.exception.ConflictException;
import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.NotFoundException;
import at.frosty.restaurant.common.table.model.Table;
import at.frosty.restaurant.common.table.model.dto.TableDto;
import at.frosty.restaurant.common.table.model.dto.UpdateTableDto;
import at.frosty.restaurant.common.table.model.mapper.TableMapper;
import at.frosty.restaurant.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final String className = this.getClass().getSimpleName();
    private final TableRepository tableRepository;
    private final TableMapper tableMapper;


    private void validateTableNumberNotOccupied(int number, String methodName) {
        if (tableRepository.existsByNumber(number)) {
            log.warn("{}: {} refused, table with number={} already exists",
                    className, methodName, number);
            throw new ConflictException("table with number=" + number + " already exists",
                    ErrorType.TABLE_ALREADY_EXISTS);
        }
    }

    private Table getTableEntity(UUID uuid) {
        return tableRepository.findById(uuid).orElseThrow(() -> {
            log.warn("{}: unable to find table with uuid={}", className, uuid);
            return new NotFoundException("table with uuid=" + uuid + " not found", ErrorType.TABLE_NOT_FOUND);
        });
    }

    @Override
    @Transactional
    public TableDto createTable(TableDto tableDto) {
        validateTableNumberNotOccupied(tableDto.getNumber(), "createTable()");

        Table entity = tableMapper.toEntity(tableDto);
        entity = tableRepository.save(entity);
        TableDto result = tableMapper.toDto(entity);

        log.info("{}: createTable() result={}", className, result);
        return result;
    }

    @Override
    @Transactional
    public TableDto updateTable(UUID uuid, UpdateTableDto updateTableDto) {
        Table entity = getTableEntity(uuid);
        if(updateTableDto.getNumber() != entity.getNumber()) {
            validateTableNumberNotOccupied(updateTableDto.getNumber(), "updateTable()");
        }

        tableMapper.updateTableFromUpdateDto(updateTableDto, entity);

        TableDto result = tableMapper.toDto(entity);
        log.info("{}: updateTable() result={}", className, result);
        return result;
    }

    @Override
    @Transactional
    public void deleteTable(UUID uuid) {
        Table entity = getTableEntity(uuid);
        tableRepository.delete(entity);
        log.info("{}: table with uuid={} was deleted", className, uuid);
    }

    @Override
    public List<TableDto> getAllTablesWithCapacity(int capacity) {
        List<TableDto> result = tableRepository.findByCapacityGreaterThanEqual(capacity).stream()
                .map(tableMapper::toDto)
                .toList();

        if (result.isEmpty()) {
            log.warn("{}: getAllTablesWithCapacity() unable to find any table", className);
            throw new NotFoundException("there are no tables in DB", ErrorType.TABLE_NOT_FOUND);
        }

        log.info("{}: getAllTablesWithCapacity() resultSize={}", className, result.size());
        log.debug("{}: getAllTablesWithCapacity() result={}", className, result);
        return result;
    }

    @Override
    public TableDto getTableByUuid(UUID uuid) {
        TableDto result = tableMapper.toDto(getTableEntity(uuid));
        log.info("{}: getTableByUuid() result={}", className, result);
        return result;
    }

    @Override
    public List<TableDto> getAllTables() {
        List<TableDto> result = tableRepository.findAll().stream()
                .map(tableMapper::toDto)
                .toList();

        if (result.isEmpty()) {
            log.warn("{}: getAllTables() unable to find any table", className);
            throw new NotFoundException("there are no tables in DB", ErrorType.TABLE_NOT_FOUND);
        }

        log.info("{}: getAllTables() resultSize={}", className, result.size());
        log.debug("{}: getAllTables() result={}", className, result);
        return result;
    }
}
