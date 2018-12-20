package com.forcelate.acceptance.dto;;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServletFilter implements Serializable
{

    @SerializedName("servletNameMappings")
    @Expose
    private List<Object> servletNameMappings = new ArrayList<Object>();
    @SerializedName("urlPatternMappings")
    @Expose
    private List<String> urlPatternMappings = new ArrayList<String>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("className")
    @Expose
    private String className;
    private final static long serialVersionUID = -4702622173822169628L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ServletFilter() {
    }

    /**
     * 
     * @param urlPatternMappings
     * @param name
     * @param className
     * @param servletNameMappings
     */
    public ServletFilter(List<Object> servletNameMappings, List<String> urlPatternMappings, String name, String className) {
        super();
        this.servletNameMappings = servletNameMappings;
        this.urlPatternMappings = urlPatternMappings;
        this.name = name;
        this.className = className;
    }

    public List<Object> getServletNameMappings() {
        return servletNameMappings;
    }

    public void setServletNameMappings(List<Object> servletNameMappings) {
        this.servletNameMappings = servletNameMappings;
    }

    public List<String> getUrlPatternMappings() {
        return urlPatternMappings;
    }

    public void setUrlPatternMappings(List<String> urlPatternMappings) {
        this.urlPatternMappings = urlPatternMappings;
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
