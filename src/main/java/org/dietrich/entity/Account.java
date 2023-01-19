package org.dietrich.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Account extends PanacheEntity {

    public String username;
    public String state;
    public String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    @JsonManagedReference
    public Set<Token> tokens = new HashSet<>();

    public static Optional<Account> findByStateOptional(String state) {
        return find("state", state).firstResultOptional();
    }

}
