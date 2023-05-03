package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/coupons")
public class AdminCouponsController {

	private AdminCouponsServiceImpl adminCouponsServiceImpl;

	public AdminCouponsController(AdminCouponsServiceImpl adminCouponsServiceImpl) {
		this.adminCouponsServiceImpl = adminCouponsServiceImpl;
	}

	@GetMapping("/count")
	public int getCouponCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int couponCount;

		if (condition == null) {
			couponCount = adminCouponsServiceImpl.getCouponCount(filter);
		} else {
			couponCount = adminCouponsServiceImpl.getSearchCouponCount(filter, condition, keyword);
		}

		return couponCount;
	}

	@GetMapping("")
	public List<AdminCouponsDTO> getCouponList(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminCouponsDTO> couponList;
		int pageNum = (pages - 1) * 10;

		if (condition == null) {
			couponList = adminCouponsServiceImpl.getCouponList(filter, sort, pageNum);
		} else {
			couponList = adminCouponsServiceImpl.getSearchCouponList(filter, condition, keyword, sort, pageNum);
		}

		return couponList;
	}

	@DeleteMapping("/delete")
	public void deleteCoupon(@RequestParam(value = "couponCode", required = true) int couponCode) {
		adminCouponsServiceImpl.deleteCoupon(couponCode);
	}
}
