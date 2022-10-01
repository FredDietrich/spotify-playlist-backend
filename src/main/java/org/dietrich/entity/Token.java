package org.dietrich.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.ToString;

@Entity
@ToString
public class Token extends PanacheEntity {
    
    @JsonProperty("access_token")
    public String accessToken;
    
    @JsonProperty("token_type")
    public String tokenType;

    public String scope;

    @JsonProperty("expires_in")
    public Long expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    public Account account;

}
