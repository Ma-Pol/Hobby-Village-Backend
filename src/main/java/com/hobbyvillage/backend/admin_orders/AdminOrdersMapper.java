package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminOrdersMapper {
	// 페이지네이션 용 주문 상품 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts;")
	int getAllOrderCount();

	// 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts WHERE deadline != '1000-01-01';")
	int getDeliveriedOrderCount();

	// 검색 시 페이지네이션 용 주문 수 조회: 최근 주문/오래된 주문 순
	@Select("SELECT COUNT(*) FROM orderProducts WHERE ${condition} LIKE '%${keyword}%';")
	int getSearchAllOrderCount(@Param("condition") String condition, @Param("keyword") String keyword);

	// 검색 시 페이지네이션 용 주문 수 조회: 반납 기한 순
	@Select("SELECT COUNT(*) FROM orderProducts WHERE deadline != '1000-01-01' AND ${condition} LIKE '%${keyword}%';")
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
}
