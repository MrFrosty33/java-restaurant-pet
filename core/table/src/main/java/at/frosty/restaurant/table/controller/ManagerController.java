package at.frosty.restaurant.table.controller;

import at.frosty.restaurant.common.table.model.dto.TableDto;
import at.frosty.restaurant.common.table.model.dto.UpdateTableDto;
import at.frosty.restaurant.table.service.TableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/table")
@PreAuthorize("hasRole('MANAGER')")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ManagerController {
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TableDto createTable(@RequestBody @Valid @NotNull TableDto tableDto) {
        log.info("{}: received createTable(tableDto={}) call", className, tableDto);
        return service.createTable(tableDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TableDto updateTable(@PathVariable @NotNull UUID id,
                                @RequestBody @Valid @NotNull UpdateTableDto updateTableDto) {
        log.info("{}: received updateTable(id={}, updateTableDto={}) call", className, id, updateTableDto);
        return service.updateTable(id, updateTableDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable @NotNull UUID id) {
        log.info("{}: received deleteTable(id={}) call", className, id);
        service.deleteTable(id);
    }
}
