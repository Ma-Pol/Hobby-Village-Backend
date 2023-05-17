package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

public interface AdminCouponsService {
	int getCouponCount(String filter);
	int getSearchCouponCount(String filter, String condition, String keyword);
	List<AdminCouponsDTO> getCouponList(String filter, String sort, int pageNum);
	List<AdminCouponsDTO> getSearchCouponList(String filter, String condition, String keyword, String sort, int pageNum);
	void deleteCoupon(int couponCode);
}
