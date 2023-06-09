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

		// 검색 여부 확인
		if (keyword == null) {
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

		// 검색 여부 확인
		if (keyword == null) {
			couponList = adminCouponsServiceImpl.getCouponList(filter, sort, pageNum);
		} else {
			couponList = adminCouponsServiceImpl.getSearchCouponList(filter, condition, keyword, sort, pageNum);
		}

		return couponList;
	}

	@GetMapping("/check/{couponCode}")
	public int checkCouponCode(@PathVariable(value = "couponCode", required = true) String couponCode) {
		int result = 0;

		// couponCode가 숫자인지 확인
		if (couponCode.matches("-?\\d+")) {
			int couponCodeInt = Integer.parseInt(couponCode);
			result = adminCouponsServiceImpl.checkCouponCode(couponCodeInt);
		}

		return result;
	}

	@GetMapping("/getCouponDetails")
	public AdminCouponsDTO getCouponDetails(@RequestParam int couponCode) {
		return adminCouponsServiceImpl.getCouponDetails(couponCode);
	}

	@DeleteMapping("/delete")
	public void deleteCoupon(@RequestParam(value = "couponCode", required = true) int couponCode) {
		adminCouponsServiceImpl.deleteCoupon(couponCode);
	}

	@PostMapping("/addCoupon")
	public void addCoupon(@RequestBody AdminCouponsDTO coupon) {
		adminCouponsServiceImpl.addCoupon(coupon);
	}

}
