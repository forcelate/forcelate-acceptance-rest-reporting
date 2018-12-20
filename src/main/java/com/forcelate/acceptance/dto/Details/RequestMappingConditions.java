package com.forcelate.acceptance.dto.Details;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
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
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("consumes")
    public List<Object> getConsumes() {
        return consumes;
    }

    @JsonProperty("consumes")
    public void setConsumes(List<Object> consumes) {
        this.consumes = consumes;
    }

    @JsonProperty("headers")
    public List<Object> getHeaders() {
        return headers;
    }

    @JsonProperty("headers")
    public void setHeaders(List<Object> headers) {
        this.headers = headers;
    }

    @JsonProperty("methods")
    public List<String> getMethods() {
        return methods;
    }

    @JsonProperty("methods")
    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    @JsonProperty("params")
    public List<Object> getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(List<Object> params) {
        this.params = params;
    }

    @JsonProperty("patterns")
    public List<String> getPatterns() {
        return patterns;
    }

    @JsonProperty("patterns")
    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    @JsonProperty("produces")
    public List<Produce> getProduces() {
        return produces;
    }

    @JsonProperty("produces")
    public void setProduces(List<Produce> produces) {
        this.produces = produces;
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
