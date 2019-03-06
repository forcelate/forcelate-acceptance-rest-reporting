
package com.forcelate.acceptance.dto;

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
public class DispatcherServlets implements Serializable {

    private List<DispatcherServlet> dispatcherServlet = new ArrayList<>();
    private static final long serialVersionUID = 7562786695374503212L;

    public DispatcherServlets(List<DispatcherServlet> dispatcherServlet) {
        super();
        this.dispatcherServlet = dispatcherServlet;
    }
}
