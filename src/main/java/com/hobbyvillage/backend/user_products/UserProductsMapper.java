package com.hobbyvillage.backend.user_products;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

// 검색어(search) 유무 
// 카테고리(category), 분류(sort)-전체/대여중/미대여,
// 정렬(array)-최신(regiDate)/평점(리뷰)-매퍼 따로/인기(찜기준)/고가/저가, 페이징(page)

@Mapper
public interface UserProductsMapper {

	// 카테고리 목록 조회
	@Select("SELECT category FROM categories ORDER BY categoryCode;")
	List<String> getCategories();

	// 브랜드 목록 조회
	@Select("SELECT brand FROM brands ORDER BY brand ASC;")
	List<String> getBrands();

	// -------------------------------------

	// 상품 개수 조회 - 검색x
	@Select("SELECT COUNT(*) FROM products WHERE ${category} AND ${sort} AND prodDeleted = 0;")
	int getProductCount(@Param("category") String category, @Param("sort") String sort);

	// 상품 개수 조회 - 검색o
	@Select("SELECT COUNT(DISTINCT P.prodCode) FROM products p "
			+ "LEFT JOIN productTag pt ON p.prodCode = pt.prodCode WHERE ${category} AND ${sort} AND p.prodDeleted = 0 "
			+ "AND (CONCAT_WS(' ', p.prodCategory, p.prodBrand, p.prodName, p.prodContent, p.prodHost) "
			+ "LIKE '%${keyword}%' OR pt.prodTag LIKE '%${keyword}%');")
	int getSearchProductCount(@Param("category") String category, @Param("sort") String sort,
			@Param("keyword") String keyword);

	// 상품 개수 조회 - 검색x (브랜드관)
	@Select("SELECT COUNT(*) FROM products WHERE ${brand} AND ${sort} AND prodDeleted = 0;")
	int getBrandProductCount(@Param("brand") String brand, @Param("sort") String sort);

	// -------------------------------------

	// 상품 목록 조회 - 검색x & 평점순
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, "
			+ "AVG(r.revwRate) AS avgRate FROM products p LEFT JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE ${category} AND ${sort} AND prodDeleted = 0 GROUP BY p.prodCode "
			+ "ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListRR(@Param("category") String category, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 상품 목록 조회 - 검색x & not 평점순
	@Select("SELECT prodCode, prodName, prodPrice, prodDibs, prodIsRental, prodRegiDate " + "FROM products "
			+ "WHERE ${category} AND ${sort} AND prodDeleted = 0 ORDER BY ${array} LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductList(@Param("category") String category, @Param("sort") String sort,
			@Param("array") String array, @Param("pageNum") int pageNum);

	// 상품 목록 조회 - 검색x & 평점순 (브랜드관)
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, p.prodBrand, "
			+ "AVG(r.revwRate) AS avgRate FROM products p LEFT JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE ${brand} AND ${sort} AND p.prodDeleted = 0 GROUP BY p.prodCode "
			+ "ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getBrandProductListRR(@Param("brand") String brand, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 상품 목록 조회 - 검색x & not 평점순 (브랜드관)
	@Select("SELECT prodCode, prodName, prodPrice, prodDibs, prodIsRental, prodRegiDate, prodBrand FROM products "
			+ "WHERE ${brand} AND ${sort} AND prodDeleted = 0 ORDER BY ${array} LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getBrandProductList(@Param("brand") String brand, @Param("sort") String sort,
			@Param("array") String array, @Param("pageNum") int pageNum);

