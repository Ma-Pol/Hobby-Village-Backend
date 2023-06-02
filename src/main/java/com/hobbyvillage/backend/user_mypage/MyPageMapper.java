package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

	// -----------------------------------------------------
	
	// 판매/위탁 신청 개수 조회 (전체)
	@Select("SELECT COUNT(*) FROM requests WHERE reqEmail=#{reqEmail} ORDER BY reqDate DESC;")
	int getRequestCountAll(@Param("reqEmail") String reqEmail);
	
	// 판매/위탁 신청 목록 조회 (전체)
	@Select("SELECT reqCode, reqSort, reqEmail, reqBank, reqAccountNum, reqCategory, reqTitle, "
			+ "reqContent, reqDate, reqProgress FROM requests WHERE reqEmail=#{reqEmail} ORDER BY reqDate DESC LIMIT #{pageNum}, 5;")
	List<MyPageRequestDTO> getRequestListAll(@Param("reqEmail") String reqEmail, @Param("pageNum") int pageNum);
	
	// 판매/위탁 신청 개수 조회
	@Select("SELECT COUNT(*) FROM requests WHERE reqEmail=#{reqEmail} AND reqProgress=#{reqProgress} ORDER BY reqDate DESC;")
	int getRequestCount(@Param("reqEmail") String reqEmail, @Param("reqProgress") String reqProgress);
	
	// 판매/위탁 신청 목록 조회 
	@Select("SELECT reqCode, reqSort, reqEmail, reqBank, reqAccountNum, reqCategory, reqTitle, "
			+ "reqContent, reqDate, reqProgress FROM requests WHERE reqEmail=#{reqEmail} AND reqProgress=#{reqProgress} ORDER BY reqDate DESC LIMIT #{pageNum}, 5;")
	List<MyPageRequestDTO> getRequestList(@Param("reqEmail") String reqEmail, @Param("reqProgress") String reqProgress, @Param("pageNum") int pageNum);
	
	// 판매/위탁 신청 물품 이미지 파일명 조회
	@Select("SELECT reqFile FROM requestFiles WHERE reqCode=#{reqCode} ORDER BY reqFile;")
	List<String> getRequestPictures(@Param("reqCode") String reqCode);
	
	// -----------------------------------------------------
	
	// 위탁 철회
	@Update("UPDATE requests SET reqProgress='철회 요청' WHERE reqCode=#{reqCode};")
	void withdrawRequest(@Param("reqCode") String reqCode);
	
	// 계좌정보 수정
	@Update("UPDATE requests SET reqBank=#{reqBank}, reqAccountNum=#{reqAccountNum} WHERE reqCode=#{reqCode};")
	void updateAccount(@Param("reqBank") String reqBank, @Param("reqAccountNum") String reqAccountNum, @Param("reqCode") String reqCode);
}
