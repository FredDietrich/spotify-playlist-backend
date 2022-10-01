package org.dietrich.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "href",
        "total"
})
@Generated("jsonschema2pojo")
public class FollowersDTO {

    @JsonProperty("href")
    public String href;
    
    @JsonProperty("total")
    public Integer total;

}
