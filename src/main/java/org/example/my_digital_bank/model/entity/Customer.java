package org.example.my_digital_bank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Class: Customer
 */
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company_name")
    private String companyName;

    @NotBlank(message = "Please provide a valid VATIN (Value Added Tax Identification Number)!")
    private String vatin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_type_id")
    private CustomerType customerType;

    /*
    @JsonIgnore
    @ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
    */

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<UserCustomer> userCustomerSet = new HashSet<>();

    private boolean enabled;

    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<CustomerAccount> customerAccountSet = new HashSet<>();

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
