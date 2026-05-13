package at.frosty.restaurant.common.menu.model.dto;

import at.frosty.restaurant.common.menu.model.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class DishOrderDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Category category;
}
