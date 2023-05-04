package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminProductsMapper {
	// 미검색 상태에서 상품 개수 조회
	@Select("SELECT COUNT(*) FROM products WHERE ${filter};")
	int getProductCount(@Param("filter") String filter);

	// 검색 상태에서 상품 개수 조회
	@Select("SELECT COUNT(*) FROM products WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchProductCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	// 미검색 상태에서 상품 목록 조회
	@Select("SELECT p.prodCode, p.prodName, p.prodHost, p.prodIsRental, u.userCode "
			+ "FROM products p INNER JOIN users u ON p.prodHost = u.nickname WHERE ${filter} "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminProductsDTO> getProductList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 상품 목록 조회
	@Select("SELECT p.prodCode, p.prodName, p.prodHost, p.prodIsRental, u.userCode "
			+ "FROM products p INNER JOIN users u ON p.prodHost = u.nickname "
			+ "WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminProductsDTO> getSearchProductList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);
}
