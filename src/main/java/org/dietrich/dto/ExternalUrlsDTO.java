package org.dietrich.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "spotify"
})
@Generated("jsonschema2pojo")
public class ExternalUrlsDTO {

    @JsonProperty("spotify")
    public String spotify;

}
