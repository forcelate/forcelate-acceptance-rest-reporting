package com.forcelate.acceptance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Application implements Serializable {
    private Mappings mappings;
    private transient Object parentId;
    private static final long serialVersionUID = 7209117407767594525L;

    public Application(Mappings mappings, Object parentId) {
        super();
        this.mappings = mappings;
        this.parentId = parentId;
    }
}
