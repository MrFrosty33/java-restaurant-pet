package at.frosty.restaurant.menu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventsListener {
    private final String className = this.getClass().getSimpleName();

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        log.info("{}: authentication success for user={}", className, event.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        log.warn("{}: authentication failure for user={}, exception={}",
                className, event.getAuthentication().getName(), event.getException().getMessage());
    }
}