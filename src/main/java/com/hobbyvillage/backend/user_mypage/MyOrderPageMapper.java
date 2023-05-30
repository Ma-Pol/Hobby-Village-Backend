package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MyOrderPageMapper {
	// 주문 상태 별 주문 상품 개수 조회
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "WHERE o.odrEmail = #{email} AND op.odrState = #{odrState};")
	int getOdrStateCount(@Param("email") String email, @Param("odrState") String odrState);

	// 주문 상태 별 주문 상품 조회
	@Select("SELECT op.opcode, op.odrNumber, op.odrState, o.odrDate, p.prodCode, p.prodHost, p.prodName, p.prodPrice, p.prodShipping, "
			+ "op.rentalPeriod, op.deadline, o.exactPrice, o.receiver, o.odrZipCode, o.odrAddress1, o.odrAddress2, "
			+ "op.courierCompany, op.trackingNumber "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN products p "
			+ "ON op.prodCode = p.prodCode WHERE o.odrEmail = #{email} AND op.odrState = #{odrState} ORDER BY -o.odrDate;")
	List<MyOrderListDTO> getOrderList(@Param("email") String email, @Param("odrState") String odrState);

	// 리뷰 작성 여부 조회
	@Select("SELECT COUNT(*) FROM reviews WHERE prodCode = #{prodCode} AND revwWriter = #{nickname}")
	boolean checkReviewed(@Param("prodCode") String prodCode, @Param("nickname") String nickname);

	// 주문 별 상품 이미지 명 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode = #{prodCode} ORDER BY prodPicCode LIMIT 1;")
	String getProdPicture(@Param("prodCode") String prodCode);

	// 주문 물품 상태 변경 처리
	@Update("UPDATE orderProducts SET odrState = #{odrState} WHERE opCode = #{opCode};")
	int changeProduct(@Param("opCode") int opCode, @Param("odrState") String odrState);
}
