package org.company.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long accountId;
    @Builder.Default
    @Column(unique = true, nullable = false)
    private String currency = "RUB";
    @Builder.Default
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

