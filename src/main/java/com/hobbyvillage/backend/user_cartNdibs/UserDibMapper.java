package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDibMapper {
	// 전체 찜 상품 개수 조회
	@Select("SELECT COUNT(*) FROM dibs WHERE email = #{email};")
	int getAllDibCount(@Param("email") String email);

	// 찜 브랜드관 상품 개수 조회
	@Select("SELECT COUNT(*) FROM dibs d INNER JOIN products p ON d.prodCode = p.prodCode "
			+ "WHERE email = #{email} AND prodBrand IS NOT NULL;")
	int getBrandDibCount(@Param("email") String email);

	// 전체 찜 상품 목록 조회
	@Select("SELECT d.dibCode, d.prodCode, p.prodCategory, p.prodBrand, p.prodHost, p.prodName, p.prodDibs, p.prodPrice, "
			+ "p.prodShipping, p.prodIsRental, pp.prodPicture FROM dibs d INNER JOIN products p ON d.prodCode = p.prodCode "
			+ "INNER JOIN (SELECT prodCode, prodPicture FROM productPictures WHERE prodPicCode IN ("
			+ "SELECT MIN(prodPicCode) FROM productPictures GROUP BY prodCode)) pp ON d.prodCode = pp.prodCode "
			+ "WHERE d.email = #{email} ${filter};")
	List<UserDibDTO> getDibList(@Param("email") String email, @Param("filter") String filter);

	// 찜 상품 삭제
	@Delete("DELETE FROM dibs WHERE dibCode = #{dibCode};")
	int deleteDib(@Param("dibCode") int dibCode);
}
