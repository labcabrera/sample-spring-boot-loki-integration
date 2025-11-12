package org.labcabrera.sample.archetype.interfaces.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class Pagination {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
