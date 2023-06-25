package com.example.medcheckb8.db.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationExperts {
    private List<ExpertResponse> responses;
    private int currentPage;
    private int pageSize;
}
