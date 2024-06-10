package com.EcommerceApp.JGFE.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.EcommerceApp.JGFE.dto.OrderDto;
import com.EcommerceApp.JGFE.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payement;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private user user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());

        if (coupon != null) {
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }

}
