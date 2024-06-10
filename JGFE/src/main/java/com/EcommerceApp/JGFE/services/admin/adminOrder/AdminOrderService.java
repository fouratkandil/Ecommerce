package com.EcommerceApp.JGFE.services.admin.adminOrder;

import java.util.List;

import com.EcommerceApp.JGFE.dto.AnalyticsResponse;
import com.EcommerceApp.JGFE.dto.OrderDto;

public interface AdminOrderService {
    List<OrderDto> getAllPlacOrders();
    OrderDto changeOderStatus(Long orderId,  String status);
    AnalyticsResponse calculateAnalytics();
}
