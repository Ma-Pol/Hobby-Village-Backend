package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminCouponsServiceImpl implements AdminCouponsService {

	private AdminCouponsMapper mapper;

	public AdminCouponsServiceImpl(AdminCouponsMapper mapper) {
		this.mapper = mapper;
	}

	// 필터 조건에 따른 쿼리문 설정 메서드
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

	@Override // 미검색 상태에서 쿠폰 개수 조회
	public int getCouponCount(String filter) {
		filter = filtering(filter);

		return mapper.getCouponCount(filter);
	}

	@Override // 검색 상태에서 쿠폰 개수 조회
	public int getSearchCouponCount(String filter, String condition, String keyword) {
		filter = filtering(filter);

		return mapper.getSearchCouponCount(filter, condition, keyword);
	}

	@Override // 미검색 상태에서 쿠폰 목록 조회
	public List<AdminCouponsDTO> getCouponList(String filter, String sort, int pageNum) {
		filter = filtering(filter);

		return mapper.getCouponList(filter, sort, pageNum);
	}

	@Override // 검색 상태에서 쿠폰 목록 조회
	public List<AdminCouponsDTO> getSearchCouponList(String filter, String condition, String keyword, String sort,
			int pageNum) {
		filter = filtering(filter);

		return mapper.getSearchCouponList(filter, condition, keyword, sort, pageNum);
	}

	@Override
	public int checkCouponCode(int couponCode) {
		return mapper.checkCouponCode(couponCode);
	}

	@Override // 쿠폰 상세 보기
	public AdminCouponsDTO getCouponDetails(int couponCode) {
		return mapper.getCouponDetails(couponCode);
	}

	@Override // 쿠폰 삭제
	public void deleteCoupon(int couponCode) {
		mapper.deleteCoupon(couponCode);
	}

	@Override // 쿠폰 등록
	public void addCoupon(AdminCouponsDTO coupon) {
		mapper.addCoupon(coupon);
	}

}
