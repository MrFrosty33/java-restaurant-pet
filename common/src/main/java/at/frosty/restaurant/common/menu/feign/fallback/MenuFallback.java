package at.frosty.restaurant.common.menu.feign.fallback;

import at.frosty.restaurant.common.menu.exception.ErrorType;
import at.frosty.restaurant.common.menu.exception.ServiceUnavailableException;
import at.frosty.restaurant.common.menu.feign.MenuClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MenuFallback implements FallbackFactory<MenuClient> {
    private final String className = this.getClass().getSimpleName();

    @Override
    public MenuClient create(Throwable cause) {
        return uuid -> {
            log.error("{}: getDishOrderByUuid(uuid={}) failure", className, uuid, cause);
            throw new ServiceUnavailableException("menu-service unavailable", ErrorType.SERVICE_UNAVAILABLE);
        };
    }
}
