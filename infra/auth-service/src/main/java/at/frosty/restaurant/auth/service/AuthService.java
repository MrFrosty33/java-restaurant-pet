package at.frosty.restaurant.auth.service;

import at.frosty.restaurant.common.security.model.dto.RestaurantUserDto;

public interface AuthService {
    RestaurantUserDto getUserByUsername(String username);
}
