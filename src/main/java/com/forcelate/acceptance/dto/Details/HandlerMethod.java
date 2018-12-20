package com.forcelate.acceptance.dto.Details;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "className",
    "name",
    "descriptor"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HandlerMethod {

    @JsonProperty("className")
    private String className;
    @JsonProperty("name")
    private String name;
    @JsonProperty("descriptor")
    private String descriptor;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("className")
    public String getClassName() {
        return className;
    }

    @JsonProperty("className")
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("descriptor")
    public String getDescriptor() {
        return descriptor;
    }

    @JsonProperty("descriptor")
    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
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
