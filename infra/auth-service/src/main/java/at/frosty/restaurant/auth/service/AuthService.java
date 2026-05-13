package at.frosty.restaurant.auth.service;

import at.frosty.restaurant.common.security.model.RestaurantUserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    RestaurantUserDto getUserByUsername(String username);
}
