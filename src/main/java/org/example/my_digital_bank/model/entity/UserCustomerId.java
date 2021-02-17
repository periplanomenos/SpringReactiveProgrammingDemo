package org.example.my_digital_bank.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class: UserCustomerId
 */
@Slf4j
@Getter
@Setter
@Embeddable
public class UserCustomerId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "customer_id")
    private Long customerId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
