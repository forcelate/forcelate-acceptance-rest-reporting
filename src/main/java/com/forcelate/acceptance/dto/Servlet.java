package com.forcelate.acceptance.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Servlet implements Serializable
{

    @SerializedName("mappings")
    @Expose
    private List<String> mappings = new ArrayList<String>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("className")
    @Expose
    private String className;
    private final static long serialVersionUID = 5610427687265354828L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Servlet() {
    }

    /**
     * 
     * @param name
     * @param mappings
     * @param className
     */
    public Servlet(List<String> mappings, String name, String className) {
        super();
        this.mappings = mappings;
        this.name = name;
        this.className = className;
    }

    public List<String> getMappings() {
        return mappings;
    }

    public void setMappings(List<String> mappings) {
        this.mappings = mappings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
