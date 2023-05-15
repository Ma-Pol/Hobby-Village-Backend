package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	// 상품 상세 조회 -- 리뷰 테이블에서 리뷰 평균 가져오는 법 ??
	@Select("SELECT prodCode, prodBrand, prodPrice, prodCategory, prodRegiDate, prodName, rentalCount, "
			+ "prodIsRental, prodContent, prodDibs, prodHost "
			+ "WHERE prodCode=#{prodCode}")
	public AdminProductsDTO getProductDetail(@Param("prodCode") String prodCode);
	
	// 상품 상세 조회 - 이미지 조회 
	
	// 상품 상세 조회 - 연관검색어 조회
	
	// 브랜드 목록 불러오기
	
	// 카테고리 목록 불러오기
	
	// 상품 등록 
	@Insert("INSERT INTO products (prodCode, prodBrand, prodPrice, prodCategory, prodShipping,"
			+ "prodName, prodContent, prodHost) VALUES (#{prodCode}, #{prodBrand}, #{prodPrice}, #{prodCategory}, "
			+ "#{prodShipping}, #{prodName}, #{prodContent}, #{prodHost})")
	public boolean addProduct(AdminProductsDTO products);
	
	// 상품 등록 - 이미지 업로드
	
	// 상품 등록 - 연관검색어 등록
	
	// 상품 수정
	@Update("UPDATE products SET prodBrand=#{prodBrand}, prodPrice=#{prodPrice}, prodCategory=#{prodCategory}, "
			+ "prodName=#{prodName}, prodContent=#{prodContent}")
	public boolean modifyProduct(AdminProductsDTO products);
	
	// 상품 수정 - 이미지 수정
	
	// 상품 수정 - 연관검색어 수정
}
