package com.forcelate.acceptance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Example implements Serializable
{

    @SerializedName("contexts")
    @Expose
    private Contexts contexts;
    private final static long serialVersionUID = -2453170818080565875L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Example() {
    }

    /**
     * 
     * @param contexts
     */
    public Example(Contexts contexts) {
        super();
        this.contexts = contexts;
    }

    public Contexts getContexts() {
        return contexts;
    }

    public void setContexts(Contexts contexts) {
        this.contexts = contexts;
    }

}
