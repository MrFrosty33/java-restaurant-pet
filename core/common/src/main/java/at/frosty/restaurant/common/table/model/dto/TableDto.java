package at.frosty.restaurant.common.table.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public class TableDto {
    private UUID id;

    @PositiveOrZero
    @Max(1000)
    private int number;

    @PositiveOrZero
    @Max(30)
    private int capacity;
}
