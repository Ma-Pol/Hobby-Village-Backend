package com.hobbyvillage.backend.user_purchase;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserPurchaseMapper {
	// 상품의 대여 상태 체크
	@Select("SELECT prodIsRental FROM products WHERE prodCode = #{prodCode};")
	int getProductState(@Param("prodCode") String prodCode);

	// state에 저장된 상품 정보와 실제 상품 정보 비교
	@Select("SELECT COUNT(*) FROM products WHERE prodCode = #{prodCode} AND prodPrice = #{prodPrice} "
			+ "AND prodShipping = #{prodShipping} AND prodHost = #{prodHost};")
	int checkProductInfo(@Param("prodCode") String prodCode, @Param("prodPrice") int prodPrice,
			@Param("prodShipping") int prodShipping, @Param("prodHost") String prodHost);

	/// 회원 정보 조회
	@Select("SELECT * FROM users WHERE email = #{email};")
	UserPurchaseUserDTO getUserInfo(@Param("email") String email);

	// 회원의 배송지 목록 조회
	@Select("SELECT * FROM userAddress WHERE email = #{email} ORDER BY -isDefault, addressCode;")
	List<UserPurchaseAddressDTO> getAddressList(@Param("email") String email);

	// 회원의 쿠폰 목록 조회
	@Select("SELECT * FROM userCoupon uc INNER JOIN coupons c ON uc.couponCode = c.couponCode "
			+ "WHERE uc.email = #{email} AND deadline > now();")
	List<UserPurchaseCouponDTO> getCouponList(@Param("email") String email);

	// 상품의 대여 상태 변경 (미대여 -> 대여)
	@Update("UPDATE products SET prodIsRental = 1 WHERE prodCode = #{prodCode};")
	int updateProductState(@Param("prodCode") String prodCode);

	// 추가 결제인 경우 이전 주문번호 조회
	@Select("SELECT odrNumber FROM orderProducts WHERE prodCode = #{prodCode} ORDER BY -opCode LIMIT 1;")
	String getPrevOdrNumber(@Param("prodCode") String prodCode);

	// (추가 주문 x) 실 결제 진행 전 주문 정보 임시 저장(최종 결제 후 결제 데이터 검증용)
	@Insert("INSERT INTO orders (odrNumber, odrPayment, exactPrice, usedSavedMoney, odrEmail, odrPhone, "
			+ "odrZipCode, odrAddress1, odrAddress2, receiver, deliRequest) VALUES (#{odrNumber}, "
			+ "#{odrPayment}, #{exactPrice}, #{usedSavedMoney}, #{odrEmail}, #{odrPhone}, #{odrZipCode}, "
			+ "#{odrAddress1}, #{odrAddress2}, #{receiver}, #{deliRequest});")
	int purchasePreProcess1(UserPurchaseProcessDTO data);

	// (추가 주문 o) 실 결제 진행 전 주문 정보 임시 저장(최종 결제 후 결제 데이터 검증용)
	@Insert("INSERT INTO exOrders (odrNumber, prevOdrNumber, prodCode, odrPayment, exactPrice, usedSavedMoney, rentalPeriod) "
			+ "VALUES (#{odrNumber}, #{prevOdrNumber}, #{prodCode}, #{odrPayment}, #{exactPrice}, #{usedSavedMoney}, #{rentalPeriod});")
	int purchasePreProcess2(UserPurchaseProcessDTO data);

	// paid_amount와 DB 내 exactPrice를 비교
	@Select("SELECT COUNT(*) FROM ${searchTable} WHERE odrNumber = #{odrNumber} AND exactPrice = #{paid_amount};")
	int compareToDatabase(@Param("odrNumber") String odrNumber, @Param("paid_amount") int paid_amount,
			@Param("searchTable") String searchTable);

	// import 실 결제 금액과 DB 내 exactPrice를 비교하기 위해 exactPrice 조회
	@Select("SELECT exactPrice FROM ${searchTable} WHERE odrNumber = #{odrNumber} AND exactPrice = #{paid_amount};")
	int compareToImport(@Param("odrNumber") String odrNumber, @Param("paid_amount") int paid_amount,
			@Param("searchTable") String searchTable);

	// 결제 성공 시 상품 대여 횟수 증가
	@Update("UPDATE products SET rentalCount = rentalCount + #{rentalCount} WHERE prodCode = #{prodCode};")
	int increaseRentalCount(@Param("prodCode") String prodCode, @Param("rentalCount") int rentalCount);

	// 결제 성공 시 유저 적립금 차감
	@Update("UPDATE users SET savedMoney = savedMoney - #{exactSavedMoney} WHERE email = #{email};")
	int decreaseSavedMoney(@Param("email") String email, @Param("exactSavedMoney") int exactSavedMoney);

	// 결제 성공 시 유저 쿠폰 데이터 삭제
	@Delete("DELETE FROM userCoupon WHERE email = #{email} AND couponCode = #{couponCode};")
	int deleteCoupon(@Param("email") String email, @Param("couponCode") int couponCode);

	// (추가 주문 x) 결제 성공 시 주문 상품 데이터 추가
	@Insert("INSERT INTO orderProducts (odrNumber, prodCode, rentalPeriod) "
			+ "VALUES (#{odrNumber}, #{prodCode}, #{rentalPeriod});")
	int insertOrderProduct(UserPurchaseProcessDTO data);

	// (추가 주문 o) 결제 성공 시 주문 상품 데이터 변경(rentalPeriod, deadline)
	@Update("UPDATE orderProducts SET rentalPeriod = rentalPeriod + #{rentalPeriod}, "
			+ "deadline = DATE_ADD(deadline, INTERVAL #{rentalPeriod} DAY), odrState = '배송 완료' "
			+ "WHERE odrNumber = #{prevOdrNumber} AND prodCode = #{prodCode};")
	int updateOrderProduct(UserPurchaseProcessDTO data);

	// (추가 주문 ox 공통) 결제 성공 시 imp_uid 추가
	@Update("UPDATE ${searchTable} SET imp_uid = #{imp_uid} WHERE odrNumber = #{odrNumber};")
	int updateImpUid(@Param("odrNumber") String odrNumber, @Param("imp_uid") String imp_uid,
			@Param("searchTable") String searchTable);

	// 결제 실패 시 상품의 대여 상태 변경 (대여 -> 미대여)
	@Update("UPDATE products SET prodIsRental = 0 WHERE prodCode = #{prodCode};")
	int updateProductStateFailed(@Param("prodCode") String prodCode);

	// 결제 실패 시 임시 저장된 주문 정보 삭제
	@Delete("DELETE FROM ${searchTable} WHERE odrNumber = #{odrNumber};")
	int deleteOrder(@Param("odrNumber") String odrNumber, @Param("searchTable") String searchTable);

}
