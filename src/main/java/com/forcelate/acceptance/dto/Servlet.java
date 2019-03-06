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
public class Servlet implements Serializable {

    private List<String> mappings = new ArrayList<>();
    private String name;
    private String className;
    private static final long serialVersionUID = 5610427687265354828L;

    public Servlet(List<String> mappings, String name, String className) {
        super();
        this.mappings = mappings;
        this.name = name;
        this.className = className;
    }
}
