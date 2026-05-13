package at.frosty.restaurant.auth.service;

import at.frosty.restaurant.auth.repository.UserRepository;
import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.NotFoundException;
import at.frosty.restaurant.common.security.model.RestaurantUser;
import at.frosty.restaurant.common.security.model.dto.RestaurantUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final String className = this.getClass().getSimpleName();
    private final UserRepository userRepository;

    @Override
    public RestaurantUserDto getUserByUsername(String username) {
        RestaurantUser entity = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("{}: unable to find user with username={}", className, username);
            return new NotFoundException("user with username=" + username + " not found", ErrorType.USER_NOT_FOUND);
        });

        RestaurantUserDto result = RestaurantUserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .build();

        log.info("{}: found user: username={}, roles={}", className, result.getUsername(), result.getRoles());
        return result;
    }
}
