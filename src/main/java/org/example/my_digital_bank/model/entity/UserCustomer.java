package org.example.my_digital_bank.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.example.my_digital_bank.model.core.Relationship;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Class: UserCustomer
 */
@Getter
@Setter
@Entity
@Table(name = "users_customers")
public class UserCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserCustomerId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerId")
    private Customer customer;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship")
    private Relationship relationship;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
