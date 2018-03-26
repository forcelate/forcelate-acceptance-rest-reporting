package com.jedivision.acceptance.rest.utils.domain.reporting;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Git {
    private String branch;
    private String commitId;
    private String commitTime;
}
