package io.tech1.acceptance.domain.processing;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Execution {
    private CaseMapping mapping;
    private List<CaseCall> calls;
}
