package com.EcommerceApp.JGFE.services.admin.coupon;

import java.util.List;

import org.springframework.stereotype.Service;

import com.EcommerceApp.JGFE.entity.Coupon;
import com.EcommerceApp.JGFE.enums.exceptions.ValidationException;
import com.EcommerceApp.JGFE.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{

    private final CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }

    
    
}