package at.frosty.restaurant.auth.service;

import at.frosty.restaurant.common.security.model.dto.RestaurantUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthUserDetailsService implements UserDetailsService {
    private final String className = this.getClass().getSimpleName();
    private final AuthServiceImpl service;

    /**
     * Authentication is DB-based.
     * User registration is not implemented intentionally.
     * Users must be created manually in the database with encoded passwords and appropriate roles.
     */

    @Override
    public UserDetails loadUserByUsername(String username) {
        RestaurantUserDto userDto = service.getUserByUsername(username);
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .authorities(userDto.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toList())
                .build();
    }
}
