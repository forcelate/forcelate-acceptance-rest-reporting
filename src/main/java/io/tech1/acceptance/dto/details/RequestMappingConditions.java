package io.tech1.acceptance.dto.details;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "consumes",
    "headers",
    "methods",
    "params",
    "patterns",
    "produces"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Setter
public class RequestMappingConditions {

    @JsonProperty("consumes")
    private List<Object> consumes = null;
    @JsonProperty("headers")
    private List<Object> headers = null;
    @JsonProperty("methods")
    private List<String> methods = null;
    @JsonProperty("params")
    private List<Object> params = null;
    @JsonProperty("patterns")
    private List<String> patterns = null;
    @JsonProperty("produces")
    private List<Produce> produces = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
