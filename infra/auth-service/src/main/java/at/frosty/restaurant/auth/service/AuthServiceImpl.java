package at.frosty.restaurant.auth.service;

import at.frosty.restaurant.auth.repository.UserRepository;
import at.frosty.restaurant.common.menu.exception.ErrorType;
import at.frosty.restaurant.common.menu.exception.NotFoundException;
import at.frosty.restaurant.common.security.model.RestaurantUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final String className = this.getClass().getSimpleName();
    private final UserRepository userRepository;

    @Override
    public UserDetails getUserByUsername(String username) {
        RestaurantUser entity = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("{}: unable to find user with username={}", className, username);
            return new NotFoundException("user with username=" + username + " not found", ErrorType.USER_NOT_FOUND);
        });

        UserDetails result = User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .authorities(entity.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toList())
                .build();

        log.info("{}: result of getUserByUsername(username={}): {}", className, username, result);
        return result;
    }
}
