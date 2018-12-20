
package com.forcelate.acceptance.dto;;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.forcelate.acceptance.dto.Details.DetailsItems;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherServlet implements Serializable
{

    @SerializedName("handler")
    @Expose
    private String handler;
    @SerializedName("predicate")
    @Expose
    private String predicate;
    @SerializedName("details")
    @Expose
    private DetailsItems details;
    private final static long serialVersionUID = -6787044356205479215L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DispatcherServlet() {
    }

    /**
     * 
     * @param details
     * @param predicate
     * @param handler
     */
    public DispatcherServlet(String handler, String predicate, DetailsItems details) {
        super();
        this.handler = handler;
        this.predicate = predicate;
        this.details = details;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public DetailsItems getDetails() {
        return details;
    }

    public void setDetails(DetailsItems details) {
        this.details = details;
    }

}
