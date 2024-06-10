package com.EcommerceApp.JGFE.services.admin.adminOrder;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.dto.AnalyticsResponse;
import com.EcommerceApp.JGFE.dto.OrderDto;
import com.EcommerceApp.JGFE.entity.Order;
import com.EcommerceApp.JGFE.enums.OrderStatus;
import com.EcommerceApp.JGFE.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacOrders() {
        List<Order> orderList = orderRepository
                .findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Delivered, OrderStatus.Shipped));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOderStatus(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (Objects.equals(status, "Shipped")) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

    public AnalyticsResponse calculateAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previewsMonthDate = currentDate.minusMonths(1);

        Long currentMothOrders = getTotalOrdersForMoth(currentDate.getMonthValue(), currentDate.getYear());
        Long previewsMothOrders = getTotalOrdersForMoth(previewsMonthDate.getMonthValue(), previewsMonthDate.getYear());

        Long currentMonthEarnings = getTotalMonthEarning(currentDate.getMonthValue(), currentDate.getYear());
        Long previewsMonthEarnings = getTotalMonthEarning(previewsMonthDate.getMonthValue(),
                previewsMonthDate.getYear());

        Long placed = orderRepository.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderRepository.countByOrderStatus(OrderStatus.Shipped);
        Long delivered = orderRepository.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponse(placed, shipped, delivered, currentMothOrders, 
        previewsMothOrders, currentMonthEarnings, previewsMonthEarnings);
    }

    private Long getTotalMonthEarning(int monthValue, int year) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calender to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth,
                OrderStatus.Delivered);

        Long sum = 0L;
        for (Order order : orders) {
            sum += order.getAmount();
        }

        return sum;
    }

    private Long getTotalOrdersForMoth(int monthValue, int year) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthValue - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calender to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepository.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth,
                OrderStatus.Delivered);

        return (long) orders.size();
    }
}
