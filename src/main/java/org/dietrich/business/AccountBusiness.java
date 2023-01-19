package org.dietrich.business;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;

import org.dietrich.dto.RedirectUriDTO;
import org.dietrich.dto.TokenRequestDTO;
import org.dietrich.entity.Account;
import org.dietrich.entity.Token;
import org.dietrich.helper.Base64Helper;
import org.dietrich.repository.AccountRepository;
import org.dietrich.repository.TokenRepository;
import org.dietrich.service.SpotifyService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import lombok.extern.jbosslog.JBossLog;
import net.bytebuddy.utility.RandomString;

@ApplicationScoped
@JBossLog
public class AccountBusiness {

    @Inject
    AccountRepository accountRepository;

    @Inject
    TokenRepository tokenRepository;

    @ConfigProperty(name = "spotify-playlist-backend.clientid")
    String clientId;

    @ConfigProperty(name = "spotify-playlist-backend.clientsecret")
    String clientSecret;

    @ConfigProperty(name = "spotify-playlist-backend.redirect_uri")
    String redirectUri;

    @RestClient
    @Inject
    SpotifyService spotifyService;

    public List<Account> listAll() {
        return this.accountRepository.listAll();
    }

    public Account findById(Long id) {
        return this.accountRepository.findById(id);
    }

    public Account findByStateOptional(String state) {
        return this.accountRepository.findByStateOptional(state)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada para o state " + state));
    }

    @Transactional
    public Account create(Account account) {
        String state = new RandomString(16).nextString();
        account.state = state;
        this.save(account);
        return account;
    }

    public RedirectUriDTO getLoginUriForAccount(Long id) {
        Optional<Account> accountOpt = this.accountRepository.findByIdOptional(id);
        Account account = accountOpt.orElseThrow(() -> new NotFoundException("Conta não encontrada com id: " + id));
        URI uri = UriBuilder.fromPath("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", account.state)
                .build();
        return RedirectUriDTO.builder()
                .redirectUri(uri.toString())
                .build();
    }

    @Transactional
    public void save(Account account) {
        this.accountRepository.persist(account);
    }

    @Transactional
    public void saveCodeToAccountWithState(String code, String state) {
        Account account = this.accountRepository.findByStateOptional(state)
                .map(acc -> {
                    acc.code = code;
                    return acc;
                })
                .orElseThrow(() -> new NotFoundException("Conta não encontrada para o state " + state));
        this.save(account);
    }

    @Transactional
    public void saveTokenToAccount(Account account, Token token) {
        account.tokens.add(token);
        this.save(account);
    }

    @Transactional
    public Optional<Token> requestAccessTokenWithCode(String state) {
        Account account = this.findByStateOptional(state);
        TokenRequestDTO tokenDto = TokenRequestDTO.builder()
            .code(account.code)
            .grantType("authorization_code")
            .redirectUri(redirectUri)
            .build();
        String basicAuthString = Base64Helper.encodeBasicAuth(clientId, clientSecret);
        Token token = null;
        try {
            token = spotifyService.getAccessToken(tokenDto, basicAuthString);
        } catch (Exception e) {
            log.error(e);
        }
        if (token != null) {
            token.account = account;
            this.tokenRepository.persist(token);
            this.saveTokenToAccount(account, token);
        }
        return Optional.ofNullable(token);
    }

}
