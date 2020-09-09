
package io.tech1.acceptance.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Mappings implements Serializable {

    private DispatcherServlets dispatcherServlets;
    private List<ServletFilter> servletFilters = new ArrayList<>();
    private List<Servlet> servlets = new ArrayList<>();
    private static final long serialVersionUID = -7898425453660427302L;

    public Mappings(DispatcherServlets dispatcherServlets, List<ServletFilter> servletFilters, List<Servlet> servlets) {
        super();
        this.dispatcherServlets = dispatcherServlets;
        this.servletFilters = servletFilters;
        this.servlets = servlets;
    }
}
