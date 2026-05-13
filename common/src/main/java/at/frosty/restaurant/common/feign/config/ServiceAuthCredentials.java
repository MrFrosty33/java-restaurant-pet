package at.frosty.restaurant.common.feign.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "service-auth-credentials")
public class ServiceAuthCredentials {
    private String username;
    private String password;

    public String getCredentials() {
        return username + ":" + password;
    }
}
