package at.frosty.restaurant.common.table.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class TableDto {
    private UUID id;

    @Positive
    @Max(1000)
    private int number;

    @Positive
    @Max(30)
    private int capacity;
}
