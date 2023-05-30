package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MyPageMapper {

	// 유저 적립금 조회
	@Select("SELECT savedMoney FROM users WHERE email = #{email};")
	int getSavedMoney(@Param("email") String email);

	// 유저 쿠폰 개수 조회
	@Select("SELECT COUNT(*) FROM userCoupon uc INNER JOIN coupons c ON uc.couponCode = c.couponCode "
			+ "WHERE uc.email = #{email};")
	int getCouponCount(@Param("email") String email);

	// 유저 쿠폰 리스트 조회
	@Select("SELECT c.couponCode, c.couponName, c.discountPer, c.discountFix, c.startDate, c.deadline "
			+ "FROM userCoupon uc INNER JOIN coupons c ON uc.couponCode = c.couponCode WHERE uc.email = #{email};")
	List<MyPageCouponsDTO> getCouponList(@Param("email") String email);

	// 유저 쿠폰 삭제
	@Delete("DELETE FROM userCoupon WHERE email = #{email} AND couponCode = #{couponCode}")
	void deleteCoupon(@Param("email") String email, @Param("couponCode") int couponCode);

	// 유저 프로필 사진 변경
	@Update("UPDATE users SET profPicture = #{profPicture} WHERE email = #{email};")
	void modifyProfPicture(@Param("email") String email, @Param("profPicture") String profPicture);

}
