package at.frosty.restaurant.common.table.feign;

import at.frosty.restaurant.common.menu.model.dto.DishOrderDto;
import at.frosty.restaurant.common.table.model.dto.TableDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface TableClient {
    @GetMapping("/internal/table/{uuid}")
    TableDto getTableByUuid(@PathVariable @NotNull UUID uuid);
}
