package io.tech1.acceptance.domain.reporting;

import io.tech1.acceptance.domain.processing.CaseMapping;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class MappingsPair {
    private boolean available;
    private List<CaseMapping> mappings;
}
