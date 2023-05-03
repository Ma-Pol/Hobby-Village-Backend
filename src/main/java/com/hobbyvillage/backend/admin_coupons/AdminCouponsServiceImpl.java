package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminCouponsServiceImpl implements AdminCouponsService {

	private AdminCouponsMapper mapper;

	public AdminCouponsServiceImpl(AdminCouponsMapper mapper) {
		this.mapper = mapper;
	}

	private String filtering(String filter) {
		if (filter.equals("none")) {
			filter = "couponCode IS NOT NULL";
		} else if (filter.equals("available")) {
			filter = "deadline IS NULL OR DATEDIFF(deadline, NOW()) >= 0";
		} else {
			filter = "DATEDIFF(deadline, NOW()) < 0";
		}

		return filter;
	}

	@Override
	public int getCouponCount(String filter) {
		filter = filtering(filter);

		return mapper.getCouponCount(filter);
	}

	@Override
	public int getSearchCouponCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchCouponCount(filter, condition, keyword);
	}

	@Override
	public List<AdminCouponsDTO> getCouponList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getCouponList(filter, sort, pageNum);
	}

	@Override
	public List<AdminCouponsDTO> getSearchCouponList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchCouponList(filter, condition, keyword, sort, pageNum);
	}

	@Override
	public void deleteCoupon(int couponCode) {
		mapper.deleteCoupon(couponCode);
	}

}
