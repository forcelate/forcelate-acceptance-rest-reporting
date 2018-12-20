package com.forcelate.acceptance.dto.Details;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "handlerMethod",
    "requestMappingConditions"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsItems {

    @JsonProperty("handlerMethod")
    private HandlerMethod handlerMethod;
    @JsonProperty("requestMappingConditions")
    private RequestMappingConditions requestMappingConditions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("handlerMethod")
    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    @JsonProperty("handlerMethod")
    public void setHandlerMethod(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    @JsonProperty("requestMappingConditions")
    public RequestMappingConditions getRequestMappingConditions() {
        return requestMappingConditions;
    }

    @JsonProperty("requestMappingConditions")
    public void setRequestMappingConditions(RequestMappingConditions requestMappingConditions) {
        this.requestMappingConditions = requestMappingConditions;
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
