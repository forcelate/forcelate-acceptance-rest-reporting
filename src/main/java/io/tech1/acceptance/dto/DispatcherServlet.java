
package io.tech1.acceptance.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tech1.acceptance.dto.details.DetailsItems;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DispatcherServlet implements Serializable {

    private String handler;
    private String predicate;
    private DetailsItems details;
    private static final long serialVersionUID = -6787044356205479215L;

    public DispatcherServlet(String handler, String predicate, DetailsItems details) {
        super();
        this.handler = handler;
        this.predicate = predicate;
        this.details = details;
    }
}
