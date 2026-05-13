package at.frosty.restaurant.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails getUserByUsername(String username);
}
