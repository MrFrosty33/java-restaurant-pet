package at.frosty.restaurant.common.table.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateTableDto {
    @PositiveOrZero
    @Max(1000)
    private int number;

    @PositiveOrZero
    @Max(30)
    private int capacity;
}
