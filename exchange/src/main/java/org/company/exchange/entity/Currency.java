package org.company.exchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long currencyId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "value", nullable = false)
    private BigDecimal value;
}
