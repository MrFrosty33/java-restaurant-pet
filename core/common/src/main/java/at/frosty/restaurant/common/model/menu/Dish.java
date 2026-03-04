package at.frosty.restaurant.common.model.menu;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column
    private boolean active;

    @ElementCollection(targetClass = Allergen.class)
    @CollectionTable(name = "dish_allergens", joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "allergen", length = 1)
    @Enumerated(EnumType.STRING)
    private Set<Allergen> allergens = new HashSet<>();
}
