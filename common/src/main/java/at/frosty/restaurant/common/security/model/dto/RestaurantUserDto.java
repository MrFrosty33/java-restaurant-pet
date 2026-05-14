package at.frosty.restaurant.common.security.model.dto;

import at.frosty.restaurant.common.security.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RestaurantUserDto {
    private String username;
    private String password;
    private Set<Role> roles;
}
