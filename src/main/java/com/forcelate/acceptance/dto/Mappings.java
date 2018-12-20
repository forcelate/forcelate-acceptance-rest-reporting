
package com.forcelate.acceptance.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mappings implements Serializable
{

    @SerializedName("dispatcherServlets")
    @Expose
    private DispatcherServlets dispatcherServlets;
    @SerializedName("servletFilters")
    @Expose
    private List<ServletFilter> servletFilters = new ArrayList<ServletFilter>();
    @SerializedName("servlets")
    @Expose
    private List<Servlet> servlets = new ArrayList<Servlet>();
    private final static long serialVersionUID = -7898425453660427302L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Mappings() {
    }

    /**
     * 
     * @param servlets
     * @param dispatcherServlets
     * @param servletFilters
     */
    public Mappings(DispatcherServlets dispatcherServlets, List<ServletFilter> servletFilters, List<Servlet> servlets) {
        super();
        this.dispatcherServlets = dispatcherServlets;
        this.servletFilters = servletFilters;
        this.servlets = servlets;
    }

    public DispatcherServlets getDispatcherServlets() {
        return dispatcherServlets;
    }

    public void setDispatcherServlets(DispatcherServlets dispatcherServlets) {
        this.dispatcherServlets = dispatcherServlets;
    }

    public List<ServletFilter> getServletFilters() {
        return servletFilters;
    }

    public void setServletFilters(List<ServletFilter> servletFilters) {
        this.servletFilters = servletFilters;
    }

    public List<Servlet> getServlets() {
        return servlets;
    }

    public void setServlets(List<Servlet> servlets) {
        this.servlets = servlets;
    }

}
