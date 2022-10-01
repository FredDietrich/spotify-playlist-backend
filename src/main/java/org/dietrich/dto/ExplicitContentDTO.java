package org.dietrich.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "filter_enabled",
        "filter_locked"
})
@Generated("jsonschema2pojo")
public class ExplicitContentDTO {

    @JsonProperty("filter_enabled")
    public Boolean filterEnabled;
    
    @JsonProperty("filter_locked")
    public Boolean filterLocked;

}
