package at.frosty.restaurant.common.client.table;

import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TableFallback implements FallbackFactory<TableClient> {
    private final String className = this.getClass().getSimpleName();

    @Override
    public TableClient create(Throwable cause) {
        return id -> {
            log.error("{}: getTableById(id={}) failure", className, id, cause);
            throw new ServiceUnavailableException("table-service unavailable", ErrorType.SERVICE_UNAVAILABLE);
        };
    }
}
