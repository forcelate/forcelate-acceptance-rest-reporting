package io.tech1.acceptance.domain.reporting;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Environment {
    private String baseURI;
    private int port;
    private String basePath;
}
