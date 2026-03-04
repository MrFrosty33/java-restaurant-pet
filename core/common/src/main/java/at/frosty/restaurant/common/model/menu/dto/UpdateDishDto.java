package at.frosty.restaurant.common.model.menu.dto;

import at.frosty.restaurant.common.model.menu.Allergen;
import at.frosty.restaurant.common.model.menu.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Set;

public class UpdateDishDto {
    @Length(max = 150)
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    private Category category;

    private Boolean active;

    @Size(max = 14)
    private Set<Allergen> allergens;
}
