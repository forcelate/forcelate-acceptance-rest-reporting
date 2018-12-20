package com.forcelate.acceptance.dto.Details;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mediaType",
    "negated"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produce {

    @JsonProperty("mediaType")
    private String mediaType;
    @JsonProperty("negated")
    private Boolean negated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("mediaType")
    public String getMediaType() {
        return mediaType;
    }

    @JsonProperty("mediaType")
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @JsonProperty("negated")
    public Boolean getNegated() {
        return negated;
    }

    @JsonProperty("negated")
    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
