
package com.forcelate.acceptance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contexts implements Serializable
{

    @SerializedName("application")
    @Expose
    private Application application;
    private final static long serialVersionUID = -8034055346573971519L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Contexts() {
    }

    /**
     * 
     * @param application
     */
    public Contexts(Application application) {
        super();
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
