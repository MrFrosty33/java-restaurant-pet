package at.frosty.restaurant.common.table.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateTableDto {
    @Positive
    @Max(1000)
    private Integer number;

    @Positive
    @Max(30)
    private Integer capacity;
}
