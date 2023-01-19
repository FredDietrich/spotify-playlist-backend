package org.dietrich.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.dietrich.entity.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
 
    public Optional<Account> findByStateOptional(String state) {
        return find("state", state).firstResultOptional();
    }
    
}
