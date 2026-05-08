package at.frosty.restaurant.common.menu.model.dto;

import at.frosty.restaurant.common.menu.model.Allergen;
import at.frosty.restaurant.common.menu.model.Category;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class UpdateDishDto {
    @Length(max = 150)
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    private Category category;

    private Set<Allergen> allergens;
}
