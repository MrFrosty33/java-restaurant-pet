package at.frosty.restaurant.table.controller;

import at.frosty.restaurant.common.client.table.TableClient;
import at.frosty.restaurant.common.table.model.dto.TableDto;
import at.frosty.restaurant.table.service.TableService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/internal/table")
@RequiredArgsConstructor
@Validated
@Slf4j
public class InternalController implements TableClient {
    private final String className = this.getClass().getSimpleName();
    private final TableService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TableDto> getAllTables() {
        log.info("{}: received getAllTables() call", className);
        return service.getAllTables();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TableDto getTableById(@PathVariable @NotNull UUID id) {
        log.info("{}: received getTableByUuid(id={}) call", className, id);
        return service.getTableById(id);
    }

    @GetMapping(params = "capacity")
    @ResponseStatus(HttpStatus.OK)
    public List<TableDto> getAllTablesWithCapacity(@RequestParam @Positive @Max(30) int capacity) {
        log.info("{}: received getAllTablesWithCapacity(capacity={}) call", className, capacity);
        return service.getAllTablesWithCapacity(capacity);
    }
}
