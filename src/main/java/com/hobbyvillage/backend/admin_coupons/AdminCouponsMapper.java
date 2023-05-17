package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminCouponsMapper {
	// 미검색 상태에서 쿠폰 개수 조회
	@Select("SELECT COUNT(*) FROM coupons WHERE ${filter};")
	int getCouponCount(@Param("filter") String filter);

	// 검색 상태에서 쿠폰 개수 조회
	@Select("SELECT COUNT(*) FROM coupons WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchCouponCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	// 미검색 상태에서 쿠폰 목록 조회
	@Select("SELECT couponCode, couponName, startDate, deadline "
			+ "FROM coupons WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminCouponsDTO> getCouponList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 쿠폰 목록 조회
	@Select("SELECT couponCode, couponName, startDate, deadline "
			+ "FROM coupons WHERE ${filter} AND ${condition} LIKE '%${keyword}%' "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminCouponsDTO> getSearchCouponList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);

	// 쿠폰 삭제
	@Delete("DELETE FROM coupons WHERE couponCode = #{couponCode}")
	void deleteCoupon(@Param("couponCode") int couponCode);
	
	// 쿠폰 신규 등록 (퍼센트 할인인 경우)
	@Insert("INSERT INTO coupons (couponName, discountPer, discountFix, deadline) "
			+ "VALUES (#{couponName}, #{discountPer}, #{discountFix}, #{deadline});")
	void addCouponPer(AdminCouponsDTO coupon);
	
	// 쿠폰 신규 등록 (금액 할인인 경우)
	@Insert("INSERT INTO coupons (couponName, discountPer, discountFix, deadline) "
			+ "VALUES (#{couponName}, #{discountPer}, #{discountFix}, #{deadline});")
	void addCouponFix(AdminCouponsDTO coupon);
	
	// 쿠폰 내용 상세보기 
	@Select("SELECT * FROM coupons WHERE couponCode = #{couponCode};")
	AdminCouponsDTO getCouponDetails(@Param("couponCode") int couponCode);
	
}

