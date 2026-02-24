package at.frosty.restaurant.common.model.menu.dto;

import at.frosty.restaurant.common.model.menu.Allergen;
import at.frosty.restaurant.common.model.menu.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class DishDto {
    private UUID id;

    @NotNull
    @NotBlank
    @Length(max = 150)
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private Category category;

    @NotNull
    @NotEmpty
    @Size(max = 14)
    private Set<Allergen> allergens;

}
