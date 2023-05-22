package com.hobbyvillage.backend.user_products;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// 검색어(search) 유무 
// 카테고리(category), 분류(sort)-전체/대여중/미대여,
// 정렬(array)-최신(regiDate)/평점(리뷰)-매퍼 따로/인기(찜기준)/고가/저가, 페이징(page)

@Mapper
public interface UserProductsMapper {
	
	// 카테고리 목록 조회
	@Select("SELECT category FROM categories ORDER BY category ASC;")
	List<String> getCategories();
	
	// 브랜드 목록 조회
	@Select("SELECT brand FROM brands ORDER BY brand ASC;")
	List<String> getBrands();
	
	// -------------------------------------
	
	// 상품 개수 조회 - 검색x
	@Select("SELECT COUNT(*) FROM products WHERE ${category} AND ${sort};")
	int getProductCount(@Param("category") String category, @Param("sort") String sort);

	// 상품 개수 조회 - 검색o
	@Select("SELECT COUNT(DISTINCT products.prodCode) FROM products "
			+ "LEFT JOIN productTag ON products.prodCode = productTag.prodCode "
			+ "WHERE ${category} AND ${sort} "
			+ "AND (CONCAT_WS(' ', products.prodCategory, products.prodBrand, products.prodName, products.prodContent, products.prodHost) "
			+ "LIKE '%${keyword}%' OR productTag.prodTag LIKE '%${keyword}%');")
	int getSearchProductCount(@Param("category") String category, @Param("sort") String sort,
			@Param("keyword") String keyword);

	// 상품 개수 조회 - 검색x (브랜드관)
	@Select("SELECT COUNT(*) FROM products WHERE ${brand} AND ${sort};")
	int getBrandProductCount(@Param("brand") String brand, @Param("sort") String sort);
	
	// -------------------------------------
	
	// 상품 목록 조회 - 검색x & 평점순
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, "
			+ "AVG(r.revwRate) AS avgRate "
			+ "FROM products p LEFT JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE ${category} AND ${sort} "
			+ "GROUP BY p.prodCode "
			+ "ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListRR(@Param("category") String category, @Param("sort") String sort, 
			@Param("pageNum") int pageNum);
	
	// 상품 목록 조회 - 검색x & not 평점순
	@Select("SELECT prodCode, prodName, prodPrice, prodDibs, prodIsRental, prodRegiDate "
			+ "FROM products "
			+ "WHERE ${category} AND ${sort} "
			+ "ORDER BY ${array} "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductList(@Param("category") String category, @Param("sort") String sort,
			@Param("array") String array, @Param("pageNum") int pageNum);
	
	// 상품 목록 조회 - 검색x & 평점순 (브랜드관)
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, "
			+ "AVG(r.revwRate) AS avgRate "
			+ "FROM products p LEFT JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE ${brand} AND ${sort} "
			+ "GROUP BY p.prodCode "
			+ "ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getBrandProductListRR(@Param("brand") String brand, @Param("sort") String sort, 
			@Param("pageNum") int pageNum);
	
	// 상품 목록 조회 - 검색x & not 평점순 (브랜드관)
	@Select("SELECT prodCode, prodName, prodPrice, prodDibs, prodIsRental, prodRegiDate "
			+ "FROM products "
			+ "WHERE ${brand} AND ${sort} "
			+ "ORDER BY ${array} "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getBrandProductList(@Param("brand") String brand, @Param("sort") String sort,
			@Param("array") String array, @Param("pageNum") int pageNum);
	
	// 상품 목록 조회 - 검색o & 평점순 
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, "
			+ "AVG(r.revwRate) AS avgRate FROM products p "
			+ "LEFT JOIN productTag ON p.prodCode = productTag.prodCode "
			+ "LEFT JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE ${category} AND ${sort} "
			+ "AND (CONCAT_WS(' ', p.prodCategory, p.prodBrand, p.prodName, p.prodContent, p.prodHost) LIKE '%${keyword}%' OR productTag.prodTag LIKE '%${keyword}%') "
			+ "GROUP BY p.prodCode "
			+ "ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListSRR(@Param("category") String category, @Param("sort") String sort, 
			@Param("keyword") String keyword, @Param("pageNum") int pageNum);
	
	// 상품 목록 조회 - 검색o & not 평점순 
	@Select("SELECT products.prodCode, prodName, prodPrice, prodDibs, prodIsRental, prodRegiDate "
			+ "FROM products "
			+ "LEFT JOIN productTag ON products.prodCode = productTag.prodCode "
			+ "WHERE ${category} AND ${sort} "
			+ "AND (CONCAT_WS(' ', prodCategory, prodBrand, prodName, prodContent, prodHost) LIKE '%${keyword}%' OR productTag.prodTag LIKE '%${keyword}%') "
			+ "GROUP BY products.prodCode "
			+ "ORDER BY ${array} "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListS(@Param("category") String category, @Param("sort") String sort, 
			@Param("array") String array, @Param("keyword") String keyword, @Param("pageNum") int pageNum);

	// -------------------------------------
	
	// 상품 이미지 파일명 조회 (개별 => 목록, 상세페이지 모두 활용가능)
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode=#{prodCode} ORDER BY prodPicture ASC;")
	List<String> getProdPictures(@Param("prodCode") String prodCode);
}
