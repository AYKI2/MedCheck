package com.example.medcheckb8.db.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse<T> {
    private List<T> responses;
    private int currentPage;
    private int pageSize;
}

