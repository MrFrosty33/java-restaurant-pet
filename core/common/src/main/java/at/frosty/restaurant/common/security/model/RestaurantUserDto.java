package at.frosty.restaurant.common.security.model;

import lombok.Data;

import java.util.Set;

@Data
public class RestaurantUserDto {
    private String username;
    private String password;
    private Set<Role> roles;
}
