package com.EcommerceApp.JGFE.services.admin.coupon;

import java.util.List;

import com.EcommerceApp.JGFE.entity.Coupon;

public interface AdminCouponService {
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
    
}