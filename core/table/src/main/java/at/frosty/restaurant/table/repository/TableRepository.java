package at.frosty.restaurant.table.repository;

import at.frosty.restaurant.common.table.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
    List<Table> findByCapacityGreaterThanEqual(int capacity);
    boolean existsByNumber(int number);
}
