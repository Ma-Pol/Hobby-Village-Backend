package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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

	// 실재 상품인지 체크
	@Select("SELECT COUNT(*) FROM products WHERE prodCode = #{prodCode};")
	int checkProductCount(@Param("prodCode") String prodCode);

	// 상품 상세 조회 - 상품 정보 조회
	@Select("SELECT p.*, AVG(r.revwRate) AS revwRate FROM products p LEFT JOIN reviews r ON "
			+ "p.prodCode = r.prodCode WHERE p.prodCode = #{prodCode} GROUP BY p.prodCode;")
	AdminProductsDTO getProductDetail(@Param("prodCode") String prodCode);

	// 상품 상세 조회 - 상품 이미지 파일명 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode=#{prodCode};")
	List<String> getProdPictures(@Param("prodCode") String prodCode);

	// 상품 상세 조회 - 연관 검색어 조회
	@Select("SELECT prodTag FROM productTag WHERE prodCode=#{prodCode};")
	List<String> getProdTag(@Param("prodCode") String prodCode);

	// 브랜드 목록 불러오기 (menuitem용)
	@Select("SELECT brand FROM brands ORDER BY brand;")
	List<String> getBrandList();

	// 카테고리 목록 불러오기 (menuitem용)
	@Select("SELECT category FROM categories ORDER BY categoryCode;")
	List<String> getCategoryList();

	// 상품 등록
	@Insert("INSERT INTO products (prodCode, prodBrand, prodPrice, prodCategory, prodShipping, "
			+ "prodName, prodContent, prodHost) VALUES (#{prodCode}, #{prodBrand}, #{prodPrice}, #{prodCategory}, "
			+ "#{prodShipping}, #{prodName}, #{prodContent}, #{prodHost});")
	boolean addProduct(AdminProductsDTO products);

	// 상품 등록 - 이미지 파일명 저장
	@Insert("INSERT INTO productPictures (prodCode, prodPicture) VALUES (#{prodCode}, #{prodPicture});")
	void addPicture(@Param("prodCode") String prodCode, @Param("prodPicture") String prodPicture);

	// 상품 등록 - 연관검색어 등록
	@Insert("INSERT INTO productTag (prodCode, prodTag) VALUES (#{prodCode}, #{tag});")
	boolean addProductTags(@Param("prodCode") String prodCode, @Param("tag") String tag);

	// 상품 삭제
	@Delete("DELETE FROM products WHERE prodCode=#{prodCode};")
	boolean deleteProduct(@Param("prodCode") String prodCode);

	// 상품 수정
	@Update("UPDATE products SET prodBrand = #{prodBrand}, prodPrice = #{prodPrice}, prodCategory = #{prodCategory}, "
			+ "prodShipping = #{prodShipping}, prodName = #{prodName}, prodContent = #{prodContent}, prodHost = #{prodHost} "
			+ "WHERE prodCode = #{prodCode};")
	boolean modifyProduct(AdminProductsDTO products);

	// 상품 수정 - 기존 연관검색어 삭제
	@Delete("DELETE FROM productTag WHERE prodCode = #{prodCode};")
	void deleteProductTags(@Param("prodCode") String prodCode);

	// 상품 수정 - 기존 이미지 파일명 삭제
	@Delete("DELETE FROM productPictures WHERE prodCode = #{prodCode};")
	void deletePicture(@Param("prodCode") String prodCode);

}