package org.example.my_digital_bank.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Class: CustomerAccount
 */
@Getter
@Setter
@Entity
@Table(name = "customers_accounts")
public class CustomerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CustomerAccountId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("accountId")
    private Account account;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "primary_beneficiary")
    private boolean primaryBeneficiary;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
