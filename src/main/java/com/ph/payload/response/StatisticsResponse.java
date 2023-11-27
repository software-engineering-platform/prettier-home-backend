package com.ph.payload.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponse {

    private Long totalAdverts;
    private Long totalUsers;
    private Long totalAdvertTypes;
    private Long totalTourRequests;
    private Long totalCategories;

}
