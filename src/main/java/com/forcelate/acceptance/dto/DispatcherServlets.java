
package com.forcelate.acceptance.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherServlets implements Serializable
{

    @SerializedName("dispatcherServlet")
    @Expose
    private List<DispatcherServlet> dispatcherServlet = new ArrayList<DispatcherServlet>();
    private final static long serialVersionUID = 7562786695374503212L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DispatcherServlets() {
    }

    /**
     * 
     * @param dispatcherServlet
     */
    public DispatcherServlets(List<DispatcherServlet> dispatcherServlet) {
        super();
        this.dispatcherServlet = dispatcherServlet;
    }

    public List<DispatcherServlet> getDispatcherServlet() {
        return dispatcherServlet;
    }

    public void setDispatcherServlet(List<DispatcherServlet> dispatcherServlet) {
        this.dispatcherServlet = dispatcherServlet;
    }

}
