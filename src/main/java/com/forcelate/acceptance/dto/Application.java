package com.forcelate.acceptance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements Serializable
{

    @SerializedName("mappings")
    @Expose
    private Mappings mappings;
    @SerializedName("parentId")
    @Expose
    private Object parentId;
    private final static long serialVersionUID = 7209117407767594525L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Application() {
    }

    /**
     * 
     * @param parentId
     * @param mappings
     */
    public Application(Mappings mappings, Object parentId) {
        super();
        this.mappings = mappings;
        this.parentId = parentId;
    }

    public Mappings getMappings() {
        return mappings;
    }

    public void setMappings(Mappings mappings) {
        this.mappings = mappings;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

}
