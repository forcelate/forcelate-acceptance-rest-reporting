package com.forcelate.acceptance.dto.details;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "handlerMethod",
    "requestMappingConditions"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DetailsItems implements Serializable {

    @JsonProperty("handlerMethod")
    private transient HandlerMethod handlerMethod;
    @JsonProperty("requestMappingConditions")
    private transient RequestMappingConditions requestMappingConditions;
    @JsonIgnore
    private transient Map<String, Object> additionalProperties = new HashMap<>();
}
