package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminOrdersMapper {
	// 페이지네이션 용 주문 상품 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts op;")
	int getAllOrderCount();

	// 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts op WHERE deadline != '1000-01-01';")
	int getDeliveriedOrderCount();

	// 검색 시 페이지네이션 용 주문 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE ${condition} LIKE '%${keyword}%';")
	int getSearchAllOrderCount(@Param("condition") String condition, @Param("keyword") String keyword);

	// 검색 시 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email WHERE deadline != '1000-01-01' "
			+ "AND ${condition} LIKE '%${keyword}%';")
	int getSearchDeliveriedOrderCount(@Param("condition") String condition, @Param("keyword") String keyword);

	// 주문 목록 조회: 최근 주문/오래된 주문 순
	@Select("SELECT op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber "
			+ "INNER JOIN users u ON o.odrEmail = u.email ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getAllOrderList(@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 주문 목록 조회: 반납 기한 순
	@Select("SELECT op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE deadline != '1000-01-01' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getDeliveriedOrderList(@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 검색 시 주문 목록 조회: 최근 주문/오래된 주문 순
	@Select("SELECT op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getSearchAllOrderList(@Param("condition") String condition, @Param("keyword") String keyword,
			@Param("sort") String sort, @Param("pageNum") int pageNum);

	// 검색 시 주문 목록 조회: 반납 기한 순
	@Select("SELECT op.odrNumber, op.prodCode, op.deliDate, op.rentalPeriod, op.deadline, op.odrState, u.userCode, u.nickname "
			+ "FROM orderProducts op INNER JOIN orders o ON op.odrNumber = o.odrNumber INNER JOIN users u ON o.odrEmail = u.email "
			+ "WHERE deadline != '1000-01-01' AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminOrdersDTO> getSearchDeliveriedOrderList(@Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);

	@Select("SELECT o.*, u.userCode FROM orders o INNER JOIN users u ON o.odrEmail = u.email WHERE odrNumber = #{odrNumber}")
	AdminOrdersDetailDTO getOrderDetail(@Param("odrNumber") String odrNumber);

	@Select("SELECT o.*, p.prodCategory, p.prodName, p.prodPrice, p.prodShipping "
			+ "FROM orderProducts o INNER JOIN products p ON o.prodCode = p.prodCode "
			+ "WHERE odrNumber = #{odrNumber}")
	List<AdminOrdersProductsDTO> getOrderedProductList(@Param("odrNumber") String odrNumber);

	@Update("UPDATE orders SET odrZipCode = #{odrZipCode}, odrAddress1 = #{odrAddress1}, "
			+ "odrAddress2 = #{odrAddress2} WHERE odrNumber = #{odrNumber};")
	int modifyOrderAddress(AdminOrdersDetailDTO addressData);

	@Select("SELECT COUNT(*) FROM orderProducts WHERE opCode = #{opCode} AND odrState = '반납 중';")
	int returningCheck(@Param("opCode") int opCode);

	@Update("UPDATE orderProducts SET odrState = '반납 완료' WHERE opCode = #{opCode};")
	void modifyOdrState(@Param("opCode") int opCode);

	@Update("UPDATE products SET prodIsRental = 0 WHERE prodCode = #{prodCode};")
	int modifyRentalState(@Param("prodCode") String prodCode);
}
