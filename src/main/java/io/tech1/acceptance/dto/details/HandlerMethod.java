package io.tech1.acceptance.dto.details;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "className",
    "name",
    "descriptor"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class HandlerMethod {
    @JsonProperty("className")
    private String className;
    @JsonProperty("name")
    private String name;
    @JsonProperty("descriptor")
    private String descriptor;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
}
