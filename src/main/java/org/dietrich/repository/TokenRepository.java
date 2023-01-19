package org.dietrich.repository;

import javax.enterprise.context.ApplicationScoped;

import org.dietrich.entity.Token;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TokenRepository implements PanacheRepository<Token> {

}
