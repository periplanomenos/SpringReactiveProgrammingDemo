package org.example.my_digital_bank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Class: Account
 */
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

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

    private boolean enabled;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<CustomerAccount> customerAccountSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<AccountMovement> accountMovements;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
