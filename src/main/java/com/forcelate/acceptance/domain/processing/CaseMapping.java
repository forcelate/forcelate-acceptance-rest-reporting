package com.forcelate.acceptance.domain.processing;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class CaseMapping {
    private String endpoint;
    private String httpType;
}
