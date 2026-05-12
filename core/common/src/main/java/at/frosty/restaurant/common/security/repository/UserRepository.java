package at.frosty.restaurant.common.security.repository;

import at.frosty.restaurant.common.security.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<RestaurantUser, UUID> {
    Optional<RestaurantUser> findByUsername(String username);
}
