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

    private Table getTableEntity(UUID id) {
        return tableRepository.findById(id).orElseThrow(() -> {
            log.warn("{}: unable to find table with id={}", className, id);
            return new NotFoundException("table with id=" + id + " not found", ErrorType.TABLE_NOT_FOUND);
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
    public TableDto updateTable(UUID id, UpdateTableDto updateTableDto) {
        Table entity = getTableEntity(id);
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
    public void deleteTable(UUID id) {
        Table entity = getTableEntity(id);
        tableRepository.delete(entity);
        log.info("{}: table with id={} was deleted", className, id);
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
    public TableDto getTableById(UUID id) {
        TableDto result = tableMapper.toDto(getTableEntity(id));
        log.info("{}: getTableById() result={}", className, result);
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
