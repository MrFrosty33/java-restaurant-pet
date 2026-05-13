package at.frosty.restaurant.common.security.feign.fallback;

import at.frosty.restaurant.common.menu.exception.ErrorType;
import at.frosty.restaurant.common.menu.exception.ServiceUnavailableException;
import at.frosty.restaurant.common.security.feign.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthFallback implements FallbackFactory<AuthClient> {
    private final String className = this.getClass().getSimpleName();

    @Override
    public AuthClient create(Throwable cause) {
        return username -> {
            log.error("{}: getUserByUsername(username={}) failure", className, username, cause);
            throw new ServiceUnavailableException("auth-service unavailable", ErrorType.SERVICE_UNAVAILABLE);
        };
    }
}
