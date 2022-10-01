package org.dietrich.dto;

import javax.ws.rs.FormParam;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequestDTO {
 
    @FormParam("grant_type")
    private String grantType;

    @FormParam("code")
    private String code;

    @FormParam("redirect_uri")
    private String redirectUri;

}
