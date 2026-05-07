package at.frosty.restaurant.common.menu.model.dto;

import at.frosty.restaurant.common.menu.model.Allergen;
import at.frosty.restaurant.common.menu.model.Category;
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

    private boolean active;

    @NotNull
    @Size(max = 14)
    private Set<Allergen> allergens;
}
