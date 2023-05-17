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
	// 미검색 상태에서 상품 개수 조회 v
	@Select("SELECT COUNT(*) FROM products WHERE ${filter};")
	int getProductCount(@Param("filter") String filter);

	// 검색 상태에서 상품 개수 조회 v
	@Select("SELECT COUNT(*) FROM products WHERE ${filter} AND ${condition} LIKE '%${keyword}%';")
	int getSearchProductCount(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword);

	// 미검색 상태에서 상품 목록 조회 v
	@Select("SELECT p.prodCode, p.prodName, p.prodHost, p.prodIsRental, u.userCode "
			+ "FROM products p INNER JOIN users u ON p.prodHost = u.nickname WHERE ${filter} "
			+ "ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminProductsDTO> getProductList(@Param("filter") String filter, @Param("sort") String sort,
			@Param("pageNum") int pageNum);

	// 검색 상태에서 상품 목록 조회 v
	@Select("SELECT p.prodCode, p.prodName, p.prodHost, p.prodIsRental, u.userCode "
			+ "FROM products p INNER JOIN users u ON p.prodHost = u.nickname "
			+ "WHERE ${filter} AND ${condition} LIKE '%${keyword}%' ORDER BY ${sort} LIMIT #{pageNum}, 10;")
	List<AdminProductsDTO> getSearchProductList(@Param("filter") String filter, @Param("condition") String condition,
			@Param("keyword") String keyword, @Param("sort") String sort, @Param("pageNum") int pageNum);
	
	// 상품 상세 조회 - 리뷰 존재하는 경우 v
	@Select("SELECT p.prodCode, p.prodBrand, p.prodPrice, p.prodCategory, p.prodRegiDate, p.prodName, "
			+ "p.rentalCount, p.prodIsRental, p.prodContent, AVG(r.revwRate) AS revwRate, p.prodDibs, "
			+ "p.prodHost FROM products p INNER JOIN reviews r ON p.prodCode = r.prodCode "
			+ "WHERE p.prodCode=#{prodCode};")
	public AdminProductsDTO getProductDetailWR(@Param("prodCode") String prodCode);
	
	// 상품 상세 조회 - 리뷰 없는 경우 v
	@Select("SELECT prodCode, prodBrand, prodPrice, prodCategory, prodRegiDate, prodName, "
			+ "rentalCount, prodIsRental, prodContent, prodDibs, "
			+ "prodHost FROM products "
			+ "WHERE prodCode=#{prodCode};")
	public AdminProductsDTO getProductDetail(@Param("prodCode") String prodCode);
	
	// 상품 상세 조회 - 이미지 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode=#{prodCode};")
	List<String> getProdPictures(@Param("prodCode") String prodCode);
	
	// 상품 상세 조회 - 연관검색어 조회 v
	@Select("SELECT prodTag FROM productTag WHERE prodCode=#{prodCode};")
	List<String> getProdTag(@Param("prodCode") String prodCode);
	
	// 브랜드 목록 불러오기 (menuitem용) v
	@Select("SELECT brand FROM brands ORDER BY brand ASC;")
	List<String> getBrandList();
	
	// 카테고리 목록 불러오기 (menuitem용) v
	@Select("SELECT category FROM categories ORDER BY category ASC;")
	List<String> getCategoryList();
	
	// 상품 등록 v
	@Insert("INSERT INTO products (prodCode, prodBrand, prodPrice, prodCategory, prodShipping, "
			+ "prodName, prodContent, prodHost) VALUES (#{prodCode}, #{prodBrand}, #{prodPrice}, #{prodCategory}, "
			+ "#{prodShipping}, #{prodName}, #{prodContent}, #{prodHost});")
	public boolean addProduct(AdminProductsDTO products);
	
//	// 상품 등록 - 이미지 업로드
//	@Insert("INSERT INTO productPictures (prodCode, prodPicture) VALUES (#{prodCode}, #{prodPicture});")
//	public boolean addProductPictures(AdminProductsDTO prodPictures);
	
	// 상품 등록 - 연관검색어 등록
	@Insert("INSERT INTO productTag (prodCode, prodTag) VALUES (#{prodCode}, #{tag});")
	public boolean addProductTags(@Param("prodCode") String prodCode, @Param("tag") String tag);
	
	// 상품 수정
	@Update("UPDATE products SET prodBrand=#{prodBrand}, prodPrice=#{prodPrice}, prodCategory=#{prodCategory}, "
			+ "prodName=#{prodName}, prodContent=#{prodContent};")
	public boolean modifyProduct(AdminProductsDTO products);
	
	// 상품 수정 - 이미지 수정
	
	// 상품 수정 - 연관검색어 수정
	
	// 상품 삭제
	@Delete("DELETE FROM products WHERE prodCode=#{prodCode};")
	public boolean deleteProduct(@Param("prodCode") String prodCode);
	
	// 상품 삭제 - 이미지 삭제
//	@Delete("DELETE FROM prodPictures WHERE prodCode=#{prodCode};")
//	public boolean deleteProdPictures(@Param("prodCode") String prodCode);
	
	// 상품 삭제 - 연관검색어 삭제 
	@Delete("DELETE FROM productTag WHERE prodCode=#{prodCode};")
	public boolean deleteProductTag(@Param("prodCode") String prodCode);
}
