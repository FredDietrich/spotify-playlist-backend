package org.dietrich.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.dietrich.service.SpotifyService;
import org.dietrich.dto.RedirectUriDTO;
import org.dietrich.dto.TokenRequestDTO;
import org.dietrich.entity.Account;
import org.dietrich.entity.Token;
import org.dietrich.helper.Base64Helper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import net.bytebuddy.utility.RandomString;

@Path("/account")
public class AccountResource {

    @ConfigProperty(name = "spotify-playlist-backend.clientid")
    String clientId;

    @ConfigProperty(name = "spotify-playlist-backend.clientsecret")
    String clientSecret;

    @ConfigProperty(name = "spotify-playlist-backend.redirect_uri")
    String redirectUri;

    @RestClient
    @Inject
    SpotifyService spotifyService;

    @GET
    public List<Account> list() {
        return Account.listAll();
    }

    @GET
    @Path("/{id}")
    public Account get(Long id) {
        return Account.findById(id);
    }

    @POST
    @Transactional
    public Response create(Account account) {
        String state = new RandomString(16).nextString();
        account.state = state;
        account.persist();
        return Response.created(URI.create("/account/" + account.id)).build();
    }

    @GET
    @Path("/loginUri/{id}")
    public Response login(Long id) {
        Optional<Account> accountOpt = Account.findByIdOptional(id);
        Account account = accountOpt.orElseThrow(() -> new NotFoundException());
        URI uri = UriBuilder.fromPath("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", account.state)
                .build();
        return Response.ok(
                RedirectUriDTO.builder()
                    .redirectUri(uri.toString())
                    .build()
                ).build();
    }

    @GET
    @Path("/authorize")
    @Transactional
    public String authorize(@QueryParam("code") String code, @QueryParam("state") String state, @QueryParam("error") String error) {
        if(error != null) {
            throw new InternalError("Ocorreu um erro ao autorizar com o Spotify!");
        }
        Account account = Account.findByStateOptional(state)
            .map(acc -> {
                acc.code = code;
                acc.persist();
                return acc;
            })
            .orElseThrow(() -> new NotFoundException("Conta não encontrada! Não foi possível autorizar com o Spotify!"));
        TokenRequestDTO tokenDto = TokenRequestDTO.builder()
                .code(code)
                .grantType("authorization_code")
                .redirectUri(redirectUri)
                .build();
        String clientAuth = "Basic " + Base64Helper.encode(clientId + ":" + clientSecret);
        Token token = spotifyService.getAccessToken(tokenDto, clientAuth);
        if(token != null) {
            token.account = account;
            token.persist();
            account.tokens.add(token);
            account.persist();
            return "Autenticação feita com sucesso! Esta janela pode ser fechada";
        }
        return "Autenticação não ocorreu como deveria!";
    }

    // @Path("/{id}/playlists")
    // public 

}
