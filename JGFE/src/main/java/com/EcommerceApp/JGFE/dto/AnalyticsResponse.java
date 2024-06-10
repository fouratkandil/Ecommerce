package com.EcommerceApp.JGFE.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class AnalyticsResponse {
    private Long placed;
    private Long shipped;
    private Long delivered;

    private Long currentMonthOrders;
    private Long previewsMonthOrders;

    private Long currentMonthEarnings;
    private Long previewsMonthEarnings;

}
