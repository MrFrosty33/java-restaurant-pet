package at.frosty.restaurant.common.client.auth;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignAuthConfig {
    private final String className = this.getClass().getSimpleName();
    private final ServiceAuthCredentials serviceAuthCredentials;

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate -> {
            log.info("{}: found serviceAuthCredentials with username={}", className, serviceAuthCredentials.getUsername());
            String encoded = Base64.getEncoder()
                    .encodeToString(serviceAuthCredentials.getCredentials().getBytes(StandardCharsets.UTF_8));

            requestTemplate.header(
                    HttpHeaders.AUTHORIZATION,
                    "Basic " + encoded
            );
        };
    }
}