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
public class ServletFilter implements Serializable
{

    private transient List<Object> servletNameMappings = new ArrayList<>();
    private List<String> urlPatternMappings = new ArrayList<>();
    private String name;
    private String className;
    private static final long serialVersionUID = -4702622173822169628L;

    public ServletFilter(List<Object> servletNameMappings, List<String> urlPatternMappings, String name, String className) {
        super();
        this.servletNameMappings = servletNameMappings;
        this.urlPatternMappings = urlPatternMappings;
        this.name = name;
        this.className = className;
    }
}
