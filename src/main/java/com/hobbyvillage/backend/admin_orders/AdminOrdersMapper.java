package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminOrdersMapper {
	// 페이지네이션 용 주문 상품 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts WHERE ${filter};")
	int getAllOrderCount(@Param("filter") String filter);

	// 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts WHERE deadline != '1000-01-01' AND ${filter};")
	int getDeliveriedOrderCount(@Param("filter") String filter);

	// 검색 시 페이지네이션 용 주문 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchAllOrderCount(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("filter") String filter);

	// 검색 시 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE deadline != '1000-01-01' "
			+ "AND ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchDeliveriedOrderCount(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("filter") String filter);

	// 주문 목록 조회: 최근 주문/오래된 주문 순
	@Select("SELECT op.opCode, op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getAllOrderList(@Param("sort") String sort, @Param("filter") String filter,
			@Param("pageNum") int pageNum);

	// 주문 목록 조회: 반납 기한 순
	@Select("SELECT op.opCode, op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE deadline != '1000-01-01' AND ${filter} ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getDeliveriedOrderList(@Param("sort") String sort, @Param("filter") String filter,
			@Param("pageNum") int pageNum);

	// 검색 시 주문 목록 조회: 최근 주문/오래된 주문 순
	@Select("SELECT op.opCode, op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getSearchAllOrderList(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("filter") String filter, @Param("pageNum") int pageNum);

	// 검색 시 주문 목록 조회: 반납 기한 순
	@Select("SELECT op.opCode, op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE deadline != '1000-01-01' AND ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getSearchDeliveriedOrderList(@Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("filter") String filter,
			@Param("pageNum") int pageNum);

	// 주문 상세 조회 1: 기본 정보 조회
	@Select("SELECT o.odrPayment, o.cancelPrice, o.cancelSavedMoney, o.odrEmail, o.odrPhone, o.receiver, "
			+ "o.odrZipCode, o.odrAddress1, o.odrAddress2, o.odrDate, u.userCode FROM orders o "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE odrNumber = #{odrNumber};")
	AdminOrdersDetailDTO getOrderDetail(@Param("odrNumber") String odrNumber);

	// 주문 상세 조회 2: 총 결제액 조회(orders AND exOrders)
	@Select("SELECT SUM(odrs.exactPrice) AS exactPrice FROM ("
			+ "SELECT exactPrice FROM orders WHERE odrNumber = #{odrNumber} UNION ALL "
			+ "SELECT exactPrice FROM exOrders WHERE prevOdrNumber = #{odrNumber}) AS odrs;")
	int getOrderExactPrice(@Param("odrNumber") String odrNumber);

	// 주문 상세 조회 3: 총 사용 적립금 조회(orders AND exOrders)
	@Select("SELECT SUM(odrs.usedSavedMoney) AS usedSavedMoney FROM ("
			+ "SELECT usedSavedMoney FROM orders WHERE odrNumber = #{odrNumber} UNION ALL "
			+ "SELECT usedSavedMoney FROM exOrders WHERE prevOdrNumber = #{odrNumber}) AS odrs;")
	int getOrderUsedSavedMoney(@Param("odrNumber") String odrNumber);

	// 각 주문 별 상품 목록 조회
	@Select("SELECT o.*, p.prodCategory, p.prodName, p.prodPrice, p.prodShipping "
			+ "FROM orderProducts o INNER JOIN products p ON o.prodCode = p.prodCode "
			+ "WHERE odrNumber = #{odrNumber}")
	List<AdminOrdersProductsDTO> getOrderedProductList(@Param("odrNumber") String odrNumber);

	// 주문자 주소 수정
	@Update("UPDATE orders SET odrZipCode = #{odrZipCode}, odrAddress1 = #{odrAddress1}, "
			+ "odrAddress2 = #{odrAddress2} WHERE odrNumber = #{odrNumber};")
	int modifyOrderAddress(AdminOrdersDetailDTO addressData);

	// 주문 확인 처리 (결제 완료 -> 배송 준비 중)
	@Update("UPDATE orderProducts SET odrState = '배송 준비 중' WHERE odrNumber = #{odrNumber};")
	int payCompToPreDeli(@Param("odrNumber") String odrNumber);

	// 운송 정보 등록 처리 (배송 준비 중 -> 배송 중)
	@Update("UPDATE orderProducts SET odrState = '배송 중', courierCompany = #{courierCompany}, "
			+ "trackingNumber = #{trackingNumber} WHERE opCode = #{opCode};")
	int preDeliToShipping(@Param("opCode") int opCode, @Param("courierCompany") String courierCompany,
			@Param("trackingNumber") String trackingNumber);

	// odrState 확인
	@Select("SELECT odrState FROM orderProducts WHERE opCode = #{opCode};")
	String checkOdrState(@Param("opCode") int opCode);

	// odrState 수정
	@Update("UPDATE orderProducts SET odrState = #{nextStep} WHERE opCode = #{opCode};")
	void modifyOdrState(@Param("opCode") int opCode, @Param("nextStep") String nextStep);

	// 상품의 prodIsRental을 0으로 수정(미대여 표시)
	@Update("UPDATE products SET prodIsRental = 0 WHERE prodCode = #{prodCode};")
	void modifyRentalState(@Param("prodCode") String prodCode);

	// 주문 취소 처리 과정: 취소 금액과 사용 취소된 적립금을 조회
	@Select("SELECT cancelPrice, cancelSavedMoney FROM orders WHERE odrNumber = #{odrNumber};")
	AdminOrdersCancelPriceDTO getCanceledData(@Param("odrNumber") String odrNumber);

	// 주문 취소 처리 과정: 같은 주문 내에서 취소 처리 완료 상태가 아닌 상품 개수 확인
	@Select("SELECT COUNT(*) FROM orderProducts WHERE odrNumber = #{odrNumber} AND odrState != '취소 처리 완료';")
	int checkOdrCount(@Param("odrNumber") String odrNumber);
	
	
	// 주문 취소 처리 과정: 유저 적립금 환급
	@Update("UPDATE users SET savedMoney = savedMoney + #{returnSavedMoney} WHERE email = #{odrEmail};")
	int returnSavedMoney(@Param("odrEmail") String odrEmail, @Param("returnSavedMoney") int returnSavedMoney);
	
	// 주문 취소 처리 과정: 취소 처리 완료 처리 (취소 요청 -> 취소 처리 완료)
	@Update("UPDATE orderProducts SET odrState = '취소 처리 완료' WHERE opCode = #{opCode};")
	void cancelComplate(@Param("opCode") int opCode);
	

	// 반납 완료 처리 과정: (상품 가격 * (총 대여 기간 / 7)) * 5 / 100 만큼 지급 (실 결제 금액과는 별개)
	@Update("UPDATE users SET savedMoney = savedMoney + #{savedMoney} WHERE email = #{email};")
	int giveSavedMoney(@Param("email") String email, @Param("savedMoney") int savedMoney);
}
