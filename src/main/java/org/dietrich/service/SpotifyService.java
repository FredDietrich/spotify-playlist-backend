package org.dietrich.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.dietrich.dto.TokenRequestDTO;
import org.dietrich.entity.Token;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.Form;

@RegisterRestClient(baseUri = "https://accounts.spotify.com")
public interface SpotifyService {

    @POST
    @Path("/api/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Token getAccessToken(@Form TokenRequestDTO tokenDto, @HeaderParam("Authorization") String authorization);

}
