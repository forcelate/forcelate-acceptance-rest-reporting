package com.forcelate.acceptance.domain.reporting;

import com.forcelate.acceptance.domain.processing.CaseMapping;
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
