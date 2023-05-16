package com.hobbyvillage.backend.user_products;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//카테고리(category), 정렬(array), 분류(sort), 페이징(page), 검색어(search)

@Mapper
public interface UserProductsMapper {
	
	// 카테고리 목록 조회
	@Select("SELECT category FROM categories;")
	String[] getCategories();
	
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
	List<UserProductsDTO> getProductList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 상품 목록 조회
	@Select("SELECT p.prodCode, p.prodName, p.prodHost, p.prodIsRental, u.userCode "
			+ "FROM products p INNER JOIN users u ON p.prodHost = u.nickname "
			+ "WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<UserProductsDTO> getSearchProductList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);
}
