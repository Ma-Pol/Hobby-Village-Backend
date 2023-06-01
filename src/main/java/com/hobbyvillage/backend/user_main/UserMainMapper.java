package com.hobbyvillage.backend.user_main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMainMapper {

	// 인기순 정렬 상품 리스트 조회
	@Select("SELECT prodCode, prodName, prodPrice, prodDibs FROM products ORDER BY -rentalCount LIMIT 8;")
	List<UserMainProductDTO> getPopularProductList();

	// 인기순 정렬 상품 리스트 조회(브랜드관)
	@Select("SELECT prodCode, prodBrand, prodName, prodPrice, prodDibs FROM products  WHERE prodBrand IS NOT NULL "
			+ "ORDER BY -rentalCount LIMIT 8;")
	List<UserMainProductDTO> getPopularBrandProductList();

	// 상품 별 최상단 이미지 조회
	@Select("SELECT prodPicture FROM productPictures WHERE prodCode = #{prodCode} LIMIT 1;")
	String getProductPicture(@Param("prodCode") String prodCode);
}
