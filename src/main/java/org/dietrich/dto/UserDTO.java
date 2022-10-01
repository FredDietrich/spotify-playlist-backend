package org.dietrich.dto;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "display_name",
        "email",
        "explicit_content",
        "external_urls",
        "followers",
        "href",
        "id",
        "images",
        "product",
        "type",
        "uri"
})

@Generated("jsonschema2pojo")
public class UserDTO {

    @JsonProperty("country")
    public String country;

    @JsonProperty("display_name")
    public String displayName;

    @JsonProperty("email")
    public String email;

    @JsonProperty("explicit_content")
    public ExplicitContentDTO explicitContent;

    @JsonProperty("external_urls")
    public ExternalUrlsDTO externalUrls;

    @JsonProperty("followers")
    public FollowersDTO followers;

    @JsonProperty("href")
    public String href;

    @JsonProperty("id")
    public String id;

    @JsonProperty("images")
    public List<ImageDTO> images = null;

    @JsonProperty("product")
    public String product;

    @JsonProperty("type")
    public String type;
    
    @JsonProperty("uri")
    public String uri;

}
