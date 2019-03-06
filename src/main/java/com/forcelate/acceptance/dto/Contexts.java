
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
public class Contexts implements Serializable {

    private Application application;
    private static final long serialVersionUID = -8034055346573971519L;

    public Contexts(Application application) {
        super();
        this.application = application;
    }
}
