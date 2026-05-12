package at.frosty.restaurant.common.table.feign.fallback;

import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.ServiceUnavailableException;
import at.frosty.restaurant.common.table.feign.TableClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TableFallback implements FallbackFactory<TableClient> {
    private final String className = this.getClass().getSimpleName();

    @Override
    public TableClient create(Throwable cause) {
        return uuid -> {
            log.error("{}: getTableByUuid(uuid={}) failure", className, uuid, cause);
            throw new ServiceUnavailableException("table-service unavailable", ErrorType.SERVICE_UNAVAILABLE);
        };
    }
}
