package com.example.medcheckb8.db.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {
    private List<ApplicationResponse> application;
    private int currentPage;
    private int pageSize;
}

