package at.frosty.restaurant.common.table.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class TableDto {
    private UUID id;

    @Positive
    @NotNull
    @Max(1000)
    private Integer number;

    @Positive
    @NotNull
    @Max(30)
    private Integer capacity;
}
