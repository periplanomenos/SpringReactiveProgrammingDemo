package org.example.my_digital_bank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.example.my_digital_bank.model.core.AccountBalanceModification;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Class: AccountMovement
 */
@Getter
@Setter
@Entity
@Table(name = "accounts_movements")
public class AccountMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iban")
    private String iban;

    @Column(name = "available_balance")
    private BigDecimal availableBalance;

    @Column(name = "ledger_balance")
    private BigDecimal ledgerBalance;

    @Column(name = "description")
    private String description;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_balance_modification")
    private AccountBalanceModification accountBalanceModification;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
