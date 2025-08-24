package org.company.exchange.repository;

import org.company.exchange.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("""
            SELECT c.value
            FROM Currency c
            WHERE c.name LIKE :name
            """)
    Optional<BigDecimal> findValueByName(@Param("name") String name);

    Optional<Currency> findByName(String name);
}
