package com.forcelate.acceptance.dto.details;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mediaType",
    "negated"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Produce {

    @JsonProperty("mediaType")
    private String mediaType;
    @JsonProperty("negated")
    private Boolean negated;
}
