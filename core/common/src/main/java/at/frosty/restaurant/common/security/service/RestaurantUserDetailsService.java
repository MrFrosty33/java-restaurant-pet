package at.frosty.restaurant.common.security.service;

import at.frosty.restaurant.common.security.feign.AuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantUserDetailsService implements UserDetailsService {
    private final String className = this.getClass().getSimpleName();
    private final AuthClient authClient;

    /**
     * Authentication is DB-based.
     * User registration is not implemented intentionally.
     * Users must be created manually in the database with encoded passwords and appropriate roles.
     */

    @Override
    public UserDetails loadUserByUsername(String username) {
        return authClient.getUserByUsername(username);
    }
}
