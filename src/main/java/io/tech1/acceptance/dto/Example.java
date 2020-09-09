package io.tech1.acceptance.dto;

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
public class Example implements Serializable {

    private Contexts contexts;
    private static final long serialVersionUID = -2453170818080565875L;

    public Example(Contexts contexts) {
        super();
        this.contexts = contexts;
    }
}
