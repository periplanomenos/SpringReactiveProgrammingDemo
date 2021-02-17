package org.example.my_digital_bank.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Class: CustomerAccountId
 */
@Slf4j
@Getter
@Setter
@Embeddable
public class CustomerAccountId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_id")
    private Long accountId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
