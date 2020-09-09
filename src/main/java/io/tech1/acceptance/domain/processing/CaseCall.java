package io.tech1.acceptance.domain.processing;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class CaseCall {
    private String description;
    private CaseStatus status;
    private String message;
}
