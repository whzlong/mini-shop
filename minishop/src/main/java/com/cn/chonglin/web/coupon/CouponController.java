package com.cn.chonglin.web.coupon;

import com.cn.chonglin.bussiness.coupon.service.CouponService;
import com.cn.chonglin.bussiness.coupon.vo.AssignCouponVo;
import com.cn.chonglin.bussiness.coupon.vo.CouponVo;
import com.cn.chonglin.common.PaginationResult;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.coupon.form.CouponForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 优惠券
 */
@RestController
public class CouponController {

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "admin/coupon-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PaginationResult<CouponVo> query(@RequestParam(required = false, defaultValue = "") String code,
                                            @RequestParam(required = false, defaultValue = "") String couponName,
                                            @RequestParam(required = false, defaultValue = "0") @Min(0) int currentPage,
                                            @RequestParam(required = false, defaultValue = "15") @Min(0) @Max(200) int size){

        currentPage -= 1;

        return PaginationResult.success(couponService.query(code, couponName, size, currentPage));
    }

    @RequestMapping(value = "admin/coupons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<CouponVo> save(@Valid CouponForm couponForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ResponseResult.error(bindingResult.getFieldErrors());
        }

        return ResponseResult.success(couponService.save(couponForm.toDomain()));
    }

    @PostMapping(value = "admin/assign-coupons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> assignCoupons(@RequestParam(value = "coupons[]") String[] coupons, @RequestParam(value = "assignedUsers[]") String[] assignedUsers){

        couponService.assignCoupons(assignedUsers, coupons);

        return ResponseResult.success(null);
    }

    @GetMapping(value = "admin/assign-coupons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<AssignCouponVo>> queryAssignedCoupons(@RequestParam String userId){
        return ResponseResult.success(couponService.queryAssignedCoupons(userId));
    }
}
