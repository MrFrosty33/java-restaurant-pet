package at.frosty.restaurant.menu.service;

import at.frosty.restaurant.common.model.menu.RestaurantUser;
import at.frosty.restaurant.menu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantUserDetailsService implements UserDetailsService {
    private final String className = this.getClass().getSimpleName();
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        RestaurantUser restaurantUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("{}: unable to find user with username={}", className, username);
                    return new UsernameNotFoundException("User with username=" + username + " does not exist");
                });


        UserDetails result = User.builder()
                .username(restaurantUser.getUsername())
                .password(restaurantUser.getPassword())
                .authorities(restaurantUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toList())
                .build();

        log.info("{}: result of loadUserByUsername(username={}): {}", className, username, result);
        return result;
    }
}
