package org.dietrich.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "url",
        "height",
        "width"
})
@Generated("jsonschema2pojo")
public class ImageDTO {

    @JsonProperty("url")
    public String url;

    @JsonProperty("height")
    public Integer height;
    
    @JsonProperty("width")
    public Integer width;

}
