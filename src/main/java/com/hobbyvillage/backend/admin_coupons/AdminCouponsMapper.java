package com.hobbyvillage.backend.admin_coupons;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminCouponsMapper {
	@Select("SELECT COUNT(*) FROM coupons WHERE ${filter};")
	int getCouponCount(@Param("filter") String filter);

	@Select("SELECT COUNT(*) FROM coupons WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchCouponCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	@Select("SELECT couponCode, couponName, startDate, deadline "
			+ "FROM coupons WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminCouponsDTO> getCouponList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	@Select("SELECT couponCode, couponName, startDate, deadline "
			+ "FROM coupons WHERE ${filter} AND ${condition} LIKE '%${keyword}%' "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminCouponsDTO> getSearchCouponList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);

	@Delete("DELETE FROM coupons WHERE couponCode = #{couponCode}")
	void deleteCoupon(@Param("couponCode") int couponCode);
}
