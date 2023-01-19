package org.dietrich.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.dietrich.business.AccountBusiness;
import org.dietrich.dto.RedirectUriDTO;
import org.dietrich.entity.Account;
import org.dietrich.entity.Token;

@Path("/account")
@ApplicationScoped
public class AccountResource {

    @Inject
    AccountBusiness business;

    @GET
    public List<Account> list() {
        return this.business.listAll();
    }

    @GET
    @Path("/{id}")
    public Account get(Long id) {
        return this.business.findById(id);
    }

    @POST
    public Response create(Account account) {
        account = this.business.create(account);
        return Response.created(URI.create("/account/" + account.id)).build();
    }

    @GET
    @Path("/{id}/loginUri")
    public Response login(Long id) {
        RedirectUriDTO uri = this.business.getLoginUriForAccount(id);
        return Response.ok(uri).build();
    }

    @GET
    @Path("/authorize")
    @Produces(MediaType.TEXT_PLAIN)
    public Response authorize(@QueryParam("code") String code, @QueryParam("state") String state,
            @QueryParam("error") String error) {
        if (error != null) {
            return Response.serverError()
                    .entity("Ocorreu um erro na autenticação com o Spotify")
                    .build();
        }
        try {
            this.business.saveCodeToAccountWithState(code, state);
            Optional<Token> optToken = this.business.requestAccessTokenWithCode(state);
            if (optToken.isPresent()) {
                return Response.ok("Autenticação realizada com sucesso, esta janela pode ser fechada")
                        .build();
            }
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity("Ocorreu algum problema com a autenticação, tente novamente mais tarde")
                .build();
    }

    // @Path("/{id}/playlists")
    // public

}