	// 상품 목록 조회 - 검색o & 평점순
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate, "
			+ "AVG(r.revwRate) AS avgRate FROM products p LEFT JOIN productTag pt ON p.prodCode = pt.prodCode "
			+ "LEFT JOIN reviews r ON p.prodCode = r.prodCode WHERE ${category} AND ${sort} AND p.prodDeleted = 0 "
			+ "AND (CONCAT_WS(' ', p.prodCategory, p.prodBrand, p.prodName, p.prodContent, p.prodHost) "
			+ "LIKE '%${keyword}%' OR pt.prodTag LIKE '%${keyword}%') "
			+ "GROUP BY p.prodCode ORDER BY CASE WHEN avgRate IS NULL THEN 1 ELSE 0 END, -avgRate "
			+ "LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListSRR(@Param("category") String category, @Param("sort") String sort,
			@Param("keyword") String keyword, @Param("pageNum") int pageNum);

	// 상품 목록 조회 - 검색o & not 평점순
	@Select("SELECT p.prodCode, p.prodName, p.prodPrice, p.prodDibs, p.prodIsRental, p.prodRegiDate FROM products p "
			+ "LEFT JOIN productTag pt ON p.prodCode = pt.prodCode WHERE ${category} AND ${sort} AND p.prodDeleted = 0 "
			+ "AND (CONCAT_WS(' ', p.prodCategory, p.prodBrand, p.prodName, p.prodContent, p.prodHost) "
			+ "LIKE '%${keyword}%' OR pt.prodTag LIKE '%${keyword}%') "
			+ "GROUP BY p.prodCode ORDER BY ${array} LIMIT #{pageNum}, 12;")
	List<UserProductsDTO> getProductListS(@Param("category") String category, @Param("sort") String sort,
			@Param("array") String array, @Param("keyword") String keyword, @Param("pageNum") int pageNum);

	// -------------------------------------

	// 상품 실재 여부 확인
	@Select("SELECT COUNT(*) FROM products WHERE prodCode = #{prodCode};")
	int checkProduct(@Param("prodCode") String prodCode);

	// 상품 상세 조회
	@Select("SELECT p.prodCode, p.prodCategory, p.prodBrand, p.prodName, p.prodContent, p.prodPrice, "
			+ "p.prodHost, p.prodRegiDate, p.prodDibs, p.prodIsRental, u.profPicture " + "FROM products p "
			+ "INNER JOIN users u ON p.prodHost = u.nickname " + "WHERE p.prodCode=#{prodCode};")
	UserProductsDTO getProductDetail(@Param("prodCode") String prodCode);

	// 찜 확인
	@Select("SELECT COUNT(*) FROM dibs WHERE email=#{email} AND prodCode=#{prodCode};")
	int checkDibs(@Param("email") String email, @Param("prodCode") String prodCode);

	// 찜 등록
	@Insert("INSERT INTO dibs(email, prodCode) VALUES (#{email}, #{prodCode});")
	void updateDibs(@Param("email") String email, @Param("prodCode") String prodCode);

	// 찜 갯수 추가
	@Update("UPDATE products SET prodDibs=prodDibs+1 WHERE prodcode=#{prodCode};")
	void updateDibCount(@Param("prodCode") String prodCode);

	// 장바구니 확인
	@Select("SELECT COUNT(*) FROM carts WHERE email=#{email} AND prodCode=#{prodCode};")
	int checkCarts(@Param("email") String email, @Param("prodCode") String prodCode);

	// 장바구니 추가
	@Insert("INSERT INTO carts(email, prodCode, period) VALUES (#{email}, #{prodCode}, #{period});")
	void addCart(@Param("email") String email, @Param("prodCode") String prodCode, @Param("period") String period);

	// -------------------------------------

	// 상품 이미지 파일명 단일 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode = #{prodCode} ORDER BY prodPicture LIMIT 1;")
	String getProdPicture(@Param("prodCode") String prodCode);
	
	// 상품 이미지 파일명 전체 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode = #{prodCode} ORDER BY prodPicture;")
	List<String> getProdPictures(@Param("prodCode") String prodCode);

	// 상품 브랜드 파일명 조회
	@Select("SELECT b.brandLogo FROM products p INNER JOIN brands b ON p.prodBrand = b.brand "
			+ "WHERE p.prodCode = #{prodCode};")
	String getBrandImgName(@Param("prodCode") String prodCode);
}
